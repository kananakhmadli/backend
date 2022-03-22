package com.company;

import com.company.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
//@SpringBootApplication(exclude = {
//        SecurityAutoConfiguration.class,
//        ManagementWebSecurityAutoConfiguration.class
//})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication app = new SpringApplication(Application.class);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }
        String serverPort = env.getProperty("local.server.port");
        String contextPath = env.getProperty("server.servlet.context-path");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info("Application '{}' is running! Access URLs: Local: {}://localhost:{}{} " +
                        "External: {}://{}:{}{} Profile(s): {}",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles());
    }

    @Autowired
    private StudentService studentService;

    @Bean
    public CommandLineRunner run() {
        return args -> {
            var user = studentService.findByEmail("kanan.akhmadli@gmail.com");
            log.info("User --> {}", user);
        };
    }

}