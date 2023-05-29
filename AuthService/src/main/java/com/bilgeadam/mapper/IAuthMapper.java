package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.NewCreateUserRegisterDto;
import com.bilgeadam.dto.request.RegisterRequestDto;
import com.bilgeadam.dto.request.UpdateAuthRequestDto;
import com.bilgeadam.dto.response.RegisterResponseDto;
import com.bilgeadam.entity.Auth;
import com.bilgeadam.rabbitmq.model.MailRegisterModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);
    Auth toAuth(final RegisterRequestDto dto);
    RegisterResponseDto toResponse(final Auth auth);
    MailRegisterModel toModel(final Auth auth);
    @Mapping(source = "id",target = "authId")
    NewCreateUserRegisterDto toRegisterDto(final Auth auth);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Auth updateAuthFromDto(UpdateAuthRequestDto dto, @MappingTarget Auth auth);
}
