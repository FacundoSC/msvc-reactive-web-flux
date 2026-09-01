package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.controller;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.Rol;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.repository.RolRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/roles")
public class RolesController {

    private final RolRepository repository;

    public RolesController(RolRepository repository, RolRepository repository1) {
        this.repository = repository1;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Mono<Rol>> save(){
        Rol rol = new Rol();
        rol.setRol("admin");
        return  ResponseEntity.ok(repository.save(rol));
    }



}
