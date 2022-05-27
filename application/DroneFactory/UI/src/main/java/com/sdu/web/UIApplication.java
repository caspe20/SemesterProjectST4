package com.sdu.web;

import services.IUIService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class},scanBasePackages = "com.sdu.web")
public class UIApplication implements IUIService {

    ApplicationContext webApplicationContext;

    @Override
    public void run(String[] args) {
        webApplicationContext = SpringApplication.run(UIApplication.class,args);
        UIController controller = webApplicationContext.getBean(UIController.class);
        controller.setGateway(this);
    }

    @Override
    public void update(String json) {
        for (UIController value : webApplicationContext.getBeansOfType(UIController.class).values()) {
            value.pushUpdateString(json);
        }
    }

    public void startProduction() {
        // publish event here for starting production.
        System.out.println("Yes my dear, it does indeed work!");
    }
}