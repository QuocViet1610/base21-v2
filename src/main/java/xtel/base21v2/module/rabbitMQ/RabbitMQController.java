package xtel.base21v2.module.rabbitMQ;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("rabbitMQ")
@AllArgsConstructor
public class RabbitMQController {
    private final RabbitMQService rabbitMQService;

    @PostMapping()
    public void login(@RequestParam(value = "message") String message) {
        rabbitMQService.sendMessage(message);
    }

}
