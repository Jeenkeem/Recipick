package com.project.recipick.controller;

import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.service.RecipeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class IndexController {

    @Autowired
    final RecipeOrderService recipeOrderService;

    public IndexController(RecipeOrderService recipeOrderService) {
        this.recipeOrderService = recipeOrderService;
    }

    @GetMapping("/recipick/main")
    public String getMethodName() {
        List<RecipeInfo> recipeInfoList = recipeOrderService.recipieFindAll();

        for(int i=0; i<recipeInfoList.size(); i++) {
            System.out.println(recipeInfoList.get(i));
        }


        return "mainPage/main";
    }
    

}
