package com.project.recipick.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recipe {

    public int RECIPE_ID;
    public String RECIPE_NM_KO;
    public String SUMRY;
    public String NATION_CODE; // 유형 코드
    public String NATION_NM; // 유형 분류
    public String TY_CODE; // 음식분류코드
    public String TY_NM; // 음식 분류
    public String COOKING_TIME; // 조리 시간
    public String CALORIE; // 칼로리
    public String QNT; // 분량
    public String LEVEL_NM; // 난이도
    public String IRDNT_CODE; // 재료별 분류명
    public String PC_NM; // 가격별 분류
}
