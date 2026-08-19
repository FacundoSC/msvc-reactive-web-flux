package org.faccordoba.springcloud.msvc.msvc_envios.repository;

import org.faccordoba.springcloud.msvc.msvc_envios.model.Envio;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


public interface EnvioRepository  extends ReactiveCrudRepository<Envio, Integer> {
}
