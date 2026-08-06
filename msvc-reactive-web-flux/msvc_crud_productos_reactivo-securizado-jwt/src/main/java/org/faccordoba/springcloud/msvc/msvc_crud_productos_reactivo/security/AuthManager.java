package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;


/**
 * The type Auth manager.
 */
@Component
public class AuthManager implements ReactiveAuthenticationManager {
    @Value("${jwtKey}")
    private String JWT_KEY;
    @Value("${bearer}")
    private String BEARER;

    //primeramente, transforma el Mono <Authentication> en un Mono<Claims>, incluyendo la informacion
    // recibida en el token JWT. Despues, ese Mono<Authentication> es transformado en un nuevo Mono<Authentication>
    // generado a partir de un UserPasswordAutenticationToken que se configura
    // a partir del usuario y roles del token
    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        //TODO el objeto authentication tiene el JWT del request.
        //TODO utilizamos la JWT_KEY y las credenciales del objeto auth para compara el token del request con el que generamos en backend
        //TODO si las credenciales no son validas , devolvemos un Mono vacio , pero si lo son , usamos el claim del jwts parse para  crear
        //TODO un nuevo Mono<Autentication> en el que pasamos  el subject y las lista de roles.
        return Mono.just(authentication)
                .map(auth -> Jwts.parser()
                        .verifyWith(Keys.hmacShaKeyFor(JWT_KEY.getBytes()))
                        .build()
                        .parseSignedClaims(Objects.requireNonNull(auth.getCredentials())
                                .toString().replace(BEARER,""))
                        .getPayload())  //Mono<Claims>
                .switchIfEmpty(Mono.empty())
                .map(claims -> new UsernamePasswordAuthenticationToken(
                                claims.getSubject(),
                                null,
                        ((List<String>) claims.get("authorities", List.class)).stream()
                                .map(SimpleGrantedAuthority::new).toList())
                );//Mono<Authentication>
    }


}
