package com.sdu;

import Services.IUIService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Service;

@Service
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class},scanBasePackages = "com.sdu.controllers")
public class UIApplication implements IUIService {

    public static void main(String[] args) {
        SpringApplication.run(UIApplication.class,args);
    }

    @Override
    public void run(String[] args) {
        SpringApplication.run(UIApplication.class,args);
    }

    @Override
    public void update(String json) {
        System.out.println("Json: {\n" + json + "\n}");
    }
}
