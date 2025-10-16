package com.alumno.proyecto.resource;

import com.alumno.proyecto.dto.ApiResponse;
import com.alumno.proyecto.entity.Cliente;
import com.alumno.proyecto.service.ClienteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Clientes", description = "Gestión de clientes")
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    @GET
    @Operation(summary = "Listar todos los clientes")
    public Response listar() {
        List<Cliente> clientes = clienteService.listar();
        return Response.ok(ApiResponse.success(clientes)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener cliente por ID")
    public Response obtener(@PathParam("id") Long id) {
        try {
            Cliente cliente = clienteService.obtenerPorId(id);
            return Response.ok(ApiResponse.success(cliente)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/buscar")
    @Operation(summary = "Buscar clientes por nombre")
    public Response buscarPorNombre(@QueryParam("nombre") String nombre) {
        List<Cliente> clientes = clienteService.buscarPorNombre(nombre);
        return Response.ok(ApiResponse.success(clientes)).build();
    }

    @GET
    @Path("/email/{email}")
    @Operation(summary = "Buscar cliente por email")
    public Response buscarPorEmail(@PathParam("email") String email) {
        Cliente cliente = clienteService.buscarPorEmail(email);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound("Cliente no encontrado con email: " + email))
                    .build();
        }
        return Response.ok(ApiResponse.success(cliente)).build();
    }

    @POST
    @Operation(summary = "Crear nuevo cliente")
    public Response crear(@Valid Cliente cliente) {
        Cliente nuevoCliente = clienteService.crear(cliente);
        return Response.status(Response.Status.CREATED)
                .entity(ApiResponse.created(nuevoCliente))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar cliente")
    public Response actualizar(@PathParam("id") Long id, @Valid Cliente cliente) {
        try {
            Cliente clienteActualizado = clienteService.actualizar(id, cliente);
            return Response.ok(ApiResponse.success("Cliente actualizado", clienteActualizado)).build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar cliente")
    public Response eliminar(@PathParam("id") Long id) {
        try {
            clienteService.eliminar(id);
            return Response.noContent().build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.notFound(e.getMessage()))
                    .build();
        }
    }
}
