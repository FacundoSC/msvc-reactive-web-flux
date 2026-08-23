package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "roles")
public class Rol {
    @Id
    private Integer id;
    private String rol;
}
