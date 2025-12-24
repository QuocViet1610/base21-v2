package xtel.base21v2.infrastructure.rabbitMQ;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class RabbitMQProperties {

    @Value("${spring.rabbitmq.queue.name}")
    private String queueName;

    @Value("${spring.rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${spring.rabbitmq.routing.key}")
    private String routingKey;

}
