package pl.meksu.rentcar.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.meksu.rentcar.config.RabbitMQConfig;
import pl.meksu.rentcar.util.BaseMessage;

@Service
public class MessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendContactMessage(BaseMessage baseMessage) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.CONTACT_QUEUE, baseMessage);
    }

    public void sendResetCodeMessage(BaseMessage resetCodeMessage) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.RESET_QUEUE, resetCodeMessage);
    }
}