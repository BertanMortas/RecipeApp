package com.bilgeadam.entity;

import lombok.AllArgsConstructor;
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
public class Comment extends Base {
    @Id
    private String id;
    private String username;
    private String comment;
    private String commentDate;
    private String recipeId;
    private String userprofileId;

}
