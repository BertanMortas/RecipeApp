package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CreatePointRequestDto;
import com.bilgeadam.dto.request.UpdatePointRequestDto;
import com.bilgeadam.entity.Point;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IPointMapper {
    IPointMapper INSTANCE = Mappers.getMapper(IPointMapper.class);

    @Mapping(target = "id", ignore = true)
    Point toPoint(final CreatePointRequestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Point toPoint(final UpdatePointRequestDto dto, @MappingTarget Point point);
}
