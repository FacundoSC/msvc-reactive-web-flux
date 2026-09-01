package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.repository;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
   @Query("SELECT * FROM users WHERE username = :username")
   Mono<User> findByUsername(@Param("username") String username);
}
