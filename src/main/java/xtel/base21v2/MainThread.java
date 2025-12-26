package xtel.base21v2;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class MainThread {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletableFuture<Integer> t1 = CompletableFuture.supplyAsync(() -> {
            try {
                sleep(1000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Task 1");
            return 10;
        }, executor);

        CompletableFuture<Integer> t2 = CompletableFuture.supplyAsync(() -> {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Task 2");
            return 20;
        }, executor);

        CompletableFuture<Integer> t3 = CompletableFuture.supplyAsync(() -> {
            try {
                sleep(9000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Task 3");
            return 30;
        }, executor);

//        CompletableFuture<Integer> result = CompletableFuture
//                .allOf(t1, t2, t3)
//                .thenApply(v -> {
//                    int a = t1.join();
//                    int b = t2.join();
//                    int c = t3.join();
//                    return a + b + c;
//                });

//        CompletableFuture<Integer> result = t1.thenCombineAsync(t2, (a, b) -> a * b).thenCombineAsync(t3, (a, b) -> a * b);
        List<CompletableFuture<Integer>> futures = List.of(t1, t2, t3);
        int sum = 0;
        for (CompletableFuture<Integer> f : futures) {

            sum += f.join();
            System.out.println(f.join());
        }

        System.out.println("Sum = " + sum);


//        System.out.println(result.join());
        System.out.println("Success");

    }

}
