package reproducer;

import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.ext.web.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HttpClient {

    @Autowired
    private HttpServer httpServer;
    @Autowired
    private Vertx vertx;

    @PostConstruct
    public void startTest() {
        vertx.setPeriodic(500, id -> {
            if (httpServer.isServerStarted()) {
                vertx.cancelTimer(id);
                System.out.println("Server is started! Going to make HTTP request to test the idle timeout setting.");
                createClient().get("/")
                        .rxSend()
                        .subscribe(response -> {
                            System.out.println(response.body().toString());
                        }, ex -> {
                            ex.printStackTrace();
                        });
            }
        });
    }

    private WebClient createClient() {
        WebClientOptions options = new WebClientOptions()
                .setConnectTimeout(1000)
                .setIdleTimeout(3)
                .setDefaultPort(8080);
        return WebClient.create(vertx, options);
    }
}