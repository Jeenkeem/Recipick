package com.project.recipick.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MartInfo {
    private String P_SEQ; // 일련번호
    private String M_SEQ;  // 시장/마트번호
    private String M_NAME; // 시장/마트이름
    private String A_SEQ; // 품목 번호
    private String A_NAME; // 품목 이름
    private String A_UNIT; // 실판매규격
    private String A_PRICE; // 가격(원)
    private String P_YEAR_MONTH; // 년도, 월
    private String ADD_COL; // 비고
    private String P_DATE; // 점검일자
    private String M_TYPE_CODE; // 시장유형 구분 코드
    private String M_TYPE_NAME; // 시장유형 구분 이름
    private String M_GU_CODE; // 자치구 코드
    private String M_GU_NAME; // 자치구 이름

    // Getter and Setter methods
    public String getP_SEQ() {
        return P_SEQ;
    }

    public void setP_SEQ(String p_SEQ) {
        P_SEQ = p_SEQ;
    }

    public String getM_SEQ() {
        return M_SEQ;
    }

    public void setM_SEQ(String m_SEQ) {
        M_SEQ = m_SEQ;
    }

    public String getM_NAME() {
        return M_NAME;
    }

    public void setM_NAME(String m_NAME) {
        M_NAME = m_NAME;
    }

    public String getA_SEQ() {
        return A_SEQ;
    }

    public void setA_SEQ(String a_SEQ) {
        A_SEQ = a_SEQ;
    }

    public String getA_NAME() {
        return A_NAME;
    }

    public void setA_NAME(String a_NAME) {
        A_NAME = a_NAME;
    }

    public String getA_UNIT() {
        return A_UNIT;
    }

    public void setA_UNIT(String a_UNIT) {
        A_UNIT = a_UNIT;
    }

    public String getA_PRICE() {
        return A_PRICE;
    }

    public void setA_PRICE(String a_PRICE) {
        A_PRICE = a_PRICE;
    }

    public String getP_YEAR_MONTH() {
        return P_YEAR_MONTH;
    }

    public void setP_YEAR_MONTH(String p_YEAR_MONTH) {
        P_YEAR_MONTH = p_YEAR_MONTH;
    }

    public String getADD_COL() {
        return ADD_COL;
    }

    public void setADD_COL(String aDD_COL) {
        ADD_COL = aDD_COL;
    }

    public String getP_DATE() {
        return P_DATE;
    }

    public void setP_DATE(String p_DATE) {
        P_DATE = p_DATE;
    }

    public String getM_TYPE_CODE() {
        return M_TYPE_CODE;
    }

    public void setM_TYPE_CODE(String m_TYPE_CODE) {
        M_TYPE_CODE = m_TYPE_CODE;
    }

    public String getM_TYPE_NAME() {
        return M_TYPE_NAME;
    }

    public void setM_TYPE_NAME(String m_TYPE_NAME) {
        M_TYPE_NAME = m_TYPE_NAME;
    }

    public String getM_GU_CODE() {
        return M_GU_CODE;
    }

    public void setM_GU_CODE(String m_GU_CODE) {
        M_GU_CODE = m_GU_CODE;
    }

    public String getM_GU_NAME() {
        return M_GU_NAME;
    }

    public void setM_GU_NAME(String m_GU_NAME) {
        M_GU_NAME = m_GU_NAME;
    }
}
