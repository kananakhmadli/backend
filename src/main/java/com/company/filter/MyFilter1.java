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

@Component
@Order(2)
@Slf4j
@Profile(ApplicationConstants.SPRING_PROFILE_LOCAL)
public class MyFilter1 implements Filter {

    @Value("${api.message.local}")
    private String message;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Filter1 is called");
        log.warn(message);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}