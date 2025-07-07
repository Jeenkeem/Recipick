package com.project.recipick.mapper;

import com.project.recipick.Entity.RecipeCardEntity;
import com.project.recipick.Entity.RecipeIngredientEntity;
import com.project.recipick.dto.RecipeCardDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RecipeCardMapper {
    void insertMAFRARecipes(List<RecipeCardEntity> recipeCardEntities);

    void insertIngredient(List<RecipeIngredientEntity> recipeIngredientEntities);
    List<RecipeCardDTO> getRecipeCardList();

    List<RecipeCardDTO> getSearchedRecipeList(String searchWord);

    List<RecipeIngredientEntity> getSearchedIngredientList(String inputIngredient);

    List<RecipeCardDTO> getRecommendRecipe(Map<String, Object> paramMap);
}
