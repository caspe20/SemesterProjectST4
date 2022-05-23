package com.sdu.web.controller;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UIController {

    @RequestMapping(value = "/greeting")
    public String test(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        System.out.println("This is a test");

        return "greeting";
    }

    @Lazy
    @GetMapping("/")
    public String root(){
        return "sample";
    }

}