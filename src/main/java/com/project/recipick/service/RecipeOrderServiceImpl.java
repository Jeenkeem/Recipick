package com.project.recipick.service;

import com.project.recipick.DTO.Recipe;
import org.springframework.stereotype.Service;

@Service
public class RecipeOrderServiceImpl implements RecipeOrderService{

    public Recipe getRecipe() {

        // sample
        Recipe re = new Recipe();
        return re;
    }
}
