package com.project.recipick.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.Entity.RecipeIrdnt;
import com.project.recipick.Entity.RecipeProcedure;
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

        System.out.println(re.getRecipeId());
        System.out.println(re.getSUMRY());
        System.out.println(re.getLevelNm());

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

        /**********************************************/


        String apiUrlRecipeProcedure = "http://211.237.50.150:7080/openapi/2a2d98088a90a23a81db461c5bd31675ca4cb35b994183c8b27182fe01fd45f8/json/Grid_20150827000000000228_1/1/1000?RECIPE_ID="+recipe_id;

        // HTTP GET 요청 보내기
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrlRecipeProcedure, String.class);

        // 응답 값
        String resBody = response.getBody();
        System.out.println("GET Response: " + resBody);

        String jsonString = resBody;
        JSONParser parser2 = new JSONParser();

        JSONObject jsonObject = (JSONObject) parser2.parse(jsonString);
        String Grid228 = jsonObject.get("Grid_20150827000000000228_1").toString();
        System.out.println("Grid -> " + Grid228);

        JSONObject jsonObjectGrid = (JSONObject) parser2.parse(Grid228);
        System.out.println("row -> " + jsonObjectGrid.get("row").toString());

        String row228 = jsonObjectGrid.get("row").toString();
        JSONArray jsonArray228 = (JSONArray) parser.parse(row228);

        System.out.println(jsonArray228.get(0));

        ArrayList<RecipeProcedure> list228 = new ArrayList<>();

        for(int i=0; i<jsonArray228.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray228.get(i);

            RecipeProcedure recipeProcedure = new RecipeProcedure();

            recipeProcedure.setRECIPE_ID(Integer.parseInt(obj.get("RECIPE_ID").toString()));
            recipeProcedure.setCOOKING_NO(obj.get("COOKING_NO").toString());
            recipeProcedure.setCOOKING_DC(obj.get("COOKING_DC").toString());
            recipeProcedure.setSTEP_TIP(obj.get("STEP_TIP").toString());
            list228.add(recipeProcedure);
        }

        model.addAttribute("recipe_title", recipe_name);
        model.addAttribute("recipe_sumry", re.getSUMRY());
        model.addAttribute("recipe_level", re.getLevelNm());
        model.addAttribute("recipe_cookingTime", re.getCookingTime());
        model.addAttribute("calorie", re.getCALORIE());
        model.addAttribute("list", list);
        model.addAttribute("list228", list228);

        return "recipePage/recipeDetail";
    }
}
