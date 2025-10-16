package com.alumno.proyecto.service;

import com.alumno.proyecto.dto.EstadisticasDTO;
import com.alumno.proyecto.entity.Producto;
import com.alumno.proyecto.repository.ClienteRepository;
import com.alumno.proyecto.repository.ProductoRepository;
import com.alumno.proyecto.repository.VentaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class EstadisticasService {

    @Inject
    EntityManager em;

    @Inject
    VentaRepository ventaRepository;

    @Inject
    ProductoRepository productoRepository;

    @Inject
    ClienteRepository clienteRepository;

    public EstadisticasDTO obtenerEstadisticasGenerales() {
        EstadisticasDTO stats = new EstadisticasDTO();
        
        // Total de ventas
        Long totalVentas = ventaRepository.count();
        stats.setTotalVentas(totalVentas);
        
        // Total de clientes
        Long totalClientes = clienteRepository.count();
        stats.setTotalClientes(totalClientes);
        
        // Total de productos
        Long totalProductos = productoRepository.count();
        stats.setTotalProductos(totalProductos);
        
        // Ingresos totales
        BigDecimal ingresosTotales = em.createQuery(
            "SELECT COALESCE(SUM(v.total), 0) FROM Venta v", BigDecimal.class)
            .getSingleResult();
        stats.setIngresosTotales(ingresosTotales);
        
        // Ingresos del mes actual
        LocalDateTime inicioMes = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        BigDecimal ingresosMes = em.createQuery(
            "SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE v.fecha >= :inicio", BigDecimal.class)
            .setParameter("inicio", inicioMes)
            .getSingleResult();
        stats.setIngresosMesActual(ingresosMes);
        
        // Ingresos de hoy
        LocalDateTime inicioHoy = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        BigDecimal ingresosHoy = em.createQuery(
            "SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE v.fecha >= :inicio", BigDecimal.class)
            .setParameter("inicio", inicioHoy)
            .getSingleResult();
        stats.setIngresosHoy(ingresosHoy);
        
        return stats;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> obtenerProductosMasVendidos(int limite) {
        return (List<Map<String, Object>>) (List<?>) em.createQuery(
            "SELECT NEW map(p.nombre as producto, SUM(d.cantidadKg) as cantidad, " +
            "SUM(d.subtotal) as total) " +
            "FROM DetalleVenta d JOIN d.producto p " +
            "GROUP BY p.idProducto, p.nombre " +
            "ORDER BY SUM(d.cantidadKg) DESC",
            Map.class)
            .setMaxResults(limite)
            .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> obtenerVentasPorCategoria() {
        return (List<Map<String, Object>>) (List<?>) em.createQuery(
            "SELECT NEW map(c.nombre as categoria, COUNT(d.idDetalle) as cantidad, " +
            "SUM(d.subtotal) as total) " +
            "FROM DetalleVenta d JOIN d.producto p JOIN p.categoria c " +
            "GROUP BY c.idCategoria, c.nombre " +
            "ORDER BY SUM(d.subtotal) DESC",
            Map.class)
            .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> obtenerClientesTop(int limite) {
        return (List<Map<String, Object>>) (List<?>) em.createQuery(
            "SELECT NEW map(c.nombre as cliente, COUNT(v.idVenta) as totalCompras, " +
            "SUM(v.total) as totalGastado) " +
            "FROM Venta v JOIN v.cliente c " +
            "GROUP BY c.idCliente, c.nombre " +
            "ORDER BY SUM(v.total) DESC",
            Map.class)
            .setMaxResults(limite)
            .getResultList();
    }

    public Map<String, Object> obtenerEstadisticasProducto(Long idProducto) {
        Map<String, Object> stats = new HashMap<>();
        
        // Información del producto
        Producto producto = productoRepository.findById(idProducto);
        if (producto != null) {
            stats.put("producto", producto.getNombre());
            stats.put("stockActual", producto.getStockKg());
            stats.put("precioKg", producto.getPrecioKg());
            
            // Cantidad vendida
            BigDecimal cantidadVendida = em.createQuery(
                "SELECT COALESCE(SUM(d.cantidadKg), 0) FROM DetalleVenta d WHERE d.producto.idProducto = :id",
                BigDecimal.class)
                .setParameter("id", idProducto)
                .getSingleResult();
            stats.put("cantidadVendida", cantidadVendida);
            
            // Ingresos generados
            BigDecimal ingresosGenerados = em.createQuery(
                "SELECT COALESCE(SUM(d.subtotal), 0) FROM DetalleVenta d WHERE d.producto.idProducto = :id",
                BigDecimal.class)
                .setParameter("id", idProducto)
                .getSingleResult();
            stats.put("ingresosGenerados", ingresosGenerados);
        }
        
        return stats;
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> obtenerVentasPorDia(int dias) {
        LocalDateTime fechaInicio = LocalDateTime.now().minusDays(dias);
        return (List<Map<String, Object>>) (List<?>) em.createQuery(
            "SELECT NEW map(CAST(v.fecha AS date) as fecha, COUNT(v.idVenta) as cantidad, " +
            "SUM(v.total) as total) " +
            "FROM Venta v " +
            "WHERE v.fecha >= :fechaInicio " +
            "GROUP BY CAST(v.fecha AS date) " +
            "ORDER BY CAST(v.fecha AS date) DESC",
            Map.class)
            .setParameter("fechaInicio", fechaInicio)
            .getResultList();
    }

    public List<Producto> obtenerProductosStockBajo(BigDecimal limite) {
        return productoRepository.findByStockBajo(limite);
    }
}
