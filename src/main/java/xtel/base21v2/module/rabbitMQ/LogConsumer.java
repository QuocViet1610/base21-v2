package xtel.base21v2.module.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class LogConsumer {
    @RabbitListener(queues = "queue.devops")
    public void receiveDevOps(String msg) {
        System.out.println("[DevOps Team] Alert: " + msg);
    }

    @RabbitListener(queues = "queue.payment")
    public void receivePayment(String msg) {
        System.out.println("[Payment Team] Info: " + msg);
    }

    @RabbitListener(queues = "queue.all-logs")
    public void receiveAll(String msg) {
        System.out.println("[BigData] Archiving: " + msg);
    }
}
