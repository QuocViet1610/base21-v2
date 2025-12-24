package xtel.base21v2.module.rabbitMQ;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("rabbitMQ-topic")
@AllArgsConstructor
public class LogController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // API: /log?key=payment.error&msg=Loi_thanh_toan mapp theo key
    @GetMapping("/log")
    public void sendLog(@RequestParam String key, @RequestParam String msg) {
        // Gửi vào Topic Exchange với routingKey động
        rabbitTemplate.convertAndSend("system.log.exchange", key, msg);
        System.out.println("Message sent -> " + msg);
    }

}
