package com.project.recipick.Entity;

import lombok.Data;

@Data
public class Product {
    private Long p_SEQ; // 일련번호
    private Long M_SEQ; // 시장/마트 번호
    private String M_NAME; // 시장/마트 이름
    private Long A_SEQ; // 품목 번호
    private String A_NAME; // 품목 이름
    private String A_UNIT; // 실판매규격
    private Double A_PRICE; // 가격(원)
    private String P_YEAR_MONTH; // 년도-월
    private String ADD_COL; // 비고
    private String P_DATE; // 점검일자
    private String M_TYPE_CODE; // 시장유형 구분 코드
    private String M_TYPE_NAME; // 시장유형 구분 이름
    private String M_GU_CODE; // 자치구 코드
    private String M_GU_NAME; // 자치구 이름

    public String getM_GU_NAME() {
        return M_GU_CODE;
    }
}
