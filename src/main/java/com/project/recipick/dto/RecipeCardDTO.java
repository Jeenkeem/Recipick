package com.project.recipick.dto;

import lombok.Data;

@Data
public class RecipeCardDTO {

        private String recipe_id;
        private String recipe_nm_ko;
        private String cooking_time;
        private String calorie;
        private String level_nm;
        private String rcp_img_url;  // 이미지 url

        public String getRecipe_id() {
                return recipe_id;
        }

        public String getRecipe_nm_ko() {
                return recipe_nm_ko;
        }

        public String getCooking_time() {
                return cooking_time;
        }

        public String getLevel_nm() {
                return level_nm;
        }

        public String getRcp_img_url() {
                return rcp_img_url;
        }

        public String getCalorie() {
                return calorie;
        }
        /*
        private String recipeId;
        private String recipeNmKo;
        private String cookingTime;
        private String calorie;
        private String levelNm;
        private String rcpImgUrl;  // 이미지 url

        public String getRecipeId() {
                return recipeId;
        }

        public void setRecipeId(String  recipeId) {
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

         */

}
