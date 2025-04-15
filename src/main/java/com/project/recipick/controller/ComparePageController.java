package com.project.recipick.controller;

import com.project.recipick.dto.MartItemDto;
import com.project.recipick.service.MartInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recipick")
public class ComparePageController {

    @Autowired
    private MartInfoService martInfoService;

    // 👉 comparePage.html 반환
    @GetMapping("/comparePage")
    public String getComparePage() {
        return "comparePage/comparePage";  // src/main/resources/templates/comparePage/comparePage.html
    }

    // 👉 마트 데이터 JSON으로 내려주는 API (프론트에서 fetch로 호출함)
    @ResponseBody
    @GetMapping("/mart-items")
    public List<MartItemDto> getMartItems(@RequestParam String martName) {
        return martInfoService.getMartItemsByMartName(martName);
    }

    // 👉 마트 이름만 간단히 내려주는 API
    @ResponseBody
    @GetMapping("/mart-names")
    public List<String> getMartNames() {
        return List.of("관악신사시장(신림4동)", "대명여울빛거리시장");
    }

}
