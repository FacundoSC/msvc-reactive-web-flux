package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.Rol;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.repository.RolRepository;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final RolRepository rolRepository;
    private final UserRepository userRepository;

    public  UserDetailsServiceImpl(RolRepository rolRepository, UserRepository userRepository) {
        this.rolRepository = rolRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Mono<UserDetails> findByName(String name) {
        return null;
    }

    @Override
    public Mono<UserDetails> findByUsername(String name) {
        return userRepository
                .findByUsername(name) //Mono<Usuario>
                .flatMap(user -> rolRepository
                        .findById(user.getRolId())    // Flux<Rol>
                        .map(Rol::getRol) //Flux<String>
                        .map(rol -> User.withUsername(user.getUsername())
                                .password(user.getPassword())
                                .roles(rol)
                                .build()
                        )) //Mono<UserDetails>
                .switchIfEmpty(Mono.empty());// Mono<UserDetails>
    }
}
