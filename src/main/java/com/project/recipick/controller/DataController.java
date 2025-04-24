package com.project.recipick.controller;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.service.MartInfoService;
import com.project.recipick.service.RecipeDetailService;
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
    private final ResourceLoader resourceLoader;

    public DataController(RecipeDetailService recipeOrderService, MartInfoService martInfoService, ResourceLoader resourceLoader) {
        this.recipeOrderService = recipeOrderService;
        this.martInfoService = martInfoService;
        this.resourceLoader = resourceLoader;
    }

    @Autowired
    private RestTemplate restTemplate;


    @Value("${kakao.restapi.key}")
    private String kakaoRestApiKey;

    @GetMapping("/saveRecipe")
    public void SaveRecipe() {

        RecipeInfo re = new RecipeInfo();
        /*
        re.setRECIPE_ID(1);
        re.setRECIPE_NM_KO("나물");
        re.setSUMRY("Hello world");
        re.setNATION_CODE("AA");
        re.setNATION_NM("Ko");
        re.setTY_CODE("23");
        re.setTY_NM("sample");
        re.setCOOKING_TIME("30분");
        re.setCALORIE("300");
        re.setQNT("dhjke");
        re.setLEVEL_NM("LOW");
        re.setIRDNT_CODE("jelo");
        re.setPC_NM("helo");

         */

        List<RecipeInfo> list = new ArrayList<>();
        list.add(re);

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

}
