package com.bilgeadam.manager;

import com.bilgeadam.dto.response.FindUserByCategoryIdResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.FIND_BY_CATEGORY_ID;

@FeignClient(name = "Userprofile-to-Recipe-service", url = "http://localhost:9080/api/v1/user-profile")
public interface IUserprofileManager {
    @GetMapping(FIND_BY_CATEGORY_ID+"/{categoryId}")
    public ResponseEntity<List<FindUserByCategoryIdResponseDto>> findUserByCategoryId(@PathVariable String categoryId);
}
