package com.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class Application {

    public static void main(String[] args) {
//        enigma
//        SpringApplication.run(Application.class, args);
        SpringApplication app=new SpringApplication(Application.class);
        Environment env = app.run(args).getEnvironment();
        String s=env.getProperty("spring.application.name");
        System.out.println(s);

    }


//    @Autowired
//    private MessageSource messageSource;

//    @Value("${api-message-error}")
//    private String message;

//    @Bean
//    public CommandLineRunner run() throws Exception {
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... args) throws Exception {
//
//                System.out.println("Message= "+message );
//            }
//
//        };
//    }

}