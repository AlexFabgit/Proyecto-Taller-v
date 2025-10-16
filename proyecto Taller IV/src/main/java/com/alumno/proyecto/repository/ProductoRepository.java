package com.alumno.proyecto.repository;

import com.alumno.proyecto.entity.Producto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ProductoRepository implements PanacheRepository<Producto> {

    public List<Producto> findByCategoria(Long idCategoria) {
        return list("categoria.idCategoria", idCategoria);
    }

    public List<Producto> findByStockBajo(BigDecimal limite) {
        return list("stockKg < ?1", limite);
    }

    public List<Producto> findByNombreContaining(String nombre) {
        return list("LOWER(nombre) LIKE LOWER(?1)", "%" + nombre + "%");
    }
}
