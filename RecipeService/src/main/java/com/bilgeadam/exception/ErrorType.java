package com.bilgeadam.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum ErrorType {
    INTERNAL_ERROR(5100, "Sunucu Hatası", HttpStatus.INTERNAL_SERVER_ERROR),
    BAD_REQUEST(4000, "Parametre Hatası", HttpStatus.BAD_REQUEST),
    AUTHORIZATION_ERROR(4300, "you have no permission to create recipe", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(4400, "Böyle bir kullanıcı bulunamadı", HttpStatus.NOT_FOUND),
    RECIPE_NOT_FOUND(4400, "recipe not found", HttpStatus.NOT_FOUND),
    USER_NAME_NOT_CREATED(4100, "Böyle bir kullanıcı oluşturulamadı", HttpStatus.BAD_REQUEST),
    TOKEN_NOT_FOUND(4404, "Böyle bir token bulunamadı", HttpStatus.NOT_FOUND),
    INVALID_TOKEN(4405, "Böyle bir token geçerli değildir", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(4500, "there is no such a category", HttpStatus.BAD_REQUEST),
    FOLLOW_ALREADY_EXIST(4450, "takip isteği daha önce oluşturulmuş", HttpStatus.BAD_REQUEST),
    PASSWORD_ERROR(4900, "şifreler aynı değil değiştirmek için aynı şifreleri giriniz", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    HttpStatus httpStatus;
}
