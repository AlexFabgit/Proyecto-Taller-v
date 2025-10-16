package com.alumno.proyecto.exception;

import com.alumno.proyecto.dto.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if (exception instanceof NotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(ApiResponse.error(exception.getMessage(), 404))
                    .build();
        }

        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) exception;
            StringBuilder errors = new StringBuilder();
            cve.getConstraintViolations().forEach(violation -> 
                errors.append(violation.getMessage()).append("; ")
            );
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(ApiResponse.error("Errores de validación: " + errors.toString(), 400))
                    .build();
        }

        // Log the exception for debugging
        exception.printStackTrace();

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ApiResponse.error("Error interno del servidor: " + exception.getMessage(), 500))
                .build();
    }
}
