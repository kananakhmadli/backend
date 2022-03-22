package com.company.service;

import com.company.dto.StudentDto;
import com.company.dto.StudentDto2;
import com.company.dto.request.CreateStudentRequest;
import com.company.dto.request.UpdateStudentRequest;
import com.company.dto.response.CreateStudentResponse;
import com.company.error.exceptions.StudentNotFoundException;
import com.company.mapper.StudentMapper;
import com.company.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<StudentDto> getStudents() {
        log.info("getStudents methods is called");
        return studentMapper.toStudentDtoList(studentRepository.findAll());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<StudentDto2> getStudent2() {
        return studentMapper.toStudentDto2List(studentRepository.findAll());
    }

    public StudentDto getById(String id) {
        if (id == null)
            throw new StudentNotFoundException("ID is null");
        var student = studentRepository.findById(id).orElseThrow(
                () -> new StudentNotFoundException("There is no such student "));
        return studentMapper.toStudentDto(student);
    }

    public StudentDto findByEmail(String email) {
        return studentMapper.toStudentDto(studentRepository.findByEmail(email));
    }

    public List<StudentDto> pagination(Integer page, Integer size) {
        var pageable = PageRequest.of(page, size);
        var students = studentRepository.findAll(pageable).getContent();
        return studentMapper.toStudentDtoList(students);
    }

    public List<StudentDto> pagination(Pageable pageable) {
        var students = studentRepository.findAll(pageable).getContent();
        var all = studentRepository.findAll(pageable);
        log.error("Page size is = " + all.getSize());
        return studentMapper.toStudentDtoList(students);
    }

    public List<StudentDto> search(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty())
            firstName = "";
        if (lastName == null || lastName.trim().isEmpty())
            lastName = "";
        var students = studentRepository.findByFirstNameOrLastName(firstName, lastName);
        return studentMapper.toStudentDtoList(students);
    }

    public CreateStudentResponse addStudent(CreateStudentRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        var student = studentRepository.save(studentMapper.toStudent(request));
        return studentMapper.toCreateStudentResponseDto(student);
    }

    public void updateStudent(UpdateStudentRequest request) {
        var student = studentRepository.findById(request.getId()).orElseThrow(
                () -> new StudentNotFoundException("There is no such student"));

        assert student != null : "There is no such student to update";
        student = studentMapper.toStudent(request, student);
        studentRepository.save(student);
    }

    public StudentDto removeStudent(String id) {
        var student = studentRepository.findById(id).orElseThrow(
                () -> new StudentNotFoundException("There is no such student to delete"));
        studentRepository.deleteById(id);
        return studentMapper.toStudentDto(student);
    }

    public void removeAll() {
        studentRepository.deleteAll();
    }

}