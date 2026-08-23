package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.controller;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.dto.UserRequestDto;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.User;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.repository.UserRepository;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public  UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Mono<Void>> save(@RequestBody UserRequestDto userDto ) {
        System.out.println("metodo save");
        return  ResponseEntity.ok(userService.saveUser(userDto));
    }

    @GetMapping(value = "/")
    public ResponseEntity<Flux<User>> all(){
        return ResponseEntity.ok(userService.findAll());
    }


}
