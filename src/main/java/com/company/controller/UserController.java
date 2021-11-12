package com.company.controller;

import com.company.dto.*;
import com.company.exception.MyException;
import com.company.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    public UserService userService;


    public Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "v1/users", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto> getUsers() {
        List<UserDto> users = userService.getUsers();
        log.info("requesting v1/users");
//        log.warn(String.valueOf(ProcessHandle.current().pid()));

        return ResponseEntity.ok((ResponseDto.of(users, "getting all users")));
    }

    @RequestMapping(value = "v1/foo", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto> foo() {
        List<UserDto2> users2 = userService.getUsers2();
        return ResponseEntity.ok((ResponseDto.of(users2, "getting all users with mapper")));
    }

    @RequestMapping(value = "v1/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto> getUser(@PathVariable(value = "id", required = false) Long id) {

        UserDto userDto = userService.getById(id);
        return ResponseEntity.ok((ResponseDto.of(userDto, "getting user")));
    }

    //http://localhost:8080/api/v1/users/slice?page=0&size=2
    @RequestMapping(value = "v1/users/slice", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto> slice(Pageable pageable) {

        List<UserDto> slice = userService.slice(pageable);
        return ResponseEntity.ok((ResponseDto.of(slice, "getting pageable")));

    }

    @RequestMapping(value = "v1/user", method = RequestMethod.GET)
    public ResponseEntity<ResponseDto> search(
            @RequestParam(name = "name", required = false) String firstName,
            @RequestParam(name = "surname", required = false) String lastName
    ) {

        List<UserDto> userDtos = userService.search(firstName, lastName);
        return ResponseEntity.ok((ResponseDto.of(userDtos, "searching user")));
    }


    @PostMapping(value = "v1/users")
    public ResponseEntity<ResponseDto> addUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) throws MyException {

        CreateUserResponseDto userResponseDto = userService.addUser(createUserRequestDto);

        return ResponseEntity.ok(ResponseDto.of(userResponseDto, "Successfully added new user"));

    }

    @PutMapping(value = "v1/users")
    public ResponseEntity<ResponseDto> updateUser(
            @RequestBody CreateUserRequestDto createUserRequestDto
    ) throws Exception {

        CreateUserResponseDto userResponseDto = userService.updateUser(createUserRequestDto);
        return ResponseEntity.ok(ResponseDto.of(userResponseDto, "Successfully updated"));

    }

    @DeleteMapping("v1/users/{id}")
    public ResponseEntity<ResponseDto> removeUser(@PathVariable("id") Long id) throws Exception {

        UserDto userDto = userService.removeUser(id);

        return ResponseEntity.ok(ResponseDto.of(userDto, "Successfully deleted"));
    }

    @DeleteMapping("v1/users")
    public ResponseEntity<ResponseDto> removeUsers() {
        userService.removeAll();
        return ResponseEntity.ok(ResponseDto.of(null, "Successfully deleted all users"));
    }

}
