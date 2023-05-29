package com.bilgeadam.controller;

import com.bilgeadam.dto.request.ChangePasswordRequestDto;
import com.bilgeadam.dto.request.FavoriteRecipeRequestDto;
import com.bilgeadam.dto.request.NewCreateUserRegisterDto;
import com.bilgeadam.dto.request.UpdateUserprofileDto;
import com.bilgeadam.dto.response.FindUserByCategoryIdResponseDto;
import com.bilgeadam.dto.response.GetProfileResponseDto;
import com.bilgeadam.dto.response.UserForCommentServiceResponseDto;
import com.bilgeadam.entity.Userprofile;
import com.bilgeadam.entity.enums.EStatus;
import com.bilgeadam.service.UserprofileService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bilgeadam.constant.ApiUrls.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserprofileController {
    private final UserprofileService userprofileService;

    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUserprofile(@RequestBody NewCreateUserRegisterDto dto){
        return ResponseEntity.ok(userprofileService.createUser(dto));
    }
    @PostMapping(UPDATE)
    public ResponseEntity<Userprofile> updateUserprofile(UpdateUserprofileDto dto){
        return ResponseEntity.ok(userprofileService.updateUser(dto));
    }
    @GetMapping(FIND_BY_ID+"/{token}")
    public ResponseEntity<GetProfileResponseDto> getMyProfile(@PathVariable String token){
        return ResponseEntity.ok(userprofileService.getMyProfile(token));
    }
    @PutMapping(UPDATE_STATUS)
    public ResponseEntity<Boolean> updateStatus(String token){
        return ResponseEntity.ok(userprofileService.updateStatus(token));
    }
    @PutMapping(DELETE_BY_ID)
    public ResponseEntity<Boolean> deleteUserprofile(String token){
        return ResponseEntity.ok(userprofileService.delete(token));
    }
    @PutMapping(UPDATE_PASSWORD)
    public ResponseEntity<Boolean> updatePassword(@RequestBody ChangePasswordRequestDto dto){
        return ResponseEntity.ok(userprofileService.passwordChange(dto));
    }
    @PostMapping(FIND_BY_ID+"/authId"+"/{authId}")
    public ResponseEntity<UserForCommentServiceResponseDto> getMyProfile(@PathVariable Long authId){
        return ResponseEntity.ok(userprofileService.findByAuthId(authId));
    }
    @PostMapping(ADD_FAVORITE)
    public ResponseEntity<Boolean> favoriteRecipe(FavoriteRecipeRequestDto dto){
        return ResponseEntity.ok(userprofileService.favoriteRecipe(dto));
    }
    @PostMapping(UN_ADD_FAVORITE)
    public ResponseEntity<Boolean> unFavoriteRecipe(FavoriteRecipeRequestDto dto){
        return ResponseEntity.ok(userprofileService.unFavoriteRecipe(dto));
    }
    @GetMapping(FIND_BY_CATEGORY_ID+"/{categoryId}")
    public ResponseEntity<List<FindUserByCategoryIdResponseDto>> findUserByCategoryId(@PathVariable String categoryId){
        return ResponseEntity.ok(userprofileService.findUserByCategoryId(categoryId));
    }
}
