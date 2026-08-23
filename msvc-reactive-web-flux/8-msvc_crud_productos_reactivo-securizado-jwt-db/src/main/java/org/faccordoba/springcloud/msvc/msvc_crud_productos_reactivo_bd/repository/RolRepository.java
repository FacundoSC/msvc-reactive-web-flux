package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.repository;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.Rol;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RolRepository  extends ReactiveCrudRepository<Rol, Integer> {
    Mono<Rol> findByRol(String rol);
}
