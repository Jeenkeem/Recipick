package com.project.recipick.service;

import com.project.recipick.Entity.RecipeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RecipeInfoRepository;

@Service
public class RecipeOrderService{

    @Autowired
    final RecipeInfoRepository recipeInfoRepository;

    public RecipeOrderService(RecipeInfoRepository recipeInfoRepository) {
        this.recipeInfoRepository = recipeInfoRepository;
    }

    public void saveRecipe(RecipeInfo re) {
        recipeInfoRepository.save(re);
    }
}
