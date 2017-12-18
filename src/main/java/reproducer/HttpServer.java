package reproducer;

import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpServerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class HttpServer {

    private boolean serverStarted;
    @Autowired
    private Vertx vertx;

    @PostConstruct
    public void startServer() {
        vertx.createHttpServer()
                .requestHandler(request -> simulateDelayedResponse(request))
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        serverStarted = true;
                    }
                });
    }

    private void simulateDelayedResponse(final HttpServerRequest request) {
        vertx.setTimer(5000, nothing -> {
            request.response()
                    .setChunked(true)
                    .write("hello!")
                    .end();
        });
    }

    public boolean isServerStarted() {
        return serverStarted;
    }
}