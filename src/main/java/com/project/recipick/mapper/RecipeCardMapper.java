package com.project.recipick.mapper;

import com.project.recipick.Entity.RecipeCardEntity;
import com.project.recipick.dto.RecipeCardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RecipeCardMapper {
    void insertMAFRARecipes(List<RecipeCardEntity> recipeCardEntities);
    List<RecipeCardDTO> getRecipeCardList();
}
