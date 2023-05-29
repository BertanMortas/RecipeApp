package com.bilgeadam.manager;

import com.bilgeadam.dto.response.FindRecipeByIdResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.bilgeadam.constant.ApiUrls.FIND_BY_ID;

@FeignClient(name = "recipe-service", url = "http://localhost:9070/api/v1/recipe")
public interface IRecipeManager {
    @PostMapping(FIND_BY_ID+"/{recipeId}")
    public ResponseEntity<FindRecipeByIdResponseDto> findRecipeById(@PathVariable String recipeId);
}
