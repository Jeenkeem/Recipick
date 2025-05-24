package com.project.recipick.controller;

import com.project.recipick.Entity.RecipeIngredientEntity;
import com.project.recipick.dto.RecipeCardDTO;
import com.project.recipick.service.RecipeCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/recipick")
public class RecipeCardController {

    @Autowired
    private RecipeCardService recipeCardService;
    @GetMapping("/cards")
    public String getRecipeCards(Model model) {
        model.addAttribute("recipeCards", recipeCardService.getRecipeCardList());

        return "recipeCardPage/recipeSearch";
    }

    @RequestMapping("/search")
    @ResponseBody
    public List<RecipeCardDTO> searchRecipe(@RequestParam String searchWord){
        List<RecipeCardDTO> result = recipeCardService.getSearchedRecipeList(searchWord);
        return result;
    }

    @GetMapping("/rec")
    public String getRecipeCardsRecommend(Model model){
        model.addAttribute("recipeCards", recipeCardService.getRecipeCardList());
        return "recipeCardPage/recipeRecommend";
    }

    @RequestMapping("rec/ingrnm")
    @ResponseBody
    public List<RecipeIngredientEntity> searchIngredient(@RequestParam String inputIngredient){
        List<RecipeIngredientEntity> result = recipeCardService.getSearchedIngredientList(inputIngredient);
        return result;
    }

    @RequestMapping("rec/result")
    @ResponseBody
    public List<RecipeCardDTO> getRecommendRecipe(@RequestBody List<String> ingredients){
        int selectedSize = ingredients.size();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ingredients", ingredients);
        paramMap.put("selectedSize", selectedSize);
        List<RecipeCardDTO> result = recipeCardService.getRecommendRecipe(paramMap);
        return result;
    }
}
