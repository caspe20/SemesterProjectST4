package com.sdu.web.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
@Controller
public class UIController {

    public SseEmitter emitter;

    @RequestMapping(value = "/greeting")
    public String test(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        System.out.println("This is a test");

        return "greeting";
    }

    @Lazy
    @GetMapping("/")
    public String root() {
        return "sample";
    }

    @CrossOrigin
    @RequestMapping(value = "/sse",consumes = MediaType.ALL_VALUE)
    public SseEmitter createSseConnection() {
        emitter = new SseEmitter(Long.MAX_VALUE);
        try {
            emitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return emitter;
    }

    public void send(String s) {
        if(emitter != null) {
            try {
                System.out.println("Sending!");
                emitter.send(SseEmitter.event().name("SomeName").data(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}