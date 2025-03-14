package com.project.recipick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MapPageController {
    
    @GetMapping("mapPage")
    public String getMethodName() {
        return "map/mapPage";
    }
    
}
