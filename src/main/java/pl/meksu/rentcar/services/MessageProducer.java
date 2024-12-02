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

    public void sendMessage(BaseMessage baseMessage) {

        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, baseMessage);
    }
}