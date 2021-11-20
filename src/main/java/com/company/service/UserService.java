package com.company.service;

import com.company.dto.CreateUserRequestDto;
import com.company.dto.CreateUserResponseDto;
import com.company.dto.UserDto;
import com.company.dto.UserDto2;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public List<UserDto> getUsers();

    public List<UserDto2>getUsers2();

    public UserDto getById(Long id);

    public UserDto findByEmail(String email);

    public List<UserDto> slice(Pageable pageable);

    public List<UserDto> search(String firsName, String lastName);

    public UserDto removeUser(Long id) throws Exception;

    public void removeAll();

    public CreateUserResponseDto updateUser(CreateUserRequestDto createUserRequestDto) throws Exception;

    public CreateUserResponseDto addUser(CreateUserRequestDto createUserRequestDto);
}
