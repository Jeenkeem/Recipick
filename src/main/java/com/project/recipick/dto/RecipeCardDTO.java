package com.project.recipick.dto;

import lombok.Data;

public class RecipeCardDTO {

        private int recipeId;
        private String recipeNmKo;
        private String cookingTime;
        private String calorie;
        private String levelNm;
        private String rcpImgUrl;  // 이미지 url

        public int getRecipeId() {
                return recipeId;
        }

        public void setRecipeId(int recipeId) {
                this.recipeId = recipeId;
        }

        // Getter and Setter for recipeNmKo
        public String getRecipeNmKo() {
                return recipeNmKo;
        }

        public void setRecipeNmKo(String recipeNmKo) {
                this.recipeNmKo = recipeNmKo;
        }

        // Getter and Setter for cookingTime
        public String getCookingTime() {
                return cookingTime;
        }

        public void setCookingTime(String cookingTime) {
                this.cookingTime = cookingTime;
        }

        // Getter and Setter for calorie
        public String getCalorie() {
                return calorie;
        }

        public void setCalorie(String calorie) {
                this.calorie = calorie;
        }

        // Getter and Setter for levelNm
        public String getLevelNm() {
                return levelNm;
        }

        public void setLevelNm(String levelNm) {
                this.levelNm = levelNm;
        }

        // Getter and Setter for rcpImgUrl
        public String getRcpImgUrl() {
                return rcpImgUrl;
        }

        public void setRcpImgUrl(String rcpImgUrl) {
                this.rcpImgUrl = rcpImgUrl;
        }

}
