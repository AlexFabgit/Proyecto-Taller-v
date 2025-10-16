package com.alumno.proyecto.repository;

import com.alumno.proyecto.entity.Categoria;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CategoriaRepository implements PanacheRepository<Categoria> {

    public Categoria findByNombre(String nombre) {
        return find("nombre", nombre).firstResult();
    }
}
