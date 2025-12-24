package xtel.base21v2.module.rabbitMQ;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import xtel.base21v2.infrastructure.rabbitMQ.RabbitMQProperties;

@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;
    private final RabbitMQProperties rabbitMQProperties;

    public void sendMessage(String sendMessageMq) {
        // RabbitTemplate sẽ tự convert object user sang JSON nhờ config ở bước 4
        rabbitTemplate.convertAndSend(rabbitMQProperties.getExchangeName(), rabbitMQProperties.getRoutingKey(), sendMessageMq);
        System.out.println("Message sent -> " + sendMessageMq);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    public void consume(String message) {
        // Jackson tự convert JSON về lại Object User
        System.out.println("Message received -> " + message );

    }
}
