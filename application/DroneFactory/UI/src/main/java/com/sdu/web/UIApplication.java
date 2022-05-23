package com.sdu.web;

import Services.IUIService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Service
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class},scanBasePackages = "com.sdu.web")
public class UIApplication implements IUIService {

    @Override
    public void run(String[] args) {
        SpringApplication.run(UIApplication.class, args);
    }

    @Override
    public void update(String json) {
        System.out.println( this.getClass().toString() + " Json: {\n" + json + "\n}");
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