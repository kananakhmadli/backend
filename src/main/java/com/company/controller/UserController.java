package com.company.controller;

import com.company.dto.CreateUserRequest;
import com.company.dto.CreateUserResponse;
import com.company.dto.RestResponse;
import com.company.dto.UpdateUserRequest;
import com.company.dto.UserDto;
import com.company.dto.UserDto2;
import com.company.mapper.RestResponseMapper;
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
@RequestMapping(value = "/")
@Validated
public class UserController {

    private final UserService userService;
    private final RestResponseMapper responseMapper;

    public UserController(UserService userService, RestResponseMapper responseMapper) {
        this.userService = userService;
        this.responseMapper = responseMapper;
    }

    @GetMapping(value = "v1/users")
    public ResponseEntity<RestResponse> getUsers() {
        List<UserDto> users = userService.getUsers();
        return ResponseEntity.ok((responseMapper.toResponse(users, "getting all users")));
    }


    @GetMapping(value = "v1/users/{id}")
    public ResponseEntity<RestResponse> getUser(@PathVariable(required = false) String id) {
        UserDto userDto = userService.getById(id);
        return ResponseEntity.ok((responseMapper.toResponse(userDto, "getting user")));
    }

    @GetMapping(value = "v1/user")
    public ResponseEntity<RestResponse> search(
            @RequestParam(name = "name", required = false) String firstName,
            @RequestParam(name = "surname", required = false) String lastName) {
        List<UserDto> userDtos = userService.search(firstName, lastName);
        return ResponseEntity.ok((responseMapper.toResponse(userDtos, "searching user")));
    }

    //http://localhost:8080/api/v1/users/pagination/v1?page=0&size=2
    @GetMapping(value = "v1/users/pagination/v1")
    public ResponseEntity<RestResponse> pagination(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        List<UserDto> slice = userService.pagination(page, size);
        return ResponseEntity.ok((responseMapper.toResponse(slice, "getting pageable1")));
    }

    //http://localhost:8080/api/v1/users/pagination/v2?page=0&size=2
    @GetMapping(value = "v1/users/pagination/v2")
    public ResponseEntity<RestResponse> pagination(Pageable pageable) {
        List<UserDto> slice = userService.pagination(pageable);
        return ResponseEntity.ok((responseMapper.toResponse(slice, "getting pageable2")));
    }

    @GetMapping(value = "v1/test1")
    public ResponseEntity<RestResponse> test1() {
        List<UserDto2> users2 = userService.getUsers2();
        return ResponseEntity.ok((responseMapper.toResponse(users2, "getting all users with mapper")));
    }


    @PostMapping(value = "v1/users")
    public ResponseEntity<RestResponse> addUser(
            @Valid @RequestBody CreateUserRequest createUserRequest) {
        CreateUserResponse userResponseDto = userService.addUser(createUserRequest);
        return ResponseEntity.ok(responseMapper.toResponse(userResponseDto, "Successfully added new user"));
    }

    @PutMapping(value = "v1/users")
    public ResponseEntity<RestResponse> updateUser(
            @RequestBody UpdateUserRequest updateUserRequest) {

        userService.updateUser(updateUserRequest);
        return ResponseEntity.ok(responseMapper.toResponse(null, "Successfully updated"));
    }

    @DeleteMapping("v1/users/{id}")
    public ResponseEntity<RestResponse> removeUser(@PathVariable String id) {
        UserDto userDto = userService.removeUser(id);
        return ResponseEntity.ok(responseMapper.toResponse(userDto, "Successfully deleted"));
    }

    @DeleteMapping("v1/users")
    public ResponseEntity<RestResponse> removeUsers() {
        userService.removeAll();
        return ResponseEntity.ok(responseMapper.toResponse(null, "Successfully deleted all users"));
    }

}