package com.project.recipick.controller;

import com.project.recipick.dto.RecipeCardDTO;
import com.project.recipick.service.RecipeCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/recipick")
public class RecipeCardController {

    @Autowired
    private RecipeCardService recipeCardService;
    @GetMapping("/cards")
    public String getRecipeCards(Model model) {
        List<RecipeCardDTO> recipeCardLists = recipeCardService.getRecipeCardList();
        model.addAttribute("recipeCards", recipeCardService.getRecipeCardList());

        return "recipecardPage/recipeSearch";
    }


}
