package org.faccordoba.springcloud.msvc.msvc_envios.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(value = "envios")
public class Envio {
    @Id
    private Integer idEnvio;
    private String producto;
    private LocalDateTime fecha;
    private String direccion;
    private String estado;
}
