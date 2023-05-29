package com.bilgeadam.manager;

import com.bilgeadam.dto.request.NewCreateUserRegisterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.bilgeadam.constant.ApiUrls.CREATE;
import static com.bilgeadam.constant.ApiUrls.UPDATE_STATUS;

@FeignClient(name = "userprofile-service", url = "http://localhost:9080/api/v1/user-profile")
public interface IUserprofileManager {
    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUserprofile(@RequestBody NewCreateUserRegisterDto dto);
    @PutMapping(UPDATE_STATUS)
    public ResponseEntity<Boolean> updateStatus(@RequestBody String token);
}
