package com.bilgeadam.manager;

import com.bilgeadam.dto.response.UserForCommentServiceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static com.bilgeadam.constant.ApiUrls.FIND_BY_ID;

@FeignClient(name = "userprofile-service", url = "http://localhost:9080/api/v1/user-profile")
public interface IUserprofileManager {
    @PostMapping(FIND_BY_ID+"/authId"+"/{authId}")
    public ResponseEntity<UserForCommentServiceResponseDto> getMyProfile(@PathVariable Long authId);
}
