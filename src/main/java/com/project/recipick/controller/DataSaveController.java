package com.project.recipick.controller;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.service.MartInfoService;
import com.project.recipick.service.RecipeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipick")
public class DataSaveController {

    @Autowired
    final RecipeOrderService recipeOrderService;

    @Autowired
    final MartInfoService martInfoService;

    public DataSaveController(RecipeOrderService recipeOrderService, MartInfoService martInfoService) {
        this.recipeOrderService = recipeOrderService;
        this.martInfoService = martInfoService;
    }


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

    @GetMapping("/saveMartInfo")
    public void saveMartInfo() {

        MartInfo ma = new MartInfo();

        ma.setP_SEQ("10");
        ma.setM_SEQ("120");
        ma.setM_NAME("10");
        ma.setA_SEQ("10");
        ma.setA_NAME("10");
        ma.setA_UNIT("10");
        ma.setA_PRICE("10");
        ma.setP_YEAR_MONTH("10");
        ma.setADD_COL("10");
        ma.setP_DATE("10");
        ma.setM_TYPE_CODE("10");
        ma.setM_TYPE_NAME("10");
        ma.setM_GU_CODE("10");
        ma.setM_GU_NAME("10");


        List<MartInfo> list = new ArrayList<>();
        list.add(ma);

        MartInfoService.saveMartInfo(list);

    }

}
