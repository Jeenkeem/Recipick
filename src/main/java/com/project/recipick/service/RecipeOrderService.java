package com.project.recipick.service;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.Entity.RecipeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.recipick.mapper.RecipeInfoMapper;

import java.util.List;

@Service
public class RecipeOrderService{

    @Autowired
    final RecipeInfoMapper recipeInfoMapper;

    public RecipeOrderService(RecipeInfoMapper recipeInfoRepository) {
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



}
