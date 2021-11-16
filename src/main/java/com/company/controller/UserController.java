package com.company.controller;

import com.company.dto.*;
import com.company.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "v1/users")
    public ResponseEntity<ResponseDto> getUsers() {
        List<UserDto> users = userService.getUsers();
        log.info("requesting v1/users");
        return ResponseEntity.ok((ResponseDto.of(users, "getting all users")));
    }

    @GetMapping(value = "v1/test1")
    public ResponseEntity<ResponseDto> test1() {
        List<UserDto2> users2 = userService.getUsers2();
        log.info("requesting v1/test1");
        return ResponseEntity.ok((ResponseDto.of(users2, "getting all users with mapper")));
    }

    @GetMapping(value = "v1/users/{id}")
    public ResponseEntity<ResponseDto> getUser(@PathVariable(value = "id", required = false) Long id) {
        log.info("requesting v1/users/{id}");
        UserDto userDto = userService.getById(id);
        return ResponseEntity.ok((ResponseDto.of(userDto, "getting user")));
    }

    //http://localhost:8080/api/v1/users/slice?page=0&size=2
    @GetMapping(value = "v1/users/slice")
    public ResponseEntity<ResponseDto> slice(Pageable pageable) {
        log.info("requesting v1/users/slice");
        List<UserDto> slice = userService.slice(pageable);
        return ResponseEntity.ok((ResponseDto.of(slice, "getting pageable")));

    }

    @GetMapping(value = "v1/user")
    public ResponseEntity<ResponseDto> search(
            @RequestParam(name = "name", required = false) String firstName,
            @RequestParam(name = "surname", required = false) String lastName
    ) {
        log.info("requesting v1/user");
        List<UserDto> userDtos = userService.search(firstName, lastName);
        return ResponseEntity.ok((ResponseDto.of(userDtos, "searching user")));
    }


    @PostMapping(value = "v1/users")
    public ResponseEntity<ResponseDto> addUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) {

        log.info("requesting v1/user");
        CreateUserResponseDto userResponseDto = userService.addUser(createUserRequestDto);
        return ResponseEntity.ok(ResponseDto.of(userResponseDto, "Successfully added new user"));

    }

    @PutMapping(value = "v1/users")
    public ResponseEntity<ResponseDto> updateUser(
            @RequestBody CreateUserRequestDto createUserRequestDto
    ) throws Exception {
        log.info("requesting v1/user");
        CreateUserResponseDto userResponseDto = userService.updateUser(createUserRequestDto);
        return ResponseEntity.ok(ResponseDto.of(userResponseDto, "Successfully updated"));

    }

    @DeleteMapping("v1/users/{id}")
    public ResponseEntity<ResponseDto> removeUser(@PathVariable("id") Long id) throws Exception {

        log.info("requesting v1/user{id}");
        UserDto userDto = userService.removeUser(id);
        return ResponseEntity.ok(ResponseDto.of(userDto, "Successfully deleted"));
    }

    @DeleteMapping("v1/users")
    public ResponseEntity<ResponseDto> removeUsers() {
        log.info("requesting v1/user");
        userService.removeAll();
        return ResponseEntity.ok(ResponseDto.of(null, "Successfully deleted all users"));
    }

}
