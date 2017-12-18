package reproducer;


import io.vertxbeans.rxjava.VertxBeans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(VertxBeans.class)
public class Starter {

    public static void main(String[] args) {
        new SpringApplication(Starter.class).run(args);
    }
}