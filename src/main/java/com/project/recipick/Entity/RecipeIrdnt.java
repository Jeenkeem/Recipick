package com.project.recipick.Entity;

import lombok.Data;

@Data
public class RecipeIrdnt {
    private int RECIPE_ID; // 레시피 코드
    private String IRDNT_SN; // 재료순번
    private String IRDNT_NM; // 재료명
    private String IRDNT_CPCTY; // 재료용량
    private String IRDNT_TY_CODE; // 재료타입 코드
    private String IRDNT_TY_NM	; // 재료타입명

    public int getRECIPE_ID() {
        return RECIPE_ID;
    }

    public void setRECIPE_ID(int RECIPE_ID) {
        this.RECIPE_ID = RECIPE_ID;
    }

    public String getIRDNT_SN() {
        return IRDNT_SN;
    }

    public void setIRDNT_SN(String IRDNT_SN) {
        this.IRDNT_SN = IRDNT_SN;
    }

    public String getIRDNT_NM() {
        return IRDNT_NM;
    }

    public void setIRDNT_NM(String IRDNT_NM) {
        this.IRDNT_NM = IRDNT_NM;
    }

    public String getIRDNT_CPCTY() {
        return IRDNT_CPCTY;
    }

    public void setIRDNT_CPCTY(String IRDNT_CPCTY) {
        this.IRDNT_CPCTY = IRDNT_CPCTY;
    }

    public String getIRDNT_TY_CODE() {
        return IRDNT_TY_CODE;
    }

    public void setIRDNT_TY_CODE(String IRDNT_TY_CODE) {
        this.IRDNT_TY_CODE = IRDNT_TY_CODE;
    }

    public String getIRDNT_TY_NM() {
        return IRDNT_TY_NM;
    }

    public void setIRDNT_TY_NM(String IRDNT_TY_NM) {
        this.IRDNT_TY_NM = IRDNT_TY_NM;
    }

}
