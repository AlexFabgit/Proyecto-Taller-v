package com.alumno.proyecto.repository;

import com.alumno.proyecto.entity.Cliente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ClienteRepository implements PanacheRepository<Cliente> {

    public Cliente findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public List<Cliente> findByNombreContaining(String nombre) {
        return list("LOWER(nombre) LIKE LOWER(?1)", "%" + nombre + "%");
    }
}
