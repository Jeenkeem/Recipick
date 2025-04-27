package com.project.recipick.service;

import com.project.recipick.dto.RecipeCardDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface RecipeCardService {
    void callRecipeApiAndSaveData(String apiKey, String addr);

    List<RecipeCardDTO> getRecipeCardList();
}
