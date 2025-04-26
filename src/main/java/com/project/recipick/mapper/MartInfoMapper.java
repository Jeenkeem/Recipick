package com.project.recipick.mapper;

import com.project.recipick.Entity.MartInfo;
import org.apache.ibatis.annotations.Mapper;
import com.project.recipick.dto.MartItemDTO;

import java.util.List;

@Mapper
public interface MartInfoMapper {
    List<MartInfo> findAll();

    List<MartInfo> findSameMartInfo(String gu_name);

    List<String> getProductByCuCode(String gu_name);

    List<MartItemDTO> selectItemsByMartName(String martName);
}
