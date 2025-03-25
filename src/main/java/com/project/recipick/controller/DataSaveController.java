package com.project.recipick.controller;

import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.service.RecipeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/recipie")
public class DataSaveController {

    @Autowired
    final RecipeOrderService recipeOrderService;

    public DataSaveController(RecipeOrderService recipeOrderService) {
        this.recipeOrderService = recipeOrderService;
    }


    @RequestMapping("saveRecipe")
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


    }

}
