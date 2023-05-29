package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressDto {
    private Long authId;
    private String street;
    private String neighborhood;
    private String district;
    private String state;
    private String buildingNo;
    private String apartNo;
    private String postalCode;
    private String country;
}
