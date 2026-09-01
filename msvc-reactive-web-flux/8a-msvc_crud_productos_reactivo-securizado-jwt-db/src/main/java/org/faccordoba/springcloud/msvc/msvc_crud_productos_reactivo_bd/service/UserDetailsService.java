package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service;

import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

public interface UserDetailsService {
 Mono<UserDetails> findByName(String name);

 Mono<UserDetails> findByUsername(String name);
}
