package com.company.filter;

import com.company.config.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
@Order(1)
@Component
@Profile(ApplicationConstants.SPRING_PROFILE_PROD)
public class MyFilter2 implements Filter {

    @Value("${api.message}")
    private String message;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Filter2 is called");
        log.warn(message);
        filterChain.doFilter(servletRequest, servletResponse);
    }

}