package com.bilgeadam.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentUpdateRequestDto {
    private String id;
    private String token;
    @Pattern(regexp = "^((?!(fuck|bitch)).)*$")
    private String comment;
    private String recipeId;
}
