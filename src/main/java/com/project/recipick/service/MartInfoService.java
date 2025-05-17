package com.project.recipick.service;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.mapper.MartInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.recipick.dto.MartItemDTO;

import java.util.List;

@Service
public class MartInfoService {

    @Autowired
    final MartInfoMapper martInfoMapper;

    public MartInfoService(MartInfoMapper martInfoMapper) {
        this.martInfoMapper = martInfoMapper;
    }

    public void saveMartInfo(MartInfo martInfo) {
        martInfoMapper.saveMartInfo(martInfo);
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

    public List<MartItemDTO> getMartItemsByMartName(String martName) {
        return martInfoMapper.selectItemsByMartName(martName);
    }

    public List<MartInfo> getIrdntPrice(String ingredient) {

        List<MartInfo> list = martInfoMapper.getIrdntPrice(ingredient);
        System.out.println("service " + list);

        return martInfoMapper.getIrdntPrice(ingredient);
    }

}
