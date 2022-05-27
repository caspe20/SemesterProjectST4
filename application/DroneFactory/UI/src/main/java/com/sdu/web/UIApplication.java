package com.sdu.web;

import com.sdu.web.controller.UIController;
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
    private UIEventPublisher publisher;

    @Override
    public void run(String[] args) {
        webApplicationContext = SpringApplication.run(UIApplication.class,args);
        UIController controller = webApplicationContext.getBean(UIController.class);
        controller.setGateway(this);
        publisher = new UIEventPublisher();
    }

    @Override
    public void update(String json) {
        for (UIController value : webApplicationContext.getBeansOfType(UIController.class).values()) {
            value.pushUpdateString(json);
        }
    }

    public void startProduction() {
        publisher.publish("UI");
    }

    public static void main(String[] args) {
        UIApplication app = new UIApplication();
        app.run(args);

        UIEventHandler uiEventHandlerMES = new UIEventHandler("MES",app);
        UIEventHandler uiEventHandlerAssets = new UIEventHandler("Assets",app);
    }
}