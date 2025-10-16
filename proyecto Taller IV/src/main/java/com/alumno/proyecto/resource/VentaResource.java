package com.alumno.proyecto.resource;

import com.alumno.proyecto.dto.ApiResponse;
import com.alumno.proyecto.entity.Venta;
import com.alumno.proyecto.service.VentaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Path("/api/ventas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Ventas", description = "Gestión de ventas y facturación")
public class VentaResource {

    @Inject
    VentaService ventaService;

    @GET
    @Operation(summary = "Listar todas las ventas")
    public Response listar() {
        List<Venta> ventas = ventaService.listar();
        return Response.ok(ApiResponse.success(ventas)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener venta por ID")
    public Response obtener(@PathParam("id") Long id) {
        try {
            Venta venta = ventaService.obtenerPorId(id);
            return Response.ok(ApiResponse.success(venta)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/cliente/{idCliente}")
    @Operation(summary = "Obtener ventas por cliente")
    public Response obtenerPorCliente(@PathParam("idCliente") Long idCliente) {
        List<Venta> ventas = ventaService.obtenerPorCliente(idCliente);
        return Response.ok(ApiResponse.success(ventas)).build();
    }

    @GET
    @Path("/recientes")
    @Operation(summary = "Obtener ventas recientes")
    public Response obtenerRecientes(@QueryParam("limite") @DefaultValue("10") int limite) {
        List<Venta> ventas = ventaService.obtenerRecientes(limite);
        return Response.ok(ApiResponse.success(ventas)).build();
    }

    @GET
    @Path("/por-fecha")
    @Operation(summary = "Obtener ventas por rango de fechas")
    public Response obtenerPorFechas(
            @QueryParam("inicio") String fechaInicio,
            @QueryParam("fin") String fechaFin) {
        try {
            LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
            LocalDateTime fin = LocalDateTime.parse(fechaFin);
            List<Venta> ventas = ventaService.obtenerPorFechas(inicio, fin);
            return Response.ok(ApiResponse.success(ventas)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("Formato de fecha inválido. Use ISO-8601: yyyy-MM-ddTHH:mm:ss", 400))
                    .build();
        }
    }

    @POST
    @Operation(summary = "Crear nueva venta")
    public Response crear(@Valid Venta venta) {
        try {
            Venta nuevaVenta = ventaService.crear(venta);
            return Response.status(Response.Status.CREATED)
                    .entity(ApiResponse.created(nuevaVenta))
                    .build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error(e.getMessage(), 400))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar venta")
    public Response actualizar(@PathParam("id") Long id, @Valid Venta venta) {
        try {
            Venta ventaActualizada = ventaService.actualizar(id, venta);
            return Response.ok(ApiResponse.success("Venta actualizada", ventaActualizada)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        } catch (BadRequestException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error(e.getMessage(), 400))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar venta")
    public Response eliminar(@PathParam("id") Long id) {
        try {
            ventaService.eliminar(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }
}
