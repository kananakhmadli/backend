package com.company.controller;

import com.company.dto.CreateUserRequestDto;
import com.company.dto.CreateUserResponseDto;
import com.company.dto.ResponseDto;
import com.company.dto.UserDto;
import com.company.dto.UserDto2;
import com.company.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "v1/users")
    public ResponseEntity<ResponseDto> getUsers() {
        List<UserDto> users = userService.getUsers();
        return ResponseEntity.ok((ResponseDto.of(users, "getting all users")));
    }

    @GetMapping(value = "v1/test1")
    public ResponseEntity<ResponseDto> test1() {
        List<UserDto2> users2 = userService.getUsers2();
        return ResponseEntity.ok((ResponseDto.of(users2, "getting all users with mapper")));
    }

    @GetMapping(value = "v1/users/{id}")
    public ResponseEntity<ResponseDto> getUser(@PathVariable(value = "id", required = false) Long id) {
        UserDto userDto = userService.getById(id);
        return ResponseEntity.ok((ResponseDto.of(userDto, "getting user")));
    }

    //http://localhost:8080/api/v1/users/pagination/v1?page=0&size=2
    @GetMapping(value = "v1/users/pagination/v1")
    public ResponseEntity<ResponseDto> pagination(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        List<UserDto> slice = userService.pagination(page, size);
        return ResponseEntity.ok((ResponseDto.of(slice, "getting pageable1")));
    }

    //http://localhost:8080/api/v1/users/pagination/v2?page=0&size=2
    @GetMapping(value = "v1/users/pagination/v2")
    public ResponseEntity<ResponseDto> pagination(Pageable pageable) {
        List<UserDto> slice = userService.pagination(pageable);
        return ResponseEntity.ok((ResponseDto.of(slice, "getting pageable2")));
    }

    @GetMapping(value = "v1/user")
    public ResponseEntity<ResponseDto> search(
            @RequestParam(name = "name", required = false) String firstName,
            @RequestParam(name = "surname", required = false) String lastName
    ) {
        List<UserDto> userDtos = userService.search(firstName, lastName);
        return ResponseEntity.ok((ResponseDto.of(userDtos, "searching user")));
    }

    @PostMapping(value = "v1/users")
    public ResponseEntity<ResponseDto> addUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) {
        CreateUserResponseDto userResponseDto = userService.addUser(createUserRequestDto);
        return ResponseEntity.ok(ResponseDto.of(userResponseDto, "Successfully added new user"));
    }

    @PutMapping(value = "v1/users")
    public ResponseEntity<ResponseDto> updateUser(
            @RequestBody CreateUserRequestDto createUserRequestDto
    ) {
        CreateUserResponseDto userResponseDto = userService.updateUser(createUserRequestDto);
        return ResponseEntity.ok(ResponseDto.of(userResponseDto, "Successfully updated"));
    }

    @DeleteMapping("v1/users/{id}")
    public ResponseEntity<ResponseDto> removeUser(@PathVariable("id") Long id) {
        UserDto userDto = userService.removeUser(id);
        return ResponseEntity.ok(ResponseDto.of(userDto, "Successfully deleted"));
    }

    @DeleteMapping("v1/users")
    public ResponseEntity<ResponseDto> removeUsers() {
        userService.removeAll();
        return ResponseEntity.ok(ResponseDto.of(null, "Successfully deleted all users"));
    }
}