package com.company.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

@Configuration
@EnableResourceServer
@RequiredArgsConstructor
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${security.resourceId}")
    private String resourceId;

    private final ResourceServerTokenServices tokenServices;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId(resourceId)
                .tokenServices(tokenServices);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .requestMatchers()
                .and()
                .authorizeRequests().antMatchers("/users").hasAnyAuthority("ADMIN")
                .antMatchers("/h2-console**/**").permitAll()
                .antMatchers("/home/**").permitAll()
                .antMatchers("/**").authenticated();
    }

}