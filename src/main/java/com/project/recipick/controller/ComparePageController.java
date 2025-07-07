package com.project.recipick.controller;

import com.project.recipick.dto.MartItemDTO;
import com.project.recipick.service.MartInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    // 수정 (느슨한 매칭을 사용하는 버전)
    @GetMapping("/mart-items")
    public List<MartItemDTO> getMartItems(@RequestParam String martName) {
        return martInfoService.getMartItemsByFuzzyMatch(martName);  // ✅ 유사 이름 허용
    }

}
