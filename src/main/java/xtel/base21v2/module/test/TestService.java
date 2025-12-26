package xtel.base21v2.module.test;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TestService {

    public void completableFeature(){
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            System.out.println("Run async");
        });
    }

}
