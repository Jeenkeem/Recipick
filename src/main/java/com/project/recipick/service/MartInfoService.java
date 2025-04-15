package com.project.recipick.service;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.Entity.Product;
import com.project.recipick.Entity.RecipeInfo;
import com.project.recipick.mapper.MartInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.project.recipick.dto.MartItemDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class MartInfoService {

    @Autowired
    final MartInfoMapper martInfoMapper;

    @Autowired
    private RestTemplate restTemplate;

    public MartInfoService(MartInfoMapper martInfoMapper) {
        this.martInfoMapper = martInfoMapper;
    }

    public List<MartInfo> getAllMartInfo() {
        return martInfoMapper.findAll();
    }

    public List<MartInfo> findMartInfo(String gu_name) {
        return martInfoMapper.findSameMartInfo(gu_name);
    }

    public List<String> getProductByCuCode(String gu_name) {


        List<String> guNameList = martInfoMapper.getProductByCuCode(gu_name);

        for(String name : guNameList) {

            System.out.println(name);

        }
        return guNameList;
    }

    public List<MartItemDto> getMartItemsByMartName(String martName) {
        return martInfoMapper.selectItemsByMartName(martName);
    }

}
