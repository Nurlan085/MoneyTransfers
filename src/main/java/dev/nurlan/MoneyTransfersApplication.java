package dev.nurlan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MoneyTransfersApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MoneyTransfersApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MoneyTransfersApplication.class);
    }

}
