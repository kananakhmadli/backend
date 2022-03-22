package com.company.security;

import com.company.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentService studentService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var student = studentService.findByEmail(email);
        UserBuilder builder;
        log.info(student.toString());
        builder = org.springframework.security.core.userdetails.User.withUsername(email);
        builder.disabled(false);
        builder.password(student.getPassword());
        String[] authorities = {"ADMIN"};
        log.info(Arrays.toString(authorities));
        log.info("++++++++++++++++++++++++++++++++++++++++++++++++");
        builder.authorities(authorities);
        return builder.build();
    }

}