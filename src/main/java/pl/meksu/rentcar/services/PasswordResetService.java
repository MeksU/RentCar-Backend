package pl.meksu.rentcar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.meksu.rentcar.util.BaseMessage;
import pl.meksu.rentcar.models.ResetPassword;
import pl.meksu.rentcar.models.User;
import pl.meksu.rentcar.repo.ResetPasswordRepository;
import pl.meksu.rentcar.repo.UserRepository;
import pl.meksu.rentcar.util.ResetPasswordMessage;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Random;

@Service
public class PasswordResetService {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void requestPasswordReset(String email) {
        User user = userRepository.findByMail(email).orElseThrow(() -> new RuntimeException("Użytkownik o podanym e-mailu nie istnieje."));

        String resetCode = String.format("%06d", new Random().nextInt(999999));
        Date expiryDate = Date.from(Instant.now().plus(10, ChronoUnit.MINUTES));

        ResetPassword resetPassword = new ResetPassword(user, resetCode, expiryDate);
        resetPasswordRepository.save(resetPassword);

        BaseMessage baseMessage = new BaseMessage();
        baseMessage.setType("reset_code");

        ResetPasswordMessage resetMessage = new ResetPasswordMessage(user.getMail(), resetCode);
        baseMessage.setData(resetMessage);

        messageProducer.sendMessage(baseMessage);
    }

    public boolean verifyResetCode(String email, String resetCode) {
        User user = userRepository.findByMail(email).orElseThrow(() -> new RuntimeException("Użytkownik o podanym e-mailu nie istnieje."));
        return resetPasswordRepository.findByUserAndResetCode(user, resetCode)
                .filter(resetPassword -> resetPassword.getExpiryDate().after(new Date()))
                .isPresent();
    }

    public boolean resetPassword(String email, String newPassword) {
        User user = userRepository.findByMail(email).orElseThrow(() -> new RuntimeException("Użytkownik o podanym e-mailu nie istnieje."));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }

    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void removeExpiredResetCodes() {
        Date now = new Date();
        resetPasswordRepository.deleteByExpiryDateBefore(now);
    }
}