package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.config;


import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.security.SecurityContextRepository;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service.UserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;


@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
public class
SecurityConfig {
    private final SecurityContextRepository securityContextRepository;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(SecurityContextRepository securityContextRepository, UserDetailsService userDetailsService) {
        this.securityContextRepository = securityContextRepository;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public ReactiveUserDetailsService users() throws Exception{
        return userDetailsService::findByUsername;
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
