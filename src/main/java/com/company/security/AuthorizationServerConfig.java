package com.company.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.List;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Value("${security.clientId}")
    private String clientId;

    @Value("${security.clientSecret}")
    private String clientSecret;

    @Value("${security.resourceId}")
    private String resourceId;

    private final TokenStore tokenStore;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtAccessTokenConverter accessTokenConverter;

    @Override
    public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
        configurer
                .inMemory()
                .withClient(clientId)
                .secret(passwordEncoder.encode(clientSecret))
                .authorizedGrantTypes("password")
                .scopes("read", "write")
                .resourceIds(resourceId);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        var enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(List.of(accessTokenConverter));
        endpoints
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter)
                .tokenEnhancer(enhancerChain)
                .authenticationManager(authenticationManager);
    }

}