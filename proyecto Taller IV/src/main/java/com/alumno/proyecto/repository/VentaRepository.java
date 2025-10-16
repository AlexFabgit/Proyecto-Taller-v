package com.alumno.proyecto.repository;

import com.alumno.proyecto.entity.Venta;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class VentaRepository implements PanacheRepository<Venta> {

    public List<Venta> findByCliente(Long idCliente) {
        return list("cliente.idCliente = ?1 ORDER BY fecha DESC", idCliente);
    }

    public List<Venta> findByFechaBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return list("fecha BETWEEN ?1 AND ?2 ORDER BY fecha DESC", fechaInicio, fechaFin);
    }

    public List<Venta> findRecientes(int limit) {
        return find("ORDER BY fecha DESC").page(0, limit).list();
    }
}
