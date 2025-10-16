package com.alumno.proyecto.service;

import com.alumno.proyecto.entity.Producto;
import com.alumno.proyecto.repository.ProductoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class ProductoService {

    @Inject
    ProductoRepository productoRepository;

    public List<Producto> listar() {
        return productoRepository.listAll();
    }

    public Producto obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id);
        if (producto == null) {
            throw new NotFoundException("Producto no encontrado con id: " + id);
        }
        return producto;
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContaining(nombre);
    }

    public List<Producto> buscarPorCategoria(Long idCategoria) {
        return productoRepository.findByCategoria(idCategoria);
    }

    public List<Producto> obtenerStockBajo(BigDecimal limite) {
        return productoRepository.findByStockBajo(limite);
    }

    @Transactional
    public Producto crear(Producto producto) {
        productoRepository.persist(producto);
        return producto;
    }

    @Transactional
    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto producto = obtenerPorId(id);
        producto.setNombre(productoActualizado.getNombre());
        producto.setCategoria(productoActualizado.getCategoria());
        producto.setPrecioKg(productoActualizado.getPrecioKg());
        producto.setStockKg(productoActualizado.getStockKg());
        producto.setDescripcion(productoActualizado.getDescripcion());
        return producto;
    }

    @Transactional
    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        productoRepository.delete(producto);
    }

    @Transactional
    public Producto actualizarStock(Long id, BigDecimal cantidad) {
        Producto producto = obtenerPorId(id);
        producto.setStockKg(producto.getStockKg().add(cantidad));
        return producto;
    }
}
