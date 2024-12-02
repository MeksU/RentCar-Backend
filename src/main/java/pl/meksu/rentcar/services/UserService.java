package pl.meksu.rentcar.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.meksu.rentcar.models.User;
import pl.meksu.rentcar.models.UserDetails;
import pl.meksu.rentcar.repo.UserDetailsRepository;
import pl.meksu.rentcar.repo.UserRepository;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final Logger logger = Logger.getLogger(UserService.class.getName());

    public Optional<User> getUserByMail(String mail) {
        logger.info("Fetching user by mail: " + mail);
        return userRepository.findByMail(mail);
    }

    public boolean isMailUnique(String mail) {
        logger.info("Checking if mail is unique: " + mail);
        return !userRepository.existsByMail(mail);
    }

    public User registerUser(String name, String surname, String mail, String password, String type, String address, String phone, String postalCode) {
        logger.info("Registering user with mail: " + mail);

        User user = new User();
        user.setName(name)
                .setSurname(surname)
                .setMail(mail)
                .setPassword(passwordEncoder.encode(password))
                .setType(type);

        User savedUser = userRepository.save(user);
        logger.info("User registered with mail: " + mail + " and ID: " + savedUser.getId());

        UserDetails userDetails = new UserDetails();
        userDetails.setUser(savedUser)
                .setAddress(address)
                .setPhone(phone)
                .setPostalCode(postalCode);

        userDetailsRepository.save(userDetails);

        return savedUser;
    }


    public String loginUser(String mail, String password) {
        logger.info("Attempting to authenticate user with email: " + mail);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(mail, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("User authenticated successfully with email: " + mail);
        return "User authenticated successfully";
    }

    public Optional<User> getUserById(int id) {
        logger.info("Fetching user by ID: " + id);
        return userRepository.findById(id);
    }
}