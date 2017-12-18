package reproducer;

import io.vertx.core.http.HttpServerOptions;
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
        System.setProperty("logback.configurationFile", "logback.xml");
        vertx.createHttpServer(new HttpServerOptions()
                .setCompressionSupported(true))
                .requestHandler(request -> simulateRemoteServer(request))
                .listen(8080, result -> {
                    if (result.succeeded()) {
                        serverStarted = true;
                    }
                });
    }

    private void simulateRemoteServer(final HttpServerRequest request) {
        // Sleep on response so that we simulate the delayed response.
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