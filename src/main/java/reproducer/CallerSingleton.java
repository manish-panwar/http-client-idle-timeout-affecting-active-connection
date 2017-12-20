package reproducer;

import io.vertx.rxjava.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CallerSingleton {

    private ExecutorService executorService = Executors.newFixedThreadPool(2);
    @Autowired
    private SomeSingletonBean someSingletonBean;
    @Autowired
    private Vertx vertx;

    @PostConstruct
    public void init() {
        vertx.setPeriodic(1000, id -> {
            executorService.submit(() -> {
                System.out.println(someSingletonBean.getUuid());
            });
        });
    }
}