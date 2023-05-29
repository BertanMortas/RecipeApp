package com.bilgeadam.manager;

import com.bilgeadam.dto.request.ChangePasswordRequestDto;
import com.bilgeadam.dto.request.UpdateAuthRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bilgeadam.constant.ApiUrls.DELETE_BY_ID;
import static com.bilgeadam.constant.ApiUrls.UPDATE;

@FeignClient(name = "auth-service", url = "http://localhost:9090/api/v1/auth")
public interface IAuthManager {
    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> updateAuth(@RequestBody UpdateAuthRequestDto dto);
    @PutMapping(DELETE_BY_ID+"/{authId}")
    public ResponseEntity<Void> deleteAuth(@PathVariable Long authId);
    @PutMapping("/update-password")
    public ResponseEntity<Boolean> updatePassword(@RequestBody ChangePasswordRequestDto dto);
}
