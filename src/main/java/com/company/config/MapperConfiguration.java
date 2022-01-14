package com.company.config;

import com.company.mapper.RestResponseMapper;
import com.company.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }

    @Bean
    public RestResponseMapper responseMapper() {
        return RestResponseMapper.INSTANCE;
    }

}