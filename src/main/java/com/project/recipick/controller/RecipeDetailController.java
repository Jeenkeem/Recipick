package com.project.recipick.controller;

import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecipeDetailController {

    @Autowired
    final RecipeService recipeService;

    public RecipeDetailController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipick/recipeInfo")
    public String recipeInfo(@RequestParam String recipe_name, Model model) {
        RecipeInfo re = recipeService.findRecipe(recipe_name);

        System.out.println(re.getRECIPE_NM_KO());

        model.addAttribute("recipe_title", re.getRECIPE_NM_KO());
        model.addAttribute("recipe_sumry", re.getSUMRY());
        model.addAttribute("recipe_level", re.getLEVEL_NM());
        model.addAttribute("recipe_cookingTime", re.getCOOKING_TIME());
        return "recipePage/recipeInfo";
    }
}
