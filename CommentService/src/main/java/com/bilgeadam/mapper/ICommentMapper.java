package com.bilgeadam.mapper;

import com.bilgeadam.dto.request.CommentUpdateRequestDto;
import com.bilgeadam.dto.request.CreateCommentRequestDto;
import com.bilgeadam.entity.Comment;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ICommentMapper {
    ICommentMapper INSTANCE = Mappers.getMapper(ICommentMapper.class);
    @Mapping(target = "id", ignore = true)
    Comment toComment(final CreateCommentRequestDto dto);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment toComment(final CommentUpdateRequestDto dto, @MappingTarget Comment comment);

}
