package com.alumno.proyecto.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @NotNull(message = "El nombre del producto es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria")
    @JsonIgnoreProperties("productos")
    private Categoria categoria;

    @NotNull(message = "El precio por kg es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Column(name = "precio_kg", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioKg;

    @DecimalMin(value = "0.0", message = "El stock no puede ser negativo")
    @Column(name = "stock_kg", precision = 10, scale = 2)
    private BigDecimal stockKg = BigDecimal.ZERO;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("producto")
    private List<DetalleVenta> detallesVenta;

    // Constructors
    public Producto() {
    }

    public Producto(String nombre, Categoria categoria, BigDecimal precioKg, BigDecimal stockKg, String descripcion) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioKg = precioKg;
        this.stockKg = stockKg;
        this.descripcion = descripcion;
    }

    // Getters and Setters
    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPrecioKg() {
        return precioKg;
    }

    public void setPrecioKg(BigDecimal precioKg) {
        this.precioKg = precioKg;
    }

    public BigDecimal getStockKg() {
        return stockKg;
    }

    public void setStockKg(BigDecimal stockKg) {
        this.stockKg = stockKg;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }
}
