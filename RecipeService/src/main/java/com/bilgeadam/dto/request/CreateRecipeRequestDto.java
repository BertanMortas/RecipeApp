package com.bilgeadam.dto.request;

import com.bilgeadam.entity.Ingredient;
import com.bilgeadam.entity.NutritionalValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecipeRequestDto {
    private String token;
    private String name;
    private String type;
    private String preparationTime;
    private String cookedTime;
    private String recipeInfos;
    private String photoUrl;
    private String comment;
    private Ingredient ingredient;
    private NutritionalValue nutritionalValue;
    private String categoryId;
}
