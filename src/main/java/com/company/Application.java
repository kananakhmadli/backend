package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public CommandLineRunner run() throws Exception {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//
//            }
//
//        };
//    }

}
