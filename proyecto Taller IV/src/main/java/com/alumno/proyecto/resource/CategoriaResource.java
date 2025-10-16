package com.alumno.proyecto.resource;

import com.alumno.proyecto.dto.ApiResponse;
import com.alumno.proyecto.entity.Categoria;
import com.alumno.proyecto.service.CategoriaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Categorías", description = "Gestión de categorías de productos")
public class CategoriaResource {

    @Inject
    CategoriaService categoriaService;

    @GET
    @Operation(summary = "Listar todas las categorías", description = "Obtiene el listado completo de categorías")
    @APIResponse(responseCode = "200", description = "Lista de categorías obtenida exitosamente")
    public Response listar() {
        List<Categoria> categorias = categoriaService.listar();
        return Response.ok(ApiResponse.success(categorias)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener categoría por ID", description = "Obtiene una categoría específica por su ID")
    @APIResponse(responseCode = "200", description = "Categoría encontrada")
    @APIResponse(responseCode = "404", description = "Categoría no encontrada")
    public Response obtener(@PathParam("id") Long id) {
        try {
            Categoria categoria = categoriaService.obtenerPorId(id);
            return Response.ok(ApiResponse.success(categoria)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }

    @POST
    @Operation(summary = "Crear nueva categoría", description = "Crea una nueva categoría de productos")
    @APIResponse(responseCode = "201", description = "Categoría creada exitosamente")
    @APIResponse(responseCode = "400", description = "Datos inválidos")
    public Response crear(@Valid Categoria categoria) {
        Categoria nuevaCategoria = categoriaService.crear(categoria);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.created(nuevaCategoria))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar categoría", description = "Actualiza una categoría existente")
    @APIResponse(responseCode = "200", description = "Categoría actualizada exitosamente")
    @APIResponse(responseCode = "404", description = "Categoría no encontrada")
    public Response actualizar(@PathParam("id") Long id, @Valid Categoria categoria) {
        try {
            Categoria categoriaActualizada = categoriaService.actualizar(id, categoria);
            return Response.ok(ApiResponse.success("Categoría actualizada", categoriaActualizada)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar categoría", description = "Elimina una categoría existente")
    @APIResponse(responseCode = "204", description = "Categoría eliminada exitosamente")
    @APIResponse(responseCode = "404", description = "Categoría no encontrada")
    public Response eliminar(@PathParam("id") Long id) {
        try {
            categoriaService.eliminar(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }
}
