package com.bilgeadam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionalValue {
    private String name;
    private String calorie;
    private String protein;
    private String carbonHydrate;
    private String fat;
}
