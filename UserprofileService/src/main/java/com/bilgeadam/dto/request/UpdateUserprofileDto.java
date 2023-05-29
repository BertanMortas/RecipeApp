package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserprofileDto {
    private String token;
    private String name;
    private String surname;
    private String username; //TODO username is a unique value, delete this property !!
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
}
