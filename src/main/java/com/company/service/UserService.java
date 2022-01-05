package com.company.service;

import com.company.dto.CreateUserRequestDto;
import com.company.dto.CreateUserResponseDto;
import com.company.dto.UserDto;
import com.company.dto.UserDto2;
import com.company.entity.User;
import com.company.error.exceptions.UserNotFoundException;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@SuppressWarnings({"SpringJavaAutowiredFieldsWarningInspection", "unused"})
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<UserDto> getUsers() {
        log.info("getUsers methods is called");
        return userMapper.toUserDtoList(userRepository.findAll());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<UserDto2> getUsers2() {
        return userMapper.toUserDto2List(userRepository.findAll());
    }

    public UserDto getById(Long id) {
        if (id == null)
            throw new UserNotFoundException("ID is null");
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("There is no such user "));
        return userMapper.toUserDto(user);
    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userMapper.toUserDto(user);
    }

    public List<UserDto> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<User> users = userRepository.findAll(pageable).getContent();
        return userMapper.toUserDtoList(users);
    }

    public List<UserDto> pagination(Pageable pageable) {
        List<User> users = userRepository.findAll(pageable).getContent();
        Page<User> all = userRepository.findAll(pageable);
        log.error("Page size is = " + all.getSize());
        return userMapper.toUserDtoList(users);
    }

    public List<UserDto> search(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty())
            firstName = "";
        if (lastName == null || lastName.trim().isEmpty())
            lastName = "";
        List<User> users = userRepository.findByFirstNameOrLastName(firstName, lastName);
        return userMapper.toUserDtoList(users);
    }

    public UserDto removeUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("There is no such a user to delete"));
        userRepository.deleteById(id);
        return userMapper.toUserDto(user);
    }

    public void removeAll() {
        userRepository.deleteAll();
    }

    public CreateUserResponseDto updateUser(CreateUserRequestDto createUserRequestDto) {
        User user = userMapper.toUser(createUserRequestDto);
        assert user != null : "There is no such a user to update";
        userRepository.save(user);
        return userMapper.toCreateUserResponseDto(user);
    }

    public CreateUserResponseDto addUser(CreateUserRequestDto createUserRequestDto) {
        String password = passwordEncoder.encode(createUserRequestDto.getPassword());
        createUserRequestDto.setPassword(password);
        User user = userMapper.toUser(createUserRequestDto);
        userRepository.save(user);
        return userMapper.toCreateUserResponseDto(user);
    }

}