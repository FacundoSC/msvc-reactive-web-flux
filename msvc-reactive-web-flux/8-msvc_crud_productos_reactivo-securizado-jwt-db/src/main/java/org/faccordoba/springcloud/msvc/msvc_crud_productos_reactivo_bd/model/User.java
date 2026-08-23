package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {
    @Id
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    @Column(value = "rol_id")
    private Integer rolId;
}
