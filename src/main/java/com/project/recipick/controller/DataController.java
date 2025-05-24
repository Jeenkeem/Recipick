package com.project.recipick.controller;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.Entity.RecipeIrdnt;
import com.project.recipick.service.MartInfoService;
import com.project.recipick.service.RecipeCardService;
import com.project.recipick.service.RecipeDetailService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipick")
public class DataController {

    @Autowired
    final RecipeDetailService recipeOrderService;

    @Autowired
    final MartInfoService martInfoService;

    @Autowired
    private RecipeCardService recipeCardService;

    @Autowired
    private final ResourceLoader resourceLoader;

    @Autowired
    private final RestTemplate restTemplate;

    public DataController(RecipeDetailService recipeOrderService, MartInfoService martInfoService, ResourceLoader resourceLoader, RestTemplate restTemplate) {
        this.recipeOrderService = recipeOrderService;
        this.martInfoService = martInfoService;
        this.resourceLoader = resourceLoader;
        this.restTemplate = restTemplate;
    }


    @Value("${kakao.restapi.key}")
    private String kakaoRestApiKey;

    @GetMapping("/saveRecipe")
    public void SaveRecipe() throws ParseException {
        String apiUrl = "http://211.237.50.150:7080/openapi/2a2d98088a90a23a81db461c5bd31675ca4cb35b994183c8b27182fe01fd45f8/json/Grid_20150827000000000226_1/1/1000";

        // HTTP GET 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);

        // 응답 값
        String responseBody = responseEntity.getBody();
        System.out.println("GET Response: " + responseBody);

        String jsonStringStr = responseBody;
        JSONParser parser = new JSONParser();

        JSONObject jsonObj = (JSONObject) parser.parse(jsonStringStr);
        String Grid = jsonObj.get("Grid_20150827000000000226_1").toString();
        System.out.println("Grid -> " + Grid);

        JSONObject jsonObjGrid = (JSONObject) parser.parse(Grid);
        System.out.println("row -> " + jsonObjGrid.get("row").toString());

        String row = jsonObjGrid.get("row").toString();
        JSONArray jsonArray = (JSONArray) parser.parse(row);

        System.out.println(jsonArray.get(0));

        ArrayList<RecipeInfo> list = new ArrayList<>();



        for(int i=0; i<jsonArray.size(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);

            RecipeInfo re = new RecipeInfo();

            re.setRECIPE_ID(Integer.parseInt(obj.get("RECIPE_ID").toString()));
            re.setRECIPE_NM_KO(obj.get("RECIPE_NM_KO").toString());
            re.setSUMRY(obj.get("SUMRY").toString());
            re.setNATION_CODE(obj.get("NATION_CODE").toString());
            re.setNATION_NM(obj.get("NATION_NM").toString());
            re.setTY_CODE(obj.get("TY_CODE").toString());
            re.setTY_NM(obj.get("TY_NM").toString());
            re.setCOOKING_TIME(obj.get("COOKING_TIME").toString());
            re.setCALORIE(obj.get("CALORIE").toString());
            re.setQNT(obj.get("QNT").toString());
            re.setLEVEL_NM(obj.get("LEVEL_NM").toString());
            re.setIRDNT_CODE(obj.get("IRDNT_CODE").toString());
            re.setPC_NM(obj.get("PC_NM").toString());

            list.add(re);
        }

        System.out.println(list.get(0).getRECIPE_NM_KO());
        System.out.println(list.get(1).getRECIPE_NM_KO());

        recipeOrderService.saveRecipe(list);

    }

    @GetMapping("/getAllMartInfo")
    public List<MartInfo> getAllMartInfo() {
        return martInfoService.getAllMartInfo();
    }

    @GetMapping("/getSameMartInfo")
    public List<MartInfo> getMartInfo(@RequestParam String gu_name) {
        return martInfoService.findMartInfo(gu_name);
    }

    @GetMapping("/getProductByCuCode")
    public List<String> getProductByCuCode(@RequestParam String gu_name) {
        return martInfoService.getProductByCuCode(gu_name);
    }


    @GetMapping("/findMartLocation")
    public ResponseEntity<String> findMartLocation() {
        // API 키 설정
        String apiKey = "KakaoAK "  + kakaoRestApiKey;
        String url = "https://dapi.kakao.com/v2/local/search/keyword.json"
                + "?y=37.514322572335935&x=127.06283102249932&radius=20000&query=관악신사시장(신림4동)";

        // 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // API 요청 및 응답
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // 응답 반환
        return response;
    }

    @GetMapping("/polygon")
    public ResponseEntity<Resource> ctprvnGetGeoJson() {
        // 경로를 'static/ctprvn-wgs84.json'으로 설정
        Resource resource = resourceLoader.getResource("classpath:static/gu-geojson.json");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(resource);
    }

    @GetMapping("/saveMafraRecipe")
    public void callMAFRARecipeApi(){
        String apiKey = "8d6d9d4bb3d2f6bc550aff59664c8f940d21003e41d7c03303b975b3b17510ab";
        String addr = "http://211.237.50.150:7080/openapi/";
        recipeCardService.callRecipeApiAndSaveData(apiKey, addr);
    }

    @GetMapping("/saveRecipeIngredient")
    public void callRecipeIngredientApi(){
        String apiKey = "2a2d98088a90a23a81db461c5bd31675ca4cb35b994183c8b27182fe01fd45f8";
        String addr = "http://211.237.50.150:7080/openapi/";
        recipeCardService.callRecipeIngredientAndSaveData(apiKey, addr);
    }

}
