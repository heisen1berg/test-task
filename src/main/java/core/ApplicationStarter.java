package core;

import core.handle.calculate.CalculatorIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ApplicationStarter {
    @Autowired
    private CalculatorIterator calculatorIterator;

    @PostConstruct
    private void post() {
        new Thread(calculatorIterator).start();
    }

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStarter.class);
    }
}
