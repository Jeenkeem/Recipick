package com.project.recipick.controller;

import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.service.RecipeDetailService;
import io.micrometer.core.instrument.MultiGauge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.project.recipick.dto.RecipeInfoDTO;
import java.util.List;

@Controller
public class RecipeDetailController {

    @Autowired
    final RecipeDetailService recipeDetailService;

    @Autowired
    private final RestTemplate restTemplate;

    public RecipeDetailController(RecipeDetailService recipeDetailService, RestTemplate restTemplate) {
        this.recipeDetailService = recipeDetailService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/recipick/recipeInfo")
    public String recipeInfo(@RequestParam String recipe_name,
                             @RequestParam String recipe_id,
                             Model model) {
        RecipeInfo re = recipeDetailService.findRecipe(recipe_name);

        System.out.println(re.getRECIPE_NM_KO());

        /*
        @Value("${recipeInfo.Auth.Key}")
        private String apiKey;


        String apiUrl = "http://211.237.50.150:7080/openapi/2a2d98088a90a23a81db461c5bd31675ca4cb35b994183c8b27182fe01fd45f8/json/Grid_20150827000000000227_1/1/1000?RECIPE_ID="+recipe_id;

        // HTTP GET 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        // 응답 값
        String responseBody = responseEntity.getBody();
        System.out.println("GET Response: " + responseBody);
        */

        String apiUrl = "http://211.237.50.150:7080/openapi/2a2d98088a90a23a81db461c5bd31675ca4cb35b994183c8b27182fe01fd45f8/json/Grid_20150827000000000227_1/1/1000?RECIPE_ID="+recipe_id;

        RecipeInfoDTO response = restTemplate.getForObject(apiUrl, RecipeInfoDTO.class);

        // 예시: 재료 리스트 출력
        List<MultiGauge.Row> ingredients = response.getGrid().getRow();
        for (MultiGauge.Row row : ingredients) {
            System.out.println("재료명: " + row.getIrdntNm());
        }

        model.addAttribute("recipe_title", re.getRECIPE_NM_KO());
        model.addAttribute("recipe_sumry", re.getSUMRY());
        model.addAttribute("recipe_level", re.getLEVEL_NM());
        model.addAttribute("recipe_cookingTime", re.getCOOKING_TIME());

        return "recipePage/recipeDetail";
    }
}
