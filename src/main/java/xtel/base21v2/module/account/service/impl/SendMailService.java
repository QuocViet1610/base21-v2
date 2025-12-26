package xtel.base21v2.module.account.service.impl;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import xtel.base21v2.module.account.domain.request.SendEmailEvent;

@Service
public class SendMailService {

    @Async("taskExecutor")
    @EventListener
//    @TransactionalEventListener
    public void sendEmail(SendEmailEvent event) {
        System.out.println("Start send email...");
        try {
            Thread.sleep(3000); // độ trễ 3 giây
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Email sent to " + event.getEmail());
        throw new RuntimeException("Lỗi nhưng không ảnh hương đến request SẼ BỊ NUỐT");
    }

}
