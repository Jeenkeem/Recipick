package com.project.recipick.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class RecipeInfo {

    @Id
    private Long Id;

    private int RECIPE_ID;
    private String RECIPE_NM_KO;
    private String SUMRY;
    private String NATION_CODE;
    private String NATION_NM;
    private String TY_CODE;
    private String TY_NM;
    private String COOKING_TIME;
    private String CALORIE;
    private String QNT;
    private String LEVEL_NM;
    private String IRDNT_CODE;
    private String PC_NM;

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
