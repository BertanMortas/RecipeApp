package com.bilgeadam.service;

import com.bilgeadam.dto.request.ChangePasswordRequestDto;
import com.bilgeadam.dto.request.FavoriteRecipeRequestDto;
import com.bilgeadam.dto.request.NewCreateUserRegisterDto;
import com.bilgeadam.dto.request.UpdateUserprofileDto;
import com.bilgeadam.dto.response.FindRecipeByIdResponseDto;
import com.bilgeadam.dto.response.FindUserByCategoryIdResponseDto;
import com.bilgeadam.dto.response.GetProfileResponseDto;
import com.bilgeadam.dto.response.UserForCommentServiceResponseDto;
import com.bilgeadam.entity.Userprofile;
import com.bilgeadam.entity.enums.EStatus;
import com.bilgeadam.exception.ErrorType;
import com.bilgeadam.exception.UserProfileManagerException;
import com.bilgeadam.manager.IAddressManager;
import com.bilgeadam.manager.IAuthManager;
import com.bilgeadam.manager.IRecipeManager;
import com.bilgeadam.mapper.IUserProfileMapper;
import com.bilgeadam.repository.IUserprofileRepository;
import com.bilgeadam.utility.JwtTokenProvider;
import com.bilgeadam.utility.ServiceManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserprofileService extends ServiceManager<Userprofile,String> {
    private final IUserprofileRepository userprofileRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final IAddressManager addressManager;
    private final IAuthManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final IRecipeManager recipeManager;

    public UserprofileService(IUserprofileRepository userprofileRepository, JwtTokenProvider jwtTokenProvider, IAddressManager addressManager, IAuthManager authManager, PasswordEncoder passwordEncoder, IRecipeManager recipeManager) {
        super(userprofileRepository);
        this.userprofileRepository = userprofileRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.addressManager = addressManager;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
        this.recipeManager = recipeManager;
    }

    public Boolean createUser(NewCreateUserRegisterDto dto) {
        save(IUserProfileMapper.INSTANCE.fromDtoToUserNewRequest(dto));
        return true;
    }
    public Userprofile updateUser(UpdateUserprofileDto dto){
//        Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
//        if (authId.isEmpty()) {
//            throw new UserProfileManagerException(ErrorType.INVALID_TOKEN);
//        }
//        Optional<Userprofile> userprofile = userprofileRepository.findByAuthId(authId.get());
//        if (userprofile.isEmpty()) {
//            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
//        }
        Optional<Userprofile> userprofile = tokenToUserprofileWithControls(dto.getToken());
        update(IUserProfileMapper.INSTANCE.updateUserFromDto(dto,userprofile.get()));
        // send to address
        addressManager.updateAddress(IUserProfileMapper.INSTANCE.toUpdateAddressDto(userprofile.get()));
        // send to auth entity
        authManager.updateAuth(IUserProfileMapper.INSTANCE.toUpdateDto(userprofile.get()));
        return userprofile.get();
    }
    public GetProfileResponseDto getMyProfile(String token){
        // tokenToUserprofileWithControls this method do same think
        Userprofile userprofile = tokenToUserprofileWithControls(token).get();
     /*   Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()) {
            throw new UserProfileManagerException(ErrorType.INVALID_TOKEN);
        }*/
        return IUserProfileMapper.INSTANCE.toGetProfileDto(userprofile);
        //return IUserProfileMapper.INSTANCE.toGetProfileDto(userprofileRepository.findByAuthId(authId.get()).get());
    }

    public Boolean updateStatus(String token) { // I use token for safety
        Optional<Userprofile> userprofile = tokenToUserprofileWithControls(token);
        userprofile.get().setStatus(EStatus.ACTIVE);
        update(userprofile.get());
        return true;
    }
    public Boolean delete(String token){
        Optional<Userprofile> userprofile = tokenToUserprofileWithControls(token);
        EStatus status = EStatus.INACTIVE;
        userprofile.get().setStatus(status);
        update(userprofile.get());
        // send to auth service
        authManager.deleteAuth(userprofile.get().getAuthId());
        return true;
    }
    public Boolean passwordChange(ChangePasswordRequestDto dto){
        Optional<Userprofile> userprofile = tokenToUserprofileWithControls(dto.getToken());
        if (!passwordEncoder.matches(dto.getOldPassword(), userprofile.get().getPassword())){
            throw new UserProfileManagerException(ErrorType.PASSWORD_ERROR);
        }
        userprofile.get().setPassword(passwordEncoder.encode(dto.getNewPassword()));
        save(userprofile.get());
        // send to auth
        dto.setNewPassword(userprofile.get().getPassword());
        authManager.updatePassword(dto);
        return true;
    }
    public UserForCommentServiceResponseDto findByAuthId(Long authId){
        Optional<Userprofile> userprofile = userprofileRepository.findByAuthId(authId);
        if (userprofile.isEmpty()) {
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        }
        return IUserProfileMapper.INSTANCE.toUserComment(userprofile.get());
    }
    public Boolean favoriteRecipe(FavoriteRecipeRequestDto dto){
        // todo User save the same category multiple times, when send mail check that
        // TODO !! eğer zamanım kalırsa kullanıcı favoriye tarif eklediği zaman
        //  o kategoriye ait en yüksek puan almış üç tane tarifide ilginizi çekebilir olarak mail atsın

        Optional<Userprofile> userprofile = tokenToUserprofileWithControls(dto.getToken());
        FindRecipeByIdResponseDto responseDto = recipeManager.findRecipeById(dto.getRecipeId()).getBody();
        userprofile.get().getFavoriteRecipeList().add(dto.getRecipeId());
        userprofile.get().getFavoriteRecipeCategoryList().add(responseDto.getCategoryId());
        update(userprofile.get());
        return true;
    }
    public Boolean unFavoriteRecipe(FavoriteRecipeRequestDto dto){
        Optional<Userprofile> userprofile = tokenToUserprofileWithControls(dto.getToken());
        FindRecipeByIdResponseDto responseDto = recipeManager.findRecipeById(dto.getRecipeId()).getBody();
        userprofile.get().getFavoriteRecipeList().remove(dto.getRecipeId());
        userprofile.get().getFavoriteRecipeCategoryList().remove(responseDto.getCategoryId());
        update(userprofile.get());
        return true;
    }
    public List<FindUserByCategoryIdResponseDto> findUserByCategoryId(String categoryId){
        List<Userprofile> userprofileList = userprofileRepository.findAllByFavoriteRecipeCategoryList(categoryId);
        List<FindUserByCategoryIdResponseDto> dtoList = new ArrayList<>();
        userprofileList.stream().forEach(x -> dtoList.add(IUserProfileMapper.INSTANCE.toUserprofile(x)));
        return dtoList;
    }
    public Optional<Userprofile> tokenToUserprofileWithControls(String token){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()) {
            throw new UserProfileManagerException(ErrorType.INVALID_TOKEN);
        }
        Optional<Userprofile> userprofile = userprofileRepository.findByAuthId(authId.get());
        if (userprofile.isEmpty()) {
            throw new UserProfileManagerException(ErrorType.USER_NOT_FOUND);
        }
        return userprofile;
    }

}
