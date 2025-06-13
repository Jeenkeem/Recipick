package com.project.recipick.service;

import com.project.recipick.Entity.RecipeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.recipick.mapper.RecipeInfoMapper;

import java.util.List;

@Service
public class RecipeDetailService {

    @Autowired
    final RecipeInfoMapper recipeInfoMapper;

    public RecipeDetailService(RecipeInfoMapper recipeInfoRepository) {
        this.recipeInfoMapper = recipeInfoRepository;
    }

    public List<RecipeInfo> findAllRecipe() {
        return recipeInfoMapper.findAll();
    }

    public void saveRecipe(List<RecipeInfo> re) {

        for(RecipeInfo vo : re) {
            recipeInfoMapper.saveRecipe(vo);
        }

    }

    public RecipeInfo findRecipe(String recipe_name) {

        return recipeInfoMapper.findRecipe(recipe_name);
    }


}
