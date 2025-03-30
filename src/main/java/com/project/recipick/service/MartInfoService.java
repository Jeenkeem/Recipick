package com.project.recipick.service;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.mapper.MartInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MartInfoService {

    @Autowired
    final MartInfoMapper martInfoMapper;

    public MartInfoService(MartInfoMapper martInfoMapper) {
        this.martInfoMapper = martInfoMapper;
    }

    public List<MartInfo> getAllMartInfo() {
        return martInfoMapper.findAll();
    }



}
