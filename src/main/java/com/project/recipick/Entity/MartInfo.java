package com.project.recipick.Entity;

import lombok.Data;

@Data
public class MartInfo {
    private String P_SEQ;           // p_seq: 일련번호
    private String M_SEQ;           // m_seq: 시장/마트번호
    private String M_NAME;          // m_name: 시장/마트이름
    private String A_SEQ;           // a_seq: 품목 번호
    private String A_NAME;          // a_name: 품목 이름
    private String A_UNIT;          // a_unit: 실판매규격
    private String A_PRICE;         // a_price: 가격(원)
    private String P_YEAR_MONTH;     // p_year_month: 년도, 월
    private String ADD_COL;         // add_col: 비고
    private String P_DATE;          // p_date: 점검일자
    private String M_TYPE_CODE;      // m_type_code: 시장유형 구분 코드
    private String M_TYPE_NAME;      // m_type_name: 시장유형 구분 이름
    private String M_GU_CODE;        // m_gu_code: 자치구 코드
    private String M_GU_NAME;        // m_gu_name: 자치구 이름

    public String getpSeq() {
        return P_SEQ;
    }

    public void setpSeq(String P_SEQ) {
        this.P_SEQ = P_SEQ;
    }

    public String getmSeq() {
        return M_SEQ;
    }

    public void setmSeq(String M_SEQ) {
        this.M_SEQ = M_SEQ;
    }

    public String getmName() {
        return M_NAME;
    }

    public void setmName(String M_NAME) {
        this.M_NAME = M_NAME;
    }

    public String getaSeq() {
        return A_SEQ;
    }

    public void setaSeq(String A_SEQ) {
        this.A_SEQ = A_SEQ;
    }

    public String getaName() {
        return A_NAME;
    }

    public void setaName(String A_NAME) {
        this.A_NAME = A_NAME;
    }

    public String getaUnit() {
        return A_UNIT;
    }

    public void setaUnit(String A_UNIT) {
        this.A_UNIT = A_UNIT;
    }

    public int getaPrice() {
        return Integer.parseInt(A_PRICE);
    }

    public void setaPrice(String A_PRICE) {
        this.A_PRICE = A_PRICE;
    }

    public String getpYearMonth() {
        return P_YEAR_MONTH;
    }

    public void setpYearMonth(String P_YEAR_MONTH) {
        this.P_YEAR_MONTH = P_YEAR_MONTH;
    }

    public String getAddCol() {
        return ADD_COL;
    }

    public void setAddCol(String ADD_COL) {
        this.ADD_COL = ADD_COL;
    }

    public String getpDate() {
        return P_DATE;
    }

    public void setpDate(String P_DATE) {
        this.P_DATE = P_DATE;
    }

    public String getmTypeCode() {
        return M_TYPE_CODE;
    }

    public void setmTypeCode(String M_TYPE_CODE) {
        this.M_TYPE_CODE = M_TYPE_CODE;
    }

    public String getmTypeName() {
        return M_TYPE_NAME;
    }

    public void setmTypeName(String M_TYPE_NAME) {
        this.M_TYPE_NAME = M_TYPE_NAME;
    }

    public String getmGuCode() {
        return M_GU_CODE;
    }

    public void setmGuCode(String M_GU_CODE) {
        this.M_GU_CODE = M_GU_CODE;
    }

    public String getmGuName() {
        return M_GU_NAME;
    }

    public void setmGuName(String M_GU_NAME) {
        this.M_GU_NAME = M_GU_NAME;
    }
}
