package com.project.recipick.dto;

public class MartItemDTO {
    private String aName;
    private int aPrice;

    public MartItemDTO() {}

    // ✅ 추가할 부분 (aName과 aPrice를 인자로 받는 생성자)
    public MartItemDTO(String aName, int aPrice) {
        this.aName = aName;
        this.aPrice = aPrice;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public int getaPrice() {
        return aPrice;
    }

    public void setaPrice(int aPrice) {
        this.aPrice = aPrice;
    }
}
