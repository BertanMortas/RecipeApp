package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.NewCreateUserRegisterDto;
import com.bilgeadam.dto.request.UpdateAddressDto;
import com.bilgeadam.dto.request.UpdateAuthRequestDto;
import com.bilgeadam.dto.request.UpdateUserprofileDto;
import com.bilgeadam.dto.response.FindUserByCategoryIdResponseDto;
import com.bilgeadam.dto.response.GetProfileResponseDto;
import com.bilgeadam.dto.response.UserForCommentServiceResponseDto;
import com.bilgeadam.entity.Userprofile;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IUserProfileMapper {
    IUserProfileMapper INSTANCE = Mappers.getMapper(IUserProfileMapper.class);
    Userprofile fromDtoToUserNewRequest(NewCreateUserRegisterDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Userprofile updateUserFromDto(UpdateUserprofileDto dto, @MappingTarget Userprofile userprofile);
    UpdateAddressDto toUpdateAddressDto(final Userprofile userprofile);
    UpdateAuthRequestDto toUpdateDto(final Userprofile userprofile);
    GetProfileResponseDto toGetProfileDto(final Userprofile userprofile);
    UserForCommentServiceResponseDto toUserComment(final Userprofile userprofile);
    FindUserByCategoryIdResponseDto toUserprofile(final Userprofile userprofile);
}
