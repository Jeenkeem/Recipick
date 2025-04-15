package com.project.recipick.controller;

import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class IndexController {

    @Autowired
    final RecipeService recipeOrderService;

    public IndexController(RecipeService recipeOrderService) {
        this.recipeOrderService = recipeOrderService;
    }

    @GetMapping("/recipick/main")
    public String getMethodName() {
        List<RecipeInfo> recipeInfoList = recipeOrderService.findAllRecipe();

        for(int i=0; i<recipeInfoList.size(); i++) {
            System.out.println(recipeInfoList.get(i).getRECIPE_NM_KO());
        }

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

        return "mainPage/main";
    }
    

}
