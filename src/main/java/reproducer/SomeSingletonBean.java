package reproducer;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SomeSingletonBean {

    public String getUuid() {
        return UUID.randomUUID().toString();
    }
}