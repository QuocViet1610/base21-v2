package xtel.base21v2.infrastructure.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("system.log.exchange");
    }

    // 2. Tạo Queue
    @Bean public Queue devOpsQueue() { return new Queue("queue.devops"); }
    @Bean public Queue paymentQueue() { return new Queue("queue.payment"); }
    @Bean public Queue allLogQueue() { return new Queue("queue.all-logs"); }

    // 3. Binding với Wildcards
    @Bean
    public Binding bindingDevOps(Queue devOpsQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(devOpsQueue).to(topicExchange).with("*.error");
    }
    @Bean
    public Binding bindingPayment(Queue paymentQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(paymentQueue).to(topicExchange).with("payment.#");
    }
    @Bean
    public Binding bindingAll(Queue allLogQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(allLogQueue).to(topicExchange).with("#");
    }

}
