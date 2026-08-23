package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.dto.UserRequestDto;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Void> saveUser(UserRequestDto requestDto);
    Flux<User> findAll();

}
