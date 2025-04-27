package com.project.recipick.dto;

import lombok.Data;

@Data
public class RecipeCardDTO {

        private int recipeId;
        private String recipeNmKo;
        private String cookingTime;
        private String calorie;
        private String levelNm;
        private String rcpImgUrl;  // 이미지 url

}
