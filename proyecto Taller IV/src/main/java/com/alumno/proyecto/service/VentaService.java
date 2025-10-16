package com.alumno.proyecto.service;

import com.alumno.proyecto.entity.DetalleVenta;
import com.alumno.proyecto.entity.Producto;
import com.alumno.proyecto.entity.Venta;
import com.alumno.proyecto.repository.VentaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class VentaService {

    @Inject
    VentaRepository ventaRepository;

    @Inject
    ProductoService productoService;

    public List<Venta> listar() {
        return ventaRepository.listAll();
    }

    public Venta obtenerPorId(Long id) {
        Venta venta = ventaRepository.findById(id);
        if (venta == null) {
            throw new NotFoundException("Venta no encontrada con id: " + id);
        }
        return venta;
    }

    public List<Venta> obtenerPorCliente(Long idCliente) {
        return ventaRepository.findByCliente(idCliente);
    }

    public List<Venta> obtenerPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return ventaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    public List<Venta> obtenerRecientes(int limit) {
        return ventaRepository.findRecientes(limit);
    }

    @Transactional
    public Venta crear(Venta venta) {
        // Validar y procesar cada detalle
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoService.obtenerPorId(detalle.getProducto().getIdProducto());
            
            // Verificar stock disponible
            if (producto.getStockKg().compareTo(detalle.getCantidadKg()) < 0) {
                throw new BadRequestException("Stock insuficiente para el producto: " + producto.getNombre() + 
                    ". Stock disponible: " + producto.getStockKg() + " kg");
            }
            
            // Establecer precio unitario actual del producto
            detalle.setPrecioUnitario(producto.getPrecioKg());
            detalle.setProducto(producto);
            detalle.calcularSubtotal();
            
            // Descontar del stock
            producto.setStockKg(producto.getStockKg().subtract(detalle.getCantidadKg()));
        }
        
        // Calcular total
        venta.calcularTotal();
        
        // Persistir la venta
        ventaRepository.persist(venta);
        return venta;
    }

    @Transactional
    public Venta actualizar(Long id, Venta ventaActualizada) {
        Venta venta = obtenerPorId(id);
        
        // Restaurar stock de los productos de la venta original
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoService.obtenerPorId(detalle.getProducto().getIdProducto());
            producto.setStockKg(producto.getStockKg().add(detalle.getCantidadKg()));
        }
        
        // Limpiar detalles anteriores
        venta.getDetalles().clear();
        
        // Agregar nuevos detalles
        for (DetalleVenta detalle : ventaActualizada.getDetalles()) {
            Producto producto = productoService.obtenerPorId(detalle.getProducto().getIdProducto());
            
            if (producto.getStockKg().compareTo(detalle.getCantidadKg()) < 0) {
                throw new BadRequestException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            
            detalle.setPrecioUnitario(producto.getPrecioKg());
            detalle.setProducto(producto);
            detalle.calcularSubtotal();
            venta.addDetalle(detalle);
            
            producto.setStockKg(producto.getStockKg().subtract(detalle.getCantidadKg()));
        }
        
        venta.setCliente(ventaActualizada.getCliente());
        venta.calcularTotal();
        
        return venta;
    }

    @Transactional
    public void eliminar(Long id) {
        Venta venta = obtenerPorId(id);
        
        // Restaurar stock de los productos
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoService.obtenerPorId(detalle.getProducto().getIdProducto());
            producto.setStockKg(producto.getStockKg().add(detalle.getCantidadKg()));
        }
        
        ventaRepository.delete(venta);
    }
}
