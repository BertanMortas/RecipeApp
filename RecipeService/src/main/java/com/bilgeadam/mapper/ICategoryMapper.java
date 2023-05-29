package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreateCategoryRequestDto;
import com.bilgeadam.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ICategoryMapper {
    ICategoryMapper INSTANCE = Mappers.getMapper(ICategoryMapper.class);
    Category toCategory(final CreateCategoryRequestDto dto);
}
