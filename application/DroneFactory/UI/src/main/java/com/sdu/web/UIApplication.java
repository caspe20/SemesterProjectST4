package com.sdu.web;

import services.IUIService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class},scanBasePackages = "com.sdu.web")
public class UIApplication implements IUIService {

    @Override
    public void run(String[] args) {
        SpringApplication.run(UIApplication.class, args);
    }

    @Override
    public void update(String json) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("com.sdu");
        context.refresh();
    }

    @GetMapping("/sample")
    public String show(){
        return "greeting";
    }

//    @Bean
//    public ViewResolver resolver(){
//
//    }
}