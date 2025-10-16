package com.alumno.proyecto.resource;

import com.alumno.proyecto.dto.ApiResponse;
import com.alumno.proyecto.dto.EstadisticasDTO;
import com.alumno.proyecto.service.EstadisticasService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.math.BigDecimal;

@Path("/api/estadisticas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Estadísticas", description = "Reportes y estadísticas del negocio")
public class EstadisticasResource {

    @Inject
    EstadisticasService estadisticasService;

    @GET
    @Path("/generales")
    @Operation(summary = "Obtener estadísticas generales del negocio")
    public Response obtenerGenerales() {
        EstadisticasDTO stats = estadisticasService.obtenerEstadisticasGenerales();
        return Response.ok(ApiResponse.success(stats)).build();
    }

    @GET
    @Path("/productos-mas-vendidos")
    @Operation(summary = "Obtener productos más vendidos")
    public Response productosTopVendidos(@QueryParam("limite") @DefaultValue("10") int limite) {
        var productos = estadisticasService.obtenerProductosMasVendidos(limite);
        return Response.ok(ApiResponse.success(productos)).build();
    }

    @GET
    @Path("/ventas-por-categoria")
    @Operation(summary = "Obtener ventas agrupadas por categoría")
    public Response ventasPorCategoria() {
        var ventas = estadisticasService.obtenerVentasPorCategoria();
        return Response.ok(ApiResponse.success(ventas)).build();
    }

    @GET
    @Path("/clientes-top")
    @Operation(summary = "Obtener clientes con más compras")
    public Response clientesTop(@QueryParam("limite") @DefaultValue("10") int limite) {
        var clientes = estadisticasService.obtenerClientesTop(limite);
        return Response.ok(ApiResponse.success(clientes)).build();
    }

    @GET
    @Path("/producto/{id}")
    @Operation(summary = "Obtener estadísticas de un producto específico")
    public Response estadisticasProducto(@PathParam("id") Long idProducto) {
        var stats = estadisticasService.obtenerEstadisticasProducto(idProducto);
        return Response.ok(ApiResponse.success(stats)).build();
    }

    @GET
    @Path("/ventas-por-dia")
    @Operation(summary = "Obtener ventas de los últimos días")
    public Response ventasPorDia(@QueryParam("dias") @DefaultValue("7") int dias) {
        var ventas = estadisticasService.obtenerVentasPorDia(dias);
        return Response.ok(ApiResponse.success(ventas)).build();
    }

    @GET
    @Path("/stock-bajo")
    @Operation(summary = "Obtener productos con stock bajo")
    public Response stockBajo(@QueryParam("limite") @DefaultValue("10") BigDecimal limite) {
        var productos = estadisticasService.obtenerProductosStockBajo(limite);
        return Response.ok(ApiResponse.success(productos)).build();
    }
}
