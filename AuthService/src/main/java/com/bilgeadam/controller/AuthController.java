package com.bilgeadam.controller;

import com.bilgeadam.dto.request.*;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.entity.Auth;
import com.bilgeadam.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import static com.bilgeadam.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {
    private final AuthService authService;
    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping(ACTIVATE_STATUS)
    public ResponseEntity<Boolean> activeStatus(@RequestBody StatusRequestDto dto){
        return ResponseEntity.ok(authService.activateStatus(dto));
    }
    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping(FORGOT_PASSWORD)
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequestDto dto){
        return ResponseEntity.ok(authService.forgotPassword(dto));
    }
    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> updateAuth(@RequestBody UpdateAuthRequestDto dto){
        return ResponseEntity.ok(authService.updateAuth(dto));
    }
    @PutMapping(DELETE_BY_ID+"/{authId}")
    public ResponseEntity<Void> deleteAuth(@PathVariable Long authId){
        authService.deleteStatus(authId);
        return ResponseEntity.ok().build();
    }
    @PutMapping(UPDATE_PASSWORD)
    public ResponseEntity<Boolean> updatePassword(@RequestBody ChangePasswordRequestDto dto){
        return ResponseEntity.ok(authService.updatePassword(dto));
    }
}
