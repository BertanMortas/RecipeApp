package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProfileResponseDto {
    private String name;
    private String surname;
    private String username;
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
