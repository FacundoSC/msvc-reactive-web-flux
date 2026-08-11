package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
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

    @Bean
    public MapReactiveUserDetailsService users() throws Exception{
        //TODO noop o no-op o no operation
        List<UserDetails> userDetailsList = List.
                of(User.withUsername("user1")
                        .password("{noop}user1")
                        .roles("USER").build(),
                        User.withUsername("admin")
                                .password("{noop}admin")
                                .roles("USER", "ADMIN").build(),
                        User.withUsername("user2")
                                .password("{noop}user2")
                                .roles("OPERATOR").build());
        return new MapReactiveUserDetailsService(userDetailsList);
    }




    @Bean
    public SecurityWebFilterChain filter(ServerHttpSecurity http) throws Exception {
        String path="/api/v1/products/";
      http.csrf(ServerHttpSecurity.CsrfSpec::disable)
              .authorizeExchange(authorizeExchangeSpec ->
                       authorizeExchangeSpec
                               .pathMatchers(HttpMethod.GET, path).hasAnyRole("USER")
                               .pathMatchers(HttpMethod.POST, path).hasAnyRole("ADMIN")
                               .pathMatchers(HttpMethod.DELETE, path).hasAnyRole("ADMIN","OPERATOR")
                               .pathMatchers(HttpMethod.PATCH, path).authenticated()
                               .anyExchange().permitAll()
                      )
              .httpBasic(Customizer.withDefaults());
       return http.build();
    }
}
