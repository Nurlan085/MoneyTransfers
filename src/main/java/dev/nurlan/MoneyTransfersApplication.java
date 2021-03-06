package dev.nurlan;

import dev.nurlan.dao.MoneyTransfersDao;
import dev.nurlan.dao.MoneyTransfersDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoneyTransfersApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MoneyTransfersApplication.class, args);

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MoneyTransfersApplication.class);
    }



}
