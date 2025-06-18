package com.project.recipick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    @GetMapping("/recipick")
    public String home(){
        return "MainPage";
    }
}
