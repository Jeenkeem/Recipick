package com.project.recipick.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecipeCardEntity {

    @JsonProperty("ROW_NUM")  // JSON의 "ROW_NUM"을 rowNum 필드로 매핑
    private int rowNum;

    @JsonProperty("RECIPE_ID")
    private int recipeId;

    @JsonProperty("RECIPE_NM_KO")
    private String recipeNmKo;

    @JsonProperty("SUMRY")
    private String sumry;

    @JsonProperty("NATION_CODE")
    private String nationCode;

    @JsonProperty("NATION_NM")
    private String nationNm;

    @JsonProperty("TY_CODE")
    private String tyCode;

    @JsonProperty("TY_NM")
    private String tyNm;

    @JsonProperty("COOKING_TIME")
    private String cookingTime;

    @JsonProperty("CALORIE")
    private String calorie;

    @JsonProperty("QNT")
    private String qnt;

    @JsonProperty("LEVEL_NM")
    private String levelNm;

    @JsonProperty("IRDNT_CODE")
    private String irdntCode;

    @JsonProperty("PC_NM")
    private String pcNm;



}
