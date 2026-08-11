package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.config;


import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.security.SecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    private final SecurityContextRepository securityContextRepository;

    public SecurityConfig(SecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public MapReactiveUserDetailsService users() throws Exception{
        List<UserDetails> userDetailsList = List.
                of(User.withUsername("user1")
                        .password("user1")
                        .roles("USER").build(),
                        User.withUsername("admin")
                                .password("admin")
                                .roles("USER", "ADMIN").build(),
                        User.withUsername("user2")
                                .password("user2")
                                .roles("OPERATOR").build());
        return new MapReactiveUserDetailsService(userDetailsList);
    }




    @Bean
    public SecurityWebFilterChain filter(ServerHttpSecurity http) throws Exception {
        String path = "/api/v1/products/";
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authenticationManager(securityContextRepository.getAuthenticManager())
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(authorizeExchangeSpec ->
                        authorizeExchangeSpec
                                .pathMatchers(HttpMethod.GET, path).hasAnyRole("USER")
                                .pathMatchers(HttpMethod.POST, path).hasAnyRole("ADMIN")
                                .pathMatchers(HttpMethod.DELETE, path).hasAnyRole("ADMIN", "OPERATOR")
                                .pathMatchers(HttpMethod.PATCH, path).authenticated()
                                .anyExchange().permitAll()
                ).build();
    }
}
