package org.faccordoba.springcloud.msvc.msvc_envios.service;

import org.faccordoba.springcloud.msvc.msvc_envios.model.Envio;
import org.faccordoba.springcloud.msvc.msvc_envios.model.Order;
import org.faccordoba.springcloud.msvc.msvc_envios.repository.EnvioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
public class EnvioServiceImp implements EnvioService {
    private EnvioRepository envioRepository;
    private final Logger logger = LoggerFactory.getLogger(EnvioServiceImp.class);

    public  EnvioServiceImp(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }




    @KafkaListener(topics = "${spring.kafka.topic.name}", containerFactory = "kafkaListenerContainerFactory", groupId = "group2")
    public void managerSend(String json){
            ObjectMapper mapper = new ObjectMapper();
            Order order = mapper.readValue(json, Order.class);
            Envio envio = new Envio();
            envio.setFecha(LocalDateTime.now());
            envio.setEstado("Pending");
            envio.setProducto(order.getName());
            envio.setDireccion(order.getAddress());
            envioRepository.save(envio).subscribe();
            logger.info("Envio: {}", envio);
    }




}
