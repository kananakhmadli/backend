package com.company.controller;

import com.company.dto.request.CreateStudentRequest;
import com.company.dto.request.UpdateStudentRequest;
import com.company.dto.response.RestResponse;
import com.company.mapper.RestResponseMapper;
import com.company.service.StudentService;
import lombok.RequiredArgsConstructor;
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

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final RestResponseMapper responseMapper;

    @PostConstruct
    public void init() {
        var student1 = new CreateStudentRequest();
        student1.setFirstName("Huseyin");
        student1.setLastName("Huseyinli");
        student1.setAge(21);
        student1.setEmail("huseyin.huseyinli@gmail.com");
        student1.setPassword("12345");
        studentService.addStudent(student1);

        var student2 = new CreateStudentRequest();
        student2.setFirstName("Kanan");
        student2.setLastName("Akhmadli");
        student2.setAge(20);
        student2.setEmail("kanan.akhmadli@gmail.com");
        student2.setPassword("12345");
        studentService.addStudent(student2);
    }

    @GetMapping(value = "v1/students")
    public ResponseEntity<RestResponse> getStudent() {
        var users = studentService.getStudents();
        return ResponseEntity.ok((responseMapper.toResponse(users, "getting all students")));
    }

    @GetMapping(value = "v1/students/{id}")
    public ResponseEntity<RestResponse> getStudent(@PathVariable(required = false) String id) {
        var userDto = studentService.getById(id);
        return ResponseEntity.ok((responseMapper.toResponse(userDto, "getting student")));
    }

    @GetMapping(value = "v1/student")
    public ResponseEntity<RestResponse> search(
            @RequestParam(name = "name", required = false) String firstName,
            @RequestParam(name = "surname", required = false) String lastName) {
        var userDtoList = studentService.search(firstName, lastName);
        return ResponseEntity.ok((responseMapper.toResponse(userDtoList, "searching student")));
    }

    //http://localhost:8089/api/v1/students/pagination/v1?page=0&size=2
    @GetMapping(value = "v1/students/pagination/v1")
    public ResponseEntity<RestResponse> pagination(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size) {
        var slice = studentService.pagination(page, size);
        return ResponseEntity.ok((responseMapper.toResponse(slice, "getting pageable1")));
    }

    //http://localhost:8089/api/v1/students/pagination/v2?page=0&size=2
    @GetMapping(value = "v1/students/pagination/v2")
    public ResponseEntity<RestResponse> pagination(Pageable pageable) {
        var slice = studentService.pagination(pageable);
        return ResponseEntity.ok((responseMapper.toResponse(slice, "getting pageable2")));
    }

    @GetMapping(value = "v1/test1")
    public ResponseEntity<RestResponse> test1() {
        var users2 = studentService.getStudent2();
        return ResponseEntity.ok((responseMapper.toResponse(users2, "getting all students with mapper")));
    }

    @PostMapping(value = "v1/students")
    public ResponseEntity<RestResponse> addStudent(@Valid @RequestBody CreateStudentRequest request) {
        var userResponseDto = studentService.addStudent(request);
        return ResponseEntity.ok(responseMapper.toResponse(userResponseDto, "Successfully added new student"));
    }

    @PutMapping(value = "v1/students")
    public ResponseEntity<RestResponse> updateStudent(@RequestBody UpdateStudentRequest request) {
        studentService.updateStudent(request);
        return ResponseEntity.ok(responseMapper.toResponse(null, "Successfully updated"));
    }

    @DeleteMapping("v1/students/{id}")
    public ResponseEntity<RestResponse> removeStudent(@PathVariable String id) {
        var userDto = studentService.removeStudent(id);
        return ResponseEntity.ok(responseMapper.toResponse(userDto, "Successfully deleted"));
    }

    @DeleteMapping("v1/students")
    public ResponseEntity<RestResponse> removeStudents() {
        studentService.removeAll();
        return ResponseEntity.ok(responseMapper.toResponse(null, "Successfully deleted all students"));
    }

}