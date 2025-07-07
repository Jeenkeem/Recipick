package com.project.recipick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
    
    @GetMapping("/main")
    public String getMethodName() {
        return "mainPage/main";
    }
    

}
