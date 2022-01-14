package com.company.mapper;

import com.company.dto.CreateUserRequest;
import com.company.dto.CreateUserResponse;
import com.company.dto.UpdateUserRequest;
import com.company.dto.UserDto;
import com.company.dto.UserDto2;
import com.company.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.List;

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
    @SuppressWarnings({"CommentedOutCode", "unused"})

          @Mapping(target = "fullName", expression = "java(userDto2.concatNames(user.getFirstName(),user.getLastName()))")
          UserDto2 fromUsertoUserDto(User user);
    */
    List<UserDto2> toUserDto2List(List<User> user);

    UserDto toUserDto(User user);

    List<UserDto> toUserDtoList(List<User> user);

    User toUser(CreateUserRequest createUserRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toUser(UpdateUserRequest requestDto, @MappingTarget User user);

    CreateUserResponse toCreateUserResponseDto(User user);

}