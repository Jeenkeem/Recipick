package com.project.recipick.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.Entity.RecipeIrdnt;
import com.project.recipick.service.RecipeDetailService;
import io.micrometer.core.instrument.MultiGauge;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
                             Model model) throws ParseException {

        RecipeInfo re = recipeDetailService.findRecipe(recipe_name);

        String apiUrl = "http://211.237.50.150:7080/openapi/2a2d98088a90a23a81db461c5bd31675ca4cb35b994183c8b27182fe01fd45f8/json/Grid_20150827000000000227_1/1/1000?RECIPE_ID="+recipe_id;

        // HTTP GET 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        // 응답 값
        String responseBody = responseEntity.getBody();
        System.out.println("GET Response: " + responseBody);

        String jsonStringStr = responseBody;
        JSONParser parser = new JSONParser();

        JSONObject jsonObj = (JSONObject) parser.parse(jsonStringStr);
        String Grid = jsonObj.get("Grid_20150827000000000227_1").toString();
        System.out.println("Grid -> " + Grid);

        JSONObject jsonObjGrid = (JSONObject) parser.parse(Grid);
        System.out.println("row -> " + jsonObjGrid.get("row").toString());

        String row = jsonObjGrid.get("row").toString();
        JSONArray jsonArray = (JSONArray) parser.parse(row);

        System.out.println(jsonArray.get(0));

        ArrayList<RecipeIrdnt> list = new ArrayList<>();

        for(int i=0; i<jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);

            RecipeIrdnt reIrdnt = new RecipeIrdnt();

            reIrdnt.setRECIPE_ID(Integer.parseInt(obj.get("RECIPE_ID").toString()));
            reIrdnt.setIRDNT_SN(obj.get("IRDNT_SN").toString());
            reIrdnt.setIRDNT_NM(obj.get("IRDNT_NM").toString());
            reIrdnt.setIRDNT_CPCTY(obj.get("IRDNT_CPCTY").toString());
            reIrdnt.setIRDNT_TY_CODE(obj.get("IRDNT_TY_CODE").toString());
            reIrdnt.setIRDNT_TY_NM(obj.get("IRDNT_TY_NM").toString());

            list.add(reIrdnt);
        }

        model.addAttribute("recipe_title", re.getRECIPE_NM_KO());
        model.addAttribute("recipe_sumry", re.getSUMRY());
        model.addAttribute("recipe_level", re.getLEVEL_NM());
        model.addAttribute("recipe_cookingTime", re.getCOOKING_TIME());
        model.addAttribute("list", list);

        return "recipePage/recipeDetail";
    }
}
