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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


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
    
}
