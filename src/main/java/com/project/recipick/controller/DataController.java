package com.project.recipick.controller;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.service.MartInfoService;
import com.project.recipick.service.RecipeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipick")
public class DataController {

    @Autowired
    final RecipeOrderService recipeOrderService;

    @Autowired
    final MartInfoService martInfoService;

    public DataController(RecipeOrderService recipeOrderService, MartInfoService martInfoService) {
        this.recipeOrderService = recipeOrderService;
        this.martInfoService = martInfoService;
    }

    @Autowired
    private RestTemplate restTemplate;


    @Value("${kakao.restapi.key}")
    private String kakaoRestApiKey;

    @GetMapping("/saveRecipe")
    public void SaveRecipe() {

        RecipeInfo re = new RecipeInfo();
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

        List<RecipeInfo> list = new ArrayList<>();
        list.add(re);

        recipeOrderService.saveRecipe(list);

    }

    @GetMapping("/getAllMartInfo")
    public List<MartInfo> getAllMartInfo() {
        return martInfoService.getAllMartInfo();
    }

    @GetMapping("/getSameMartInfo")
    public List<MartInfo> getMartInfo(@RequestParam String M_NAME) {
        return martInfoService.findMartInfo(M_NAME);
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

    @GetMapping("/initMartData")
    public List<MartInfo> getPresetMarts(){
        List<String> martNames = List.of("관악신사시장(신림4동)", "대명여울빛거리시장", "은행나무시장", "영동전통시장");

        return martInfoService.getMartInfoByMartNames(martNames);
    }



}
