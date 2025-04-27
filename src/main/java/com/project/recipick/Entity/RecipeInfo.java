package com.project.recipick.Entity;


import lombok.Data;

@Data
public class RecipeInfo {


    private int RECIPE_ID;
    private String RECIPE_NM_KO;  // 레시피 한글 이름
    private String SUMRY; // 간략(요약) 소개
    private String NATION_CODE; // 유형 코드
    private String NATION_NM; // 유형 분류
    private String TY_CODE; // 음식분류코드
    private String TY_NM; // 음식분류
    private String COOKING_TIME; // 조리시간
    private String CALORIE; // 칼로리
    private String QNT; // 분량
    private String LEVEL_NM; // 난이도
    private String IRDNT_CODE; // 재료별 분류명
    private String PC_NM; // 가격별 분류

    public int getRECIPE_ID() {
        return RECIPE_ID;
    }

    public void setRECIPE_ID(int RECIPE_ID) {
        this.RECIPE_ID = RECIPE_ID;
    }

    public String getRECIPE_NM_KO() {
        return RECIPE_NM_KO;
    }

    public void setRECIPE_NM_KO(String RECIPE_NM_KO) {
        this.RECIPE_NM_KO = RECIPE_NM_KO;
    }

    public String getSUMRY() {
        return SUMRY;
    }

    public void setSUMRY(String SUMRY) {
        this.SUMRY = SUMRY;
    }

    public String getNATION_CODE() {
        return NATION_CODE;
    }

    public void setNATION_CODE(String NATION_CODE) {
        this.NATION_CODE = NATION_CODE;
    }

    public String getNATION_NM() {
        return NATION_NM;
    }

    public void setNATION_NM(String NATION_NM) {
        this.NATION_NM = NATION_NM;
    }

    public String getTY_CODE() {
        return TY_CODE;
    }

    public void setTY_CODE(String TY_CODE) {
        this.TY_CODE = TY_CODE;
    }

    public String getTY_NM() {
        return TY_NM;
    }

    public void setTY_NM(String TY_NM) {
        this.TY_NM = TY_NM;
    }

    public String getCOOKING_TIME() {
        return COOKING_TIME;
    }

    public void setCOOKING_TIME(String COOKING_TIME) {
        this.COOKING_TIME = COOKING_TIME;
    }

    public String getCALORIE() {
        return CALORIE;
    }

    public void setCALORIE(String CALORIE) {
        this.CALORIE = CALORIE;
    }

    public String getQNT() {
        return QNT;
    }

    public void setQNT(String QNT) {
        this.QNT = QNT;
    }

    public String getLEVEL_NM() {
        return LEVEL_NM;
    }

    public void setLEVEL_NM(String LEVEL_NM) {
        this.LEVEL_NM = LEVEL_NM;
    }

    public String getIRDNT_CODE() {
        return IRDNT_CODE;
    }

    public void setIRDNT_CODE(String IRDNT_CODE) {
        this.IRDNT_CODE = IRDNT_CODE;
    }

    public String getPC_NM() {
        return PC_NM;
    }

    public void setPC_NM(String PC_NM) {
        this.PC_NM = PC_NM;
    }
}
