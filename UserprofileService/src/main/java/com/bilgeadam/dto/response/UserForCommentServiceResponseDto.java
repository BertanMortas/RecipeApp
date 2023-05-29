package com.bilgeadam.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserForCommentServiceResponseDto {
    private String id;
    private String username;
    private String name;
    private String surname;
    private String email;
}
