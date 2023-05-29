package com.bilgeadam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Recipe extends Base {
    @Id
    private String id;
    private String name;
    private String type;
    private String preparationTime;
    private String cookedTime;
    private String recipeInfos;
    private String photoUrl;
    private String comment; // it might be commentIds ???
    private Ingredient ingredient;
    private NutritionalValue nutritionalValue;
    private String categoryId; // todo change dto names too
    @Builder.Default
    private Boolean visibility= true;
}
