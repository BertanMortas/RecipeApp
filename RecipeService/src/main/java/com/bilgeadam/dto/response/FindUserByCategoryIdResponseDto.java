package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindUserByCategoryIdResponseDto {
    private String name;
    private String surname;
    private String username;
    private String email;
    private String recipeName;
}
