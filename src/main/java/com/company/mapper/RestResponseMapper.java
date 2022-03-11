package com.company.mapper;

import com.company.dto.response.RestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestResponseMapper {

    RestResponseMapper INSTANCE = Mappers.getMapper(RestResponseMapper.class);

    @Mapping(target = "object", source = "obj")
    @Mapping(target = "message", source = "msj")
    RestResponse toResponse(Object obj, String msj);

}