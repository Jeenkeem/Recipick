package com.project.recipick.service.ServiceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.recipick.Entity.RecipeCardEntity;
import com.project.recipick.config.AppConfig;
import com.project.recipick.dto.RecipeCardDTO;
import com.project.recipick.mapper.RecipeCardMapper;
import com.project.recipick.service.RecipeCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class RecipeCardServiceImpl implements RecipeCardService {
    @Autowired
    AppConfig appConfig;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RecipeCardMapper recipeCardMapper;

    @Override
    public List<RecipeCardDTO> getRecipeCardList(){
        return recipeCardMapper.getRecipeCardList();
    }

    public void callRecipeApiAndSaveData(String apiKey, String addr){
        String requestURL = addr + apiKey + "/json/Grid_20150827000000000226_1/1/537";
        System.out.println("요청주소---------------"+ requestURL);

        int totalCnt = 0;
        try {
            // 2. API 요청 보내기
            String response = appConfig.restTemplate().getForObject(requestURL, String.class);

            // 3. JSON 파싱
            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode gridData = rootNode.path("Grid_20150827000000000226_1");

            totalCnt = gridData.path("totalCnt").asInt();
            System.out.println("총 데이터: "+totalCnt+"-------");

            // 4. "row" 데이터만 추출
            JsonNode rowNode = gridData.path("row");

            // 5. "row" 데이터를 Recipe 객체 리스트로 변환
            List<RecipeCardEntity> recipes = objectMapper.readerForListOf(RecipeCardEntity.class).readValue(rowNode);

            // 6. DB에 저장
            if (!recipes.isEmpty()) {
                recipeCardMapper.insertMAFRARecipes(recipes);
                System.out.println("농림축산식품 레시피 데이터 저장 완료");
            } else {
                System.out.println("저장할 레시피 데이터가 없습니다.");
            }

        } catch (Exception e) {
            System.out.println("레시피 API 호출 및 저장 중 에러 발생");
        }
    }
}
