package com.project.recipick.mapper;

import com.project.recipick.Entity.MartInfo;
import com.project.recipick.Entity.MartNameAndLocation;
import org.apache.ibatis.annotations.Mapper;
import com.project.recipick.dto.MartItemDTO;

import java.util.List;

@Mapper
public interface MartInfoMapper {
    List<MartInfo> findAll();

    List<MartNameAndLocation> getAllMartName();

    List<MartInfo> findSameMartInfo(String gu_name);

    List<String> getProductByCuCode(String gu_name);

    List<MartItemDTO> selectItemsByMartName(String martName);

    List<MartInfo> getIrdntPrice(String ingredient);

    List<MartInfo> findByMName(String martName);

    void saveMartInfo(MartInfo martInfo);
}
