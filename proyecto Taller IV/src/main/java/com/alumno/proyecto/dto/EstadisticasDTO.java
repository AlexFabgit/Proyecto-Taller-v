package com.alumno.proyecto.dto;

import java.math.BigDecimal;

public class EstadisticasDTO {
    private Long totalVentas;
    private Long totalClientes;
    private Long totalProductos;
    private BigDecimal ingresosTotales;
    private BigDecimal ingresosMesActual;
    private BigDecimal ingresosHoy;

    public EstadisticasDTO() {
    }

    // Getters and Setters
    public Long getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(Long totalVentas) {
        this.totalVentas = totalVentas;
    }

    public Long getTotalClientes() {
        return totalClientes;
    }

    public void setTotalClientes(Long totalClientes) {
        this.totalClientes = totalClientes;
    }

    public Long getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(Long totalProductos) {
        this.totalProductos = totalProductos;
    }

    public BigDecimal getIngresosTotales() {
        return ingresosTotales;
    }

    public void setIngresosTotales(BigDecimal ingresosTotales) {
        this.ingresosTotales = ingresosTotales;
    }

    public BigDecimal getIngresosMesActual() {
        return ingresosMesActual;
    }

    public void setIngresosMesActual(BigDecimal ingresosMesActual) {
        this.ingresosMesActual = ingresosMesActual;
    }

    public BigDecimal getIngresosHoy() {
        return ingresosHoy;
    }

    public void setIngresosHoy(BigDecimal ingresosHoy) {
        this.ingresosHoy = ingresosHoy;
    }
}
