package com.company.service.impl;

import com.company.dto.CreateUserRequestDto;
import com.company.dto.CreateUserResponseDto;
import com.company.dto.UserDto;
import com.company.dto.UserDto2;
import com.company.entity.User;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public UserMapper userMapper;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<UserDto> getUsers() {
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

        User user = userRepository.findById(id).get();
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
    public UserDto removeUser(Long id) throws Exception { //done
        User user = userRepository.findById(id).get();

        if (user == null) {
            throw new Exception("There is no such a user to delete");
        }
        userRepository.deleteById(id);
        return userMapper.toUserDto(user);

    }

    @Override
    public void removeAll() {

        userRepository.deleteAll(); //done
    }

    @Override
    public CreateUserResponseDto updateUser(CreateUserRequestDto createUserRequestDto) throws Exception {
        User user = userMapper.toUser(createUserRequestDto);
//        User user=userRepository.findById(id).get();
        if (user == null) {
            throw new Exception("There is no such a user to update");
        }

        userRepository.save(user);
        return userMapper.toCreateUserResponseDto(user);

    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public CreateUserResponseDto addUser(CreateUserRequestDto createUserRequestDto) { //done

        String password = passwordEncoder.encode(createUserRequestDto.getPassword());
        createUserRequestDto.setPassword(password);

        User user = userMapper.toUser(createUserRequestDto);
        userRepository.save(user);
        return userMapper.toCreateUserResponseDto(user);


    }

}
