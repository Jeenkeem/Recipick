package com.project.recipick.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecipeIngredientEntity {
    @JsonProperty("ROW_NUM")
    private int rowNum;

    @JsonProperty("RECIPE_ID")
    private int recipeId;

    @JsonProperty("IRDNT_SN")
    private int irdntSn;

    @JsonProperty("IRDNT_NM")
    private String irdntNm;

    @JsonProperty("IRDNT_CPCTY")
    private String irdntCpcty;

    @JsonProperty("IRDNT_TY_CODE")
    private String irdntTyCode;

    @JsonProperty("IRDNT_TY_NM")
    private String irdntTyNm;
}
