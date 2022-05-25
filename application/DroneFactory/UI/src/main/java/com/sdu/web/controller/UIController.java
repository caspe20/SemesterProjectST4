package com.sdu.web.controller;

import com.sdu.web.UIApplication;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class UIController {

    private List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private UIApplication app;

    @RequestMapping(value = "/")
    public String test(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @CrossOrigin
    @RequestMapping(value = "/sse", consumes = MediaType.ALL_VALUE)
    public SseEmitter createSseConnection() {
        // Make new emitter the longest possible connection time.
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        // When connection is removed from website, remove emitter from array
        emitter.onCompletion(()->emitters.remove(emitter));
        // Add emitter to array
        emitters.add(emitter);
        return emitter;
    }

    public void pushUpdateString(String json){
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().name("update").data(json));
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        }
    }

    @RequestMapping(value = "/startProduction", consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void startProductionClick(){
        app.startProduction();
    }

    public void setGateway(UIApplication uiApplication) {
        app = uiApplication;
    }

//    @ResponseBody
//    @RequestMapping(value="/update")
//    public String getUpdateString(){
//        String update = app.getUpdateString();
//        if(update.isEmpty()) return "{state:\"error\"}";
//        JSONObject obj =new JSONObject(app.getUpdateString());
//        return obj.toString();
//    }

}