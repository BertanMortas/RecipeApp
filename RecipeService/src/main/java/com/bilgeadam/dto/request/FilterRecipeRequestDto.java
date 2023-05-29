package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterRecipeRequestDto {
    private String categoryId;
    private String name;
    private String ingredientsName;
    private String ingredientsQuantity;
    private String nutritionalValueCalorie;
}
