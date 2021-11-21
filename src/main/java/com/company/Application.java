package com.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.ObjectError;

import java.util.Locale;
import java.util.Objects;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }


//    @Autowired
//    private MessageSource messageSource;
//
//    @Bean
//    public CommandLineRunner run() throws Exception {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//                ObjectError objectError = new ObjectError("hey", "yes");
//                String message = messageSource.getMessage(Objects.requireNonNull(objectError.getDefaultMessage()),
//                        objectError.getArguments(), Locale.ENGLISH);
//                System.out.println(message);
//
//            }
//
//        };
//    }

}