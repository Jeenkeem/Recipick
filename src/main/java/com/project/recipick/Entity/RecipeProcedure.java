package com.project.recipick.Entity;

import lombok.Data;

@Data
public class RecipeProcedure {
    private int RECIPE_ID; // 레시피 코드
    private String COOKING_NO; // 요리설명순서
    private String COOKING_DC; // 요리설명
    private String STEP_TIP; // 과정팁

    public int getRECIPE_ID() {
        return RECIPE_ID;
    }

    public void setRECIPE_ID(int RECIPE_ID) {
        this.RECIPE_ID = RECIPE_ID;
    }

    public String getCOOKING_NO() {
        return COOKING_NO;
    }

    public void setCOOKING_NO(String COOKING_NO) {
        this.COOKING_NO = COOKING_NO;
    }

    public String getCOOKING_DC() {
        return COOKING_DC;
    }

    public void setCOOKING_DC(String COOKING_DC) {
        this.COOKING_DC = COOKING_DC;
    }

    public String getSTEP_TIP() {
        return STEP_TIP;
    }

    public void setSTEP_TIP(String STEP_TIP) {
        this.STEP_TIP = STEP_TIP;
    }
}
