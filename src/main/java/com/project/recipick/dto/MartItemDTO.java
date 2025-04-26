package com.project.recipick.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MartItemDTO {
    private String a_name; // 품목 이름
    private int a_price; // 가격
}
