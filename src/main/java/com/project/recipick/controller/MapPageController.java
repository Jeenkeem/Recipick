package com.project.recipick.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MapPageController {
    
    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @GetMapping("/recipick/mapPage")
    public String getMethodName(Model model) {

        model.addAttribute("kakaoApiKey", kakaoApiKey);

        return "map/mapPage";
    }
    
}
