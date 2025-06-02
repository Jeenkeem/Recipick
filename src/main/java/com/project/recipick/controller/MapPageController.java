package com.project.recipick.controller;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.Entity.MartNameAndLocation;
import com.project.recipick.service.MartInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;


@Controller
public class MapPageController {
    
    @Value("${kakao.api.key}")
    private String kakaoApiKey;


    @Autowired
    final MartInfoService martInfoService;

    public MapPageController(MartInfoService martInfoService) {
        this.martInfoService = martInfoService;
    }

    @GetMapping("/recipick/mapPage")
    public String getMethodName(Model model) {

        List<MartNameAndLocation> list = martInfoService.getAllMartName();

        ArrayList<String> martNames = new ArrayList<>();
        for(MartNameAndLocation arr : list) {
            martNames.add(arr.getmName());
        }

        model.addAttribute("kakaoApiKey", kakaoApiKey);
        model.addAttribute("martNames", martNames);

        return "map/mapPage";
    }

    @GetMapping("/recipick/mapPageIrdnt")
    public String mapPage(@RequestParam String irdntNames, Model model) {

        model.addAttribute("kakaoApiKey", kakaoApiKey);

        String[] names = irdntNames.split(",");
        model.addAttribute("irdntsList", Arrays.asList(names));
        return "map/mapPageIrdnt";
    }


    static class Location {
        private double latitude;
        private double longitude;

        // getters and setters
        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }

    @PostMapping("/location")
    public ResponseEntity<String> updateLocation(@RequestBody Location location) {
        System.out.println("Received latitude: " + location.getLatitude() + ", longitude: " + location.getLongitude());
        return ResponseEntity.ok("Location received");
    }

    @PostMapping("/recipick/select")
    public ResponseEntity<?> selectIngredient(@RequestBody Map<String, String> request) {
        String ingredient = request.get("ingredient");
        System.out.println("선택된 식재료: " + ingredient);

        // 로직 수행
        List<MartInfo> list = martInfoService.getIrdntPrice(ingredient);


        return ResponseEntity.ok(list);
    }

    @PostMapping("/recipick/selectAll")
    public ResponseEntity<?> selectMultipleIngredients(@RequestBody Map<String, List<String>> request) {
        List<String> ingredients = request.get("ingredients");
        System.out.println("선택된 전체 식재료: " + ingredients);

        // 마트별로 어떤 식재료가 있는지 저장할 Map
        Map<String, MartTotal> martMap = new HashMap<>();

        for (String ingredient : ingredients) {
            List<MartInfo> dataList = martInfoService.getIrdntPrice(ingredient);

            for (MartInfo info : dataList) {
                String key = info.getmName();


                // 이미 등록된 마트이면 가격과 식재료 개수 누적
                if (martMap.containsKey(key)) {
                    MartTotal mart = martMap.get(key);
                    mart.addIngredient(ingredient, info.getaPrice());
                } else {
                    MartTotal total = new MartTotal(info.getmName(), info.getmGuName());
                    total.addIngredient(ingredient, info.getaPrice());
                    martMap.put(key, total);
                }
            }
        }

        // 모든 식재료를 포함한 마트만 필터링
        List<MartTotal> result = martMap.values().stream()
                .filter(mart -> mart.containsAllIngredients(ingredients))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @GetMapping("/recipick/martInfo")
    public ResponseEntity<?> getMartInfo(@RequestParam String martName) {

        List<MartInfo> result = new ArrayList<>();
        result = martInfoService.findByMName(martName);

        return ResponseEntity.ok(result);
    }

    public static class MartTotal {
        private String mName;
        private String mGuName;
        private int totalPrice;
        private Set<String> ingredientsIncluded;

        public MartTotal(String mName, String mGuName) {
            this.mName = mName;
            this.mGuName = mGuName;
            this.totalPrice = 0;
            this.ingredientsIncluded = new HashSet<>();
        }

        public void addIngredient(String ingredient, int priceStr) {
            this.ingredientsIncluded.add(ingredient);
            this.totalPrice += priceStr;
        }

        public boolean containsAllIngredients(List<String> requiredIngredients) {
            return this.ingredientsIncluded.containsAll(requiredIngredients);
        }

        public String getmName() { return mName; }
        public String getmGuName() {
            return mGuName;
        }
        public int getTotalPrice() { return totalPrice; }
        public Set<String> getIngredientsIncluded() { return ingredientsIncluded; }

    }


}
