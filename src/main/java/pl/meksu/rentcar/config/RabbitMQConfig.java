package pl.meksu.rentcar.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import pl.meksu.rentcar.services.MessageConsumer;

@Configuration
public class RabbitMQConfig {

    public static final String CONTACT_QUEUE = "contactQueue";
    public static final String RESET_QUEUE = "resetQueue";

    @Bean
    public Queue contactQueue() {
        return new Queue(CONTACT_QUEUE, false, false, true);
    }

    @Bean
    public Queue resetQueue() {
        return new Queue(RESET_QUEUE, false, false, true);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}