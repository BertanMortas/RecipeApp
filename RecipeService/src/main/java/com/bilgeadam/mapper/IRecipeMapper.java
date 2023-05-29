package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateRecipeRequestDto;
import com.bilgeadam.dto.request.UpdateRecipeRequestDto;
import com.bilgeadam.dto.response.FindRecipeByIdResponseDto;
import com.bilgeadam.dto.response.FindUserByCategoryIdResponseDto;
import com.bilgeadam.entity.Recipe;
import com.bilgeadam.rabbitmq.model.FindUserByCategoryIdModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IRecipeMapper {
    IRecipeMapper INSTANCE = Mappers.getMapper(IRecipeMapper.class);
    Recipe toRecipe(CreateRecipeRequestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Recipe toRecipe(UpdateRecipeRequestDto dto, @MappingTarget Recipe recipe);
    FindRecipeByIdResponseDto toDto(final Recipe recipe);
    FindUserByCategoryIdModel dtoToModel(final FindUserByCategoryIdResponseDto dto);

}
