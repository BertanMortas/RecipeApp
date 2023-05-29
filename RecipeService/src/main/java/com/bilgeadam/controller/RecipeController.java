package com.bilgeadam.controller;

import com.bilgeadam.dto.request.CreateRecipeRequestDto;
import com.bilgeadam.dto.request.DeleteRecipeRequestDto;
import com.bilgeadam.dto.request.FilterRecipeRequestDto;
import com.bilgeadam.dto.request.UpdateRecipeRequestDto;
import com.bilgeadam.dto.response.FindRecipeByIdResponseDto;
import com.bilgeadam.entity.Base;
import com.bilgeadam.entity.Recipe;
import com.bilgeadam.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(RECIPE)
public class RecipeController {
    private final RecipeService recipeService;
    @PostMapping(CREATE)
    public ResponseEntity<Recipe> createRecipe(@RequestBody CreateRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.createRecipe(dto));
    }
    @PutMapping(UPDATE+"/{recipeId}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable String recipeId, @RequestBody UpdateRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.updateRecipe(recipeId, dto));
    }
    @PutMapping(DELETE_BY_ID)//TODO reCreate end point we need because admin can bring back the recipe
    public ResponseEntity<Boolean> deleteRecipe(@RequestBody DeleteRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.deleteRecipe(dto));
    }
    @GetMapping(FIND_BY_FILTER)
    public ResponseEntity<List<Recipe>> recipeFilter(FilterRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.recipeFilter(dto));
    }
    @PostMapping(FIND_BY_ID+"/{recipeId}")
    public ResponseEntity<FindRecipeByIdResponseDto> findRecipeById(@PathVariable String recipeId){
        return ResponseEntity.ok(recipeService.findRecipeById(recipeId));
    }
}
