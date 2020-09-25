package core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ApplicationStarter {
    @PostConstruct
    private void post() {
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class);
    }
}
