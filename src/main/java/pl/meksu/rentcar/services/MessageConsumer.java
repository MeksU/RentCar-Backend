package pl.meksu.rentcar.services;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.meksu.rentcar.config.RabbitMQConfig;
import pl.meksu.rentcar.util.BaseMessage;
import pl.meksu.rentcar.models.Message;
import pl.meksu.rentcar.models.ResetPassword;
import pl.meksu.rentcar.models.User;
import pl.meksu.rentcar.util.ResetPasswordMessage;

@Service
public class MessageConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.CONTACT_QUEUE)
    public void receiveMessage(BaseMessage baseMessage) {
        String type = baseMessage.getType();

        if ("contact".equals(type)) {
            Message message = (Message) baseMessage.getData();
            sendEmail(message);
        } else {
            System.out.println("Nieznany typ wiadomości: " + type);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.RESET_QUEUE)
    public void receiveResetCodeMessage(BaseMessage baseMessage) {
        String type = baseMessage.getType();

        if ("reset_code".equals(type)) {
            ResetPasswordMessage reset = (ResetPasswordMessage) baseMessage.getData();
            sendResetCodeEmail(reset.getEmail(), reset.getResetCode());
        } else {
            System.out.println("Nieznany typ wiadomości: " + type);
        }
    }

    private void sendEmail(Message message) {
        User user = message.getUser();
        String to = user.getMail();
        String subject = "RentCar contact form";
        String body = "We received your message:\n" + message.getContent() + "\nWe will answer as soon as possible.\nBest regards,\nRentCar Team";
        emailService.sendEmail(to, subject, body);
    }

    private void sendResetCodeEmail(String email, String resetCode) {
        String subject = "RentCar - Resetowanie hasła";
        String body = "Otrzymałeś kod resetowania hasła: " + resetCode + "\n\n" +
                "Jeśli to nie była Twoja prośba, zignoruj ten e-mail.\n" +
                "Kod wygasa po 10 minutach.\n\n" +
                "RentCar Team";
        emailService.sendEmail(email, subject, body);
    }
}
