package xtel.base21v2.infrastructure.rabbitMQ;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@AllArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties rabbitMQProperties;

    @Bean
    public Queue createQueue() {
        // durable = true để queue tồn tại ngay cả khi RabbitMQ restart
        return new Queue(rabbitMQProperties.getQueueName(), true);
    }

    //Tạo Exchange
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(rabbitMQProperties.getExchangeName(), true, false);
    }

    //Binding: Liên kết Queue vào Exchange qua Routing Key
    @Bean
    public Binding binding(@Qualifier("createQueue") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(rabbitMQProperties.getRoutingKey());
    }

    //Cấu hình Converter để tự động chuyển Object <-> JSON
    @Bean
    public MessageConverter converter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}








