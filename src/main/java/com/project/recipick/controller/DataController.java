package com.project.recipick.controller;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.Entity.RecipeInfo;
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

    @Value("${martinfoapi.key}")
    private String martInfoApiKey;

    @GetMapping("/saveRecipe")
    public void SaveRecipe() throws ParseException {
        int batchSize = 1000;
        int start = 1;
        int end = batchSize;
        boolean hasMore = true;
        ArrayList<RecipeInfo> list = new ArrayList<>();

        JSONParser parser = new JSONParser();

        while (hasMore) {

            System.out.println("start: " + start + ", end: " + end);

            String apiUrl = "http://211.237.50.150:7080/openapi/2a2d98088a90a23a81db461c5bd31675ca4cb35b994183c8b27182fe01fd45f8/json/Grid_20150827000000000226_1/" + start + "/" + end;

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(apiUrl, String.class);
            String responseBody = responseEntity.getBody();

            JSONObject jsonObj = (JSONObject) parser.parse(responseBody);
            String Grid = jsonObj.get("Grid_20150827000000000226_1").toString();
            JSONObject jsonObjGrid = (JSONObject) parser.parse(Grid);

            // row가 없거나, 빈 배열이면 반복 종료
            if (!jsonObjGrid.containsKey("row")) {
                break;
            }
            String row = jsonObjGrid.get("row").toString();
            JSONArray jsonArray = (JSONArray) parser.parse(row);

            if (jsonArray.size() == 0) {
                hasMore = false;
            } else {
                for (int i = 0; i < jsonArray.size(); i++) {
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
                // 다음 페이지로 이동
                start += batchSize;
                end += batchSize;
            }
        }
        // 다 모은 후 저장
        recipeOrderService.saveRecipe(list);
        System.out.println("전체 레시피 저장 완료: " + list.size() + "건");
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

    @GetMapping("/saveMartInfo")
    public void saveMartInfo() throws ParseException {
        String apiKey =  martInfoApiKey;

        int pageSize = 1000;
        int start = 1;
        int lastEnd = 0;

        while (true) {
            int end = start + pageSize - 1;
            lastEnd = end;

            String url = "http://openAPI.seoul.go.kr:8088/" + apiKey + "/json/ListNecessariesPricesService/" + start + "/" + end + "/?P_YEAR_MONTH=2025-06";

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            String responseBody = responseEntity.getBody();

            String jsonString = responseBody;
            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            String ListNecessariesPricesService = jsonObject.get("ListNecessariesPricesService").toString();

            if (ListNecessariesPricesService == null) break; // 서비스 자체가 없으면 종료


            JSONObject jsonObj = (JSONObject) parser.parse(ListNecessariesPricesService);
            String row = jsonObj.get("row").toString();

            if (row == null) break; // 서비스 자체가 없으면 종료

            JSONArray jsonArray = (JSONArray) parser.parse(row);

            if (jsonArray.isEmpty()) break; // 빈 배열이면 종료

            boolean hasTargetMonth = false;

            System.out.println(jsonArray.get(0));

            for(int i=0; i<jsonArray.size(); i++) {
                JSONObject obj = (JSONObject) jsonArray.get(i);

                // 가격이 '0'이면 저장하지 않음
                String price = obj.get("A_PRICE").toString();
                if ("0".equals(price)) continue;

                String pYearMonth = obj.get("P_YEAR_MONTH").toString();
                if ("2025-06".equals(pYearMonth)) {
                    hasTargetMonth = true;

                    MartInfo martInfo = new MartInfo();

                    martInfo.setpSeq(obj.get("P_SEQ").toString());
                    martInfo.setmSeq(obj.get("M_SEQ").toString());
                    martInfo.setmName(obj.get("M_NAME").toString());
                    martInfo.setaSeq(obj.get("A_SEQ").toString());
                    martInfo.setaName(obj.get("A_NAME").toString());
                    martInfo.setaUnit(obj.get("A_UNIT").toString());
                    martInfo.setaPrice(price);
                    martInfo.setpYearMonth(obj.get("P_YEAR_MONTH").toString());
                    martInfo.setAddCol(obj.get("ADD_COL").toString());
                    martInfo.setpDate(obj.get("P_DATE").toString());
                    martInfo.setmTypeCode(obj.get("M_TYPE_CODE").toString());
                    martInfo.setmTypeName(obj.get("M_TYPE_NAME").toString());
                    martInfo.setmGuCode(obj.get("M_GU_CODE").toString());
                    martInfo.setmGuName(obj.get("M_GU_NAME").toString());

                    System.out.println("start & end = " + start + ", " + lastEnd);
                    martInfoService.saveMartInfo(martInfo);
                }

            }

            // 이번 페이지에 2025-06이 없으면 반복 종료
            if (!hasTargetMonth) break;

            // 다음 페이지로 이동
            start += pageSize;
        }


        System.out.println("endpage = " + lastEnd);
    }


}
