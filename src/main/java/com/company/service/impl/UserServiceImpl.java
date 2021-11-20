package com.company.service.impl;

import com.company.dto.CreateUserRequestDto;
import com.company.dto.CreateUserResponseDto;
import com.company.dto.UserDto;
import com.company.dto.UserDto2;
import com.company.entity.User;
import com.company.error.exceptions.UserNotFoundException;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  UserMapper userMapper;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<UserDto> getUsers() {
        log.info("getUsers methods is called");
        List<User> users = userRepository.findAll();
        return userMapper.toUserDtoList(users);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<UserDto2> getUsers2() {
        return userMapper.toUserDto2List(userRepository.findAll());
    }

    @Override
    public UserDto getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("There is no such a user to delete"));
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return userMapper.toUserDto(user);
    }

    @Override
    public List<UserDto> slice(Pageable pageable) {
        List<User> users = userRepository.findAll(pageable).getContent();
        return userMapper.toUserDtoList(users);
    }

    @Override
    public List<UserDto> search(String firstName, String lastName) {

        if (firstName == null || firstName.trim().isEmpty())
            firstName = "";

        if (lastName == null || lastName.trim().isEmpty())
            lastName = "";

        List<User> users = userRepository.findByFirstNameOrLastName(firstName, lastName);
        return userMapper.toUserDtoList(users);
    }

    @Override
    public UserDto removeUser(Long id) { //done
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("There is no such a user to delete"));
        userRepository.deleteById(id);
        return userMapper.toUserDto(user);
    }

    @Override
    public void removeAll() {
        userRepository.deleteAll(); //done
    }

    @Override
    public CreateUserResponseDto updateUser(CreateUserRequestDto createUserRequestDto) {
        User user = userMapper.toUser(createUserRequestDto);
        if (user == null) {
            throw new UserNotFoundException("There is no such a user to update");
        }
        userRepository.save(user);
        return userMapper.toCreateUserResponseDto(user);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public CreateUserResponseDto addUser(CreateUserRequestDto createUserRequestDto) { //done
        String password = passwordEncoder.encode(createUserRequestDto.getPassword());
        createUserRequestDto.setPassword(password);

        User user = userMapper.toUser(createUserRequestDto);
        userRepository.save(user);
        return userMapper.toCreateUserResponseDto(user);
    }
}
