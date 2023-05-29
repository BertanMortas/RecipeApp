package com.bilgeadam.entity;

import com.bilgeadam.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Userprofile extends Base implements Serializable {
    @Id
    private String id;
    private Long authId;
    private String name;
    private String surname;
    @Indexed(unique = true)
    private String username;
    private String password;
    @Indexed(unique = true)
    private String email;
    private String avatar;
    private String street;
    private String neighborhood;
    private String district;
    private String state;
    private String buildingNo;
    private String apartNo;
    private String postalCode;
    private String country;
    @Builder.Default
    private EStatus status = EStatus.PENDING;
    private List<String> favoriteRecipeList = new ArrayList<>();
    private List<String> favoriteRecipeCategoryList = new ArrayList<>();
}
