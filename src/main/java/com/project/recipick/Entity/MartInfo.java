package com.project.recipick.Entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class MartInfo {
    private String pSeq;           // p_seq: 일련번호
    private String mSeq;           // m_seq: 시장/마트번호
    private String mName;          // m_name: 시장/마트이름
    private String aSeq;           // a_seq: 품목 번호
    private String aName;          // a_name: 품목 이름
    private String aUnit;          // a_unit: 실판매규격
    private String aPrice;         // a_price: 가격(원)
    private String pYearMonth;     // p_year_month: 년도, 월
    private String addCol;         // add_col: 비고
    private String pDate;          // p_date: 점검일자
    private String mTypeCode;      // m_type_code: 시장유형 구분 코드
    private String mTypeName;      // m_type_name: 시장유형 구분 이름
    private String mGuCode;        // m_gu_code: 자치구 코드
    private String mGuName;        // m_gu_name: 자치구 이름

    public String getpSeq() {
        return pSeq;
    }

    public void setpSeq(String pSeq) {
        this.pSeq = pSeq;
    }

    public String getmSeq() {
        return mSeq;
    }

    public void setmSeq(String mSeq) {
        this.mSeq = mSeq;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getaSeq() {
        return aSeq;
    }

    public void setaSeq(String aSeq) {
        this.aSeq = aSeq;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaUnit() {
        return aUnit;
    }

    public void setaUnit(String aUnit) {
        this.aUnit = aUnit;
    }

    public int getaPrice() {
        return Integer.parseInt(aPrice);
    }

    public void setaPrice(String aPrice) {
        this.aPrice = aPrice;
    }

    public String getpYearMonth() {
        return pYearMonth;
    }

    public void setpYearMonth(String pYearMonth) {
        this.pYearMonth = pYearMonth;
    }

    public String getAddCol() {
        return addCol;
    }

    public void setAddCol(String addCol) {
        this.addCol = addCol;
    }

    public String getpDate() {
        return pDate;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public String getmTypeCode() {
        return mTypeCode;
    }

    public void setmTypeCode(String mTypeCode) {
        this.mTypeCode = mTypeCode;
    }

    public String getmTypeName() {
        return mTypeName;
    }

    public void setmTypeName(String mTypeName) {
        this.mTypeName = mTypeName;
    }

    public String getmGuCode() {
        return mGuCode;
    }

    public void setmGuCode(String mGuCode) {
        this.mGuCode = mGuCode;
    }

    public String getmGuName() {
        return mGuName;
    }

    public void setmGuName(String mGuName) {
        this.mGuName = mGuName;
    }
}
