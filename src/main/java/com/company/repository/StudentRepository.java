package com.company.repository;

import com.company.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    @Query("SELECT u from Student u where u.email=:em")
    Student findByEmail(@Param("em") String email);

    @Query("SELECT u from Student u WHERE u.firstName=:first OR u.lastName=:last")
    List<Student> findByFirstNameOrLastName(@Param("first") String firsName, @Param("last") String lastName);

    @Query("SELECT CASE WHEN count (u.email)> 0 THEN false ELSE true END FROM Student u WHERE u.email=:e")
    boolean checkByEmail(@Param("e") String email);

}