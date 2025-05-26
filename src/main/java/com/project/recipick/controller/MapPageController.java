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

        // 전체 마트 정보를 식재료별로 가져옴
        List<MartInfo> allData = new ArrayList<>();
        for (String ingredient : ingredients) {
            List<MartInfo> data = martInfoService.getIrdntPrice(ingredient);
            allData.addAll(data);
        }

        for(int i=0; i<allData.size(); i++) {
            System.out.println(allData.get(i).getmName());
            System.out.println(allData.get(i).getaName());
        }



        // 마트+자치구를 하나의 키로 묶어서 가격 정보 집계
        Map<String, List<MartInfo>> martGrouped = new HashMap<>();
        for (MartInfo item : allData) {
            String key = item.getmName() + "::" + item.getmGuName(); // 마트명+자치구명으로 묶기
            martGrouped.computeIfAbsent(key, k -> new ArrayList<>()).add(item);
        }

        ArrayList<MartTotal> result = new ArrayList<>();

        // 마트별로 <마트이름, Map<식재료이름, MartInfo>> 형태로 정리
        Map<String, Map<String, MartInfo>> martMap = new HashMap<>();

        for (MartInfo info : allData) {
            String martName = info.getmName();
            String ingredient = info.getaName();

            martMap.putIfAbsent(martName, new HashMap<>());
            martMap.get(martName).put(ingredient, info);
        }

        System.out.println("모든 식재료를 갖춘 마트 정보:");
        for (Map.Entry<String, Map<String, MartInfo>> entry : martMap.entrySet()) {
            String martName = entry.getKey();
            Map<String, MartInfo> ingredientMap = entry.getValue();

            MartTotal martTotal = new MartTotal();

            // 현재 마트가 모든 식재료를 갖추고 있는지 확인
            boolean hasAllIngredients = ingredients.stream().allMatch(ingredientMap::containsKey);

            if (hasAllIngredients) {
                int totalPrice = 0;
                String district = "";

                for (String ingredient : ingredients) {
                    MartInfo info = ingredientMap.get(ingredient);
                    totalPrice += info.getaPrice();
                    district = info.getmGuName(); // 같은 마트니까 아무 식재료의 district 써도 됨

                }

                martTotal.setMartName(martName);
                martTotal.setDistrict(district);
                martTotal.setPrice(totalPrice);

                System.out.println("마트: " + martName);
                System.out.println("자치구: " + district);
                System.out.println("총합: " + totalPrice + "원");
                System.out.println("-------------------------");
            }
        }


        return ResponseEntity.ok(result);
    }

    class MartTotal {
        String martName;
        String district;
        int price;

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getMartName() {
            return martName;
        }

        public void setMartName(String martName) {
            this.martName = martName;
        }
    }


}
