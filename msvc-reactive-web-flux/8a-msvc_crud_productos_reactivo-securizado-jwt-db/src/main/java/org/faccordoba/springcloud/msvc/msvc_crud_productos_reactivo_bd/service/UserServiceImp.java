package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.dto.UserRequestDto;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.User;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.repository.RolRepository;
import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImp implements UserService {
    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    public UserServiceImp(UserRepository userRepository, RolRepository rolRepository) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
    }

    @Transactional
    @Override
    public Mono<Void> saveUser(UserRequestDto requestDto) {
        User user = new User();
        user.setUsername(requestDto.username());
        user.setPassword(BCrypt.withDefaults().hashToString(12, requestDto.password().toCharArray()));
        user.setEnabled(Boolean.TRUE);
       return rolRepository.findByRol(requestDto.rol())
                .flatMap(rol -> {
                    user.setRolId(rol.getId());
                    return userRepository.findByUsername(user.getUsername())
                            .switchIfEmpty(Mono.defer(() -> userRepository.save(user)))
                            .then();  })
               .then();


    }

    @Override
    public Flux<User> findAll() {
        return userRepository.findAll();
    }
}
