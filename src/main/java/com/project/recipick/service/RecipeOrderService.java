package com.project.recipick.service;

import com.project.recipick.Entity.RecipeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.recipick.mapper.RecipeInfoMapper;

import java.util.List;

@Service
public class RecipeOrderService{

    @Autowired
    final RecipeInfoMapper recipeInfoRepository;

    public RecipeOrderService(RecipeInfoMapper recipeInfoRepository) {
        this.recipeInfoRepository = recipeInfoRepository;
    }

    public List<RecipeInfo> recipieFindAll() {
        return recipeInfoRepository.findAll();
    }


}
