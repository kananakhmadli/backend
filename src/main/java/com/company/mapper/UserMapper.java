package com.company.mapper;

import com.company.dto.CreateUserRequestDto;
import com.company.dto.CreateUserResponseDto;
import com.company.dto.UserDto;
import com.company.dto.UserDto2;
import com.company.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@SuppressWarnings({"CommentedOutCode", "unused"})
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Named("concatNames")
    default String concatNames(User user) {
        return "Hey " + user.getFirstName() + " " + user.getLastName();
    }

    @Mapping(target = "name", source = "user.firstName")
    @Mapping(target = "surname", source = "user.lastName")
    @Mapping(target = "fullName", source = ".", qualifiedByName = "concatNames")
    UserDto2 toUserDto2(User user);

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