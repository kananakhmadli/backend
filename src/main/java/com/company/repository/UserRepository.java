package com.company.repository;

import com.company.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u from User u where u.email=:em")
    public User findByEmail(@Param("em") String email);

    @Query("SELECT u from User u WHERE u.firstName=:first OR u.lastName=:last")
    public List<User> findByFirstNameOrLastName(@Param("first") String firsName, @Param("last") String lastName);

    @Query("SELECT CASE WHEN count (u.email)> 0 THEN false ELSE true END FROM User u WHERE u.email=:e")
    public boolean checkByEmail(@Param("e") String email);
}
