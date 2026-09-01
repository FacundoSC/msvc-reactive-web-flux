package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.controller;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.UserLogin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.stream.Collectors;

/**
 * The type Auth controller.
 */
@RestController
public class AuthController {
    private final MapReactiveUserDetailsService detailsService;
    private final static long TTL=85_000_000;
    @Value("${jwtKey}")
    private String JWT_KEY;


    /**
     * Instantiates a new Auth controller.
     *
     * @param detailsService the details service
     */
    public AuthController(MapReactiveUserDetailsService detailsService) {
        this.detailsService = detailsService;
    }

    /**
     * Login mono.
     * metodo para autenticar el usuario, hoy hay una lista, esto podria ir contra una DB.
     * si el usuario es valido, genera un tocken con su informacion y se la envia al cliente
     * para que este la utilice en las llamadas a los recursos
     * @param user the user
     * @return the mono
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> login(@RequestBody UserLogin user){
        return  detailsService.findByUsername(user.user())
                .filter(userDetails -> user.pwd().equals(userDetails.getPassword()))
                .map(userDetails -> new ResponseEntity<>(getToken(userDetails), HttpStatus.OK))
                .switchIfEmpty(Mono.just(new ResponseEntity<>(HttpStatus.UNAUTHORIZED)));
    }





    //genera el token y lo envia al cliente
    private String getToken(UserDetails userDetails){
        //en el body de tocken se incluye el usuario
        // y los roles  a los que pertenece, ademas
        // de la fecha de caducidad y los datos de la firma
        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .subject(userDetails.getUsername()) //usuario
                .issuedAt(new Date())
                .claim("authorities", userDetails.getAuthorities().stream() //roles
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .expiration(new Date(System.currentTimeMillis() + TTL)) // fecha de caducidad
                .signWith(Keys.hmacShaKeyFor(JWT_KEY.getBytes())) // clave y firmar con el algoritmo
                .compact(); //generacion del token
    }

}
