package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.UpdateAddressDto;
import com.bilgeadam.entity.Address;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IAddressMapper {
    IAddressMapper INSTANCE = Mappers.getMapper(IAddressMapper.class);
    Address toAddress(final UpdateAddressDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address updateAddressFromDto(UpdateAddressDto dto, @MappingTarget Address address);
}
