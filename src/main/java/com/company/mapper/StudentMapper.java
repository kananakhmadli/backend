package com.company.mapper;

import com.company.dto.StudentDto;
import com.company.dto.StudentDto2;
import com.company.dto.request.CreateStudentRequest;
import com.company.dto.request.UpdateStudentRequest;
import com.company.dto.response.CreateStudentResponse;
import com.company.entity.Student;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    @Named("concatNames")
    default String concatNames(Student student) {
        return "Hey " + student.getFirstName() + " " + student.getLastName();
    }

    @Mapping(target = "name", source = "student.firstName")
    @Mapping(target = "surname", source = "student.lastName")
    @Mapping(target = "fullName", source = ".", qualifiedByName = "concatNames")
    StudentDto2 toStudentDto2(Student student);

    /*
    @SuppressWarnings({"CommentedOutCode", "unused"})

          @Mapping(target = "fullName", expression = "java(userDto2.concatNames(user.getFirstName(),user.getLastName()))")
          UserDto2 fromUsertoUserDto(User user);
    */
    List<StudentDto2> toStudentDto2List(List<Student> students);

    StudentDto toStudentDto(Student student);

    List<StudentDto> toStudentDtoList(List<Student> students);

    Student toStudent(CreateStudentRequest createStudentRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Student toStudent(UpdateStudentRequest requestDto, @MappingTarget Student student);

    CreateStudentResponse toCreateStudentResponseDto(Student student);

}