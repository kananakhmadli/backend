package com.company.mapper;

import com.company.dto.RestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RestResponseMapper {

    RestResponseMapper INSTANCE = Mappers.getMapper(RestResponseMapper.class);

    @Mapping(target = "object", source = "obj")
    @Mapping(target = "message", source = "msj")
    RestResponse toResponse(Object obj, String msj);

}