package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo.security;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * Utilizado por Spring Security, carga el contexto de seguridad durante el intercambio de solicitudes.
 * Utilizanda el authentication-manager y
 * extrae el token de la solicitud, autentica y crea un contexto de seguridad basado en la información del token
 */
@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private final AuthManager authenticManager;
    @Value("${bearer}")
    private String BEARER;


    /**
     * Instantiates a new Security context repository.
     *
     * @param authManager the auth manager
     */
    public SecurityContextRepository( AuthManager authManager){
        this.authenticManager = authManager;
    }

    public AuthManager getAuthenticManager() {
        return authenticManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange exchange, @Nullable SecurityContext context) {
        return Mono.empty();
    }

    /**
     * obtiene el token y le pasa la información al AuthManager para que lo valide y extraiga los datos
     * el resultado es mapeado al contexto de seguridad de Spring para realizar la autenticación y autorización
     * @param exchange the exchange to look up the {@link SecurityContext}
     *
     * @return
     */

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
      return   Mono.justOrEmpty(exchange.getRequest()
                .getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(authManager -> authManager.startsWith(BEARER))
                .flatMap(authManager ->
                    authenticManager
                            .authenticate(new UsernamePasswordAuthenticationToken(authManager.substring(7),
                                    authManager.substring(7)))
                )
                .map(SecurityContextImpl::new);
    }
}
