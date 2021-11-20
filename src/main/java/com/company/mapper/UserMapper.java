package com.company.mapper;

import com.company.dto.CreateUserRequestDto;
import com.company.dto.CreateUserResponseDto;
import com.company.dto.UserDto;
import com.company.dto.UserDto2;
import com.company.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Named("concatNames")
    default String concatNames(User user) {
        return "Hey " + user.getFirstName() + " " + user.getLastName();
    }

    @Mappings({
            @Mapping(source = "user.firstName", target = "name"),
            @Mapping(source = "user.lastName", target = "surname"),
            @Mapping(target = "fullName", source = ".", qualifiedByName = "concatNames")
    })
    UserDto2 toUserDto2_1(User user);

    /*
          @Mapping(target = "fullName", expression = "java(userDto2.concatNames(user.getFirstName(),user.getLastName()))")
          UserDto2 fromUsertoUserDto(User user);
    */
    List<UserDto2> toUserDto2List(List<User> user);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> user);

    User toUser(CreateUserRequestDto createUserRequestDto);

    CreateUserResponseDto toCreateUserResponseDto(User user);
}
