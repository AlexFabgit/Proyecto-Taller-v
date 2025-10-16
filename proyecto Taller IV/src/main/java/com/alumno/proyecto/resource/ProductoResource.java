package com.alumno.proyecto.resource;

import com.alumno.proyecto.dto.ApiResponse;
import com.alumno.proyecto.entity.Producto;
import com.alumno.proyecto.service.ProductoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.math.BigDecimal;
import java.util.List;

@Path("/api/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Productos", description = "Gestión de productos de la carnicería")
public class ProductoResource {

    @Inject
    ProductoService productoService;

    @GET
    @Operation(summary = "Listar todos los productos")
    public Response listar() {
        List<Producto> productos = productoService.listar();
        return Response.ok(ApiResponse.success(productos)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener producto por ID")
    public Response obtener(@PathParam("id") Long id) {
        try {
            Producto producto = productoService.obtenerPorId(id);
            return Response.ok(ApiResponse.success(producto)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/buscar")
    @Operation(summary = "Buscar productos por nombre")
    public Response buscarPorNombre(@QueryParam("nombre") String nombre) {
        List<Producto> productos = productoService.buscarPorNombre(nombre);
        return Response.ok(ApiResponse.success(productos)).build();
    }

    @GET
    @Path("/categoria/{idCategoria}")
    @Operation(summary = "Obtener productos por categoría")
    public Response buscarPorCategoria(@PathParam("idCategoria") Long idCategoria) {
        List<Producto> productos = productoService.buscarPorCategoria(idCategoria);
        return Response.ok(ApiResponse.success(productos)).build();
    }

    @GET
    @Path("/stock-bajo")
    @Operation(summary = "Obtener productos con stock bajo")
    public Response stockBajo(@QueryParam("limite") @DefaultValue("10") BigDecimal limite) {
        List<Producto> productos = productoService.obtenerStockBajo(limite);
        return Response.ok(ApiResponse.success(productos)).build();
    }

    @POST
    @Operation(summary = "Crear nuevo producto")
    public Response crear(@Valid Producto producto) {
        Producto nuevoProducto = productoService.crear(producto);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.created(nuevoProducto))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar producto")
    public Response actualizar(@PathParam("id") Long id, @Valid Producto producto) {
        try {
            Producto productoActualizado = productoService.actualizar(id, producto);
            return Response.ok(ApiResponse.success("Producto actualizado", productoActualizado)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }

    @PATCH
    @Path("/{id}/stock")
    @Operation(summary = "Actualizar stock de producto")
    public Response actualizarStock(@PathParam("id") Long id, @QueryParam("cantidad") BigDecimal cantidad) {
        try {
            Producto producto = productoService.actualizarStock(id, cantidad);
            return Response.ok(ApiResponse.success("Stock actualizado", producto)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar producto")
    public Response eliminar(@PathParam("id") Long id) {
        try {
            productoService.eliminar(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }
}
