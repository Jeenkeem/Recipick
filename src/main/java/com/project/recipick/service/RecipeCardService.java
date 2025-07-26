package com.project.recipick.service;

import com.project.recipick.Entity.RecipeIngredientEntity;
import com.project.recipick.Entity.RecipeIrdnt;
import com.project.recipick.dto.RecipeCardDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface RecipeCardService {
    void callRecipeApiAndSaveData(String apiKey, String addr);

    void callRecipeIngredientAndSaveData(String apiKey, String addr);

    List<RecipeCardDTO> getRecipeCardList();

    List<RecipeCardDTO> getSearchedRecipeList(String searchWord);

    List<RecipeIrdnt> getSearchedIngredientList(String inputIngredient);

    List<RecipeCardDTO> getRecommendRecipe(Map<String, Object> paramMap);
}
