package com.company.service;

import com.company.dto.request.CreateUserRequest;
import com.company.dto.response.CreateUserResponse;
import com.company.dto.request.UpdateUserRequest;
import com.company.dto.UserDto;
import com.company.dto.UserDto2;
import com.company.error.exceptions.UserNotFoundException;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<UserDto> getUsers() {
        log.info("getUsers methods is called");
        return userMapper.toUserDtoList(userRepository.findAll());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<UserDto2> getUsers2() {
        return userMapper.toUserDto2List(userRepository.findAll());
    }

    public UserDto getById(String id) {
        if (id == null)
            throw new UserNotFoundException("ID is null");
        var user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("There is no such user "));
        return userMapper.toUserDto(user);
    }

    public UserDto findByEmail(String email) {
        var user = userRepository.findByEmail(email);
        return userMapper.toUserDto(user);
    }

    public List<UserDto> pagination(int page, int size) {
        var pageable = PageRequest.of(page, size);
        var users = userRepository.findAll(pageable).getContent();
        return userMapper.toUserDtoList(users);
    }

    public List<UserDto> pagination(Pageable pageable) {
        var users = userRepository.findAll(pageable).getContent();
        var all = userRepository.findAll(pageable);
        log.error("Page size is = " + all.getSize());
        return userMapper.toUserDtoList(users);
    }

    public List<UserDto> search(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty())
            firstName = "";
        if (lastName == null || lastName.trim().isEmpty())
            lastName = "";
        var users = userRepository.findByFirstNameOrLastName(firstName, lastName);
        return userMapper.toUserDtoList(users);
    }

    public CreateUserResponse addUser(CreateUserRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        var user = userMapper.toUser(request);
        userRepository.save(user);
        return userMapper.toCreateUserResponseDto(user);
    }

    public void updateUser(UpdateUserRequest request) {
        var user = userRepository.findById(request.getId()).orElseThrow(
                () -> new UserNotFoundException("There is no such a user"));

        assert user != null : "There is no such a user to update";
        user = userMapper.toUser(request, user);
        userRepository.save(user);
    }

    public UserDto removeUser(String id) {
        var user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("There is no such a user to delete"));
        userRepository.deleteById(id);
        return userMapper.toUserDto(user);
    }

    public void removeAll() {
        userRepository.deleteAll();
    }

}