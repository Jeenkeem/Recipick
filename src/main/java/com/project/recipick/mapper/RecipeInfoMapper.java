package com.project.recipick.mapper;

import com.project.recipick.Entity.RecipeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeInfoMapper {

    List<RecipeInfo> findAll();
}
