package com.company.config;

import com.company.mapper.RestResponseMapper;
import com.company.mapper.StudentMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public StudentMapper studentMapper() {
        return StudentMapper.INSTANCE;
    }

    @Bean
    public RestResponseMapper responseMapper() {
        return RestResponseMapper.INSTANCE;
    }

}