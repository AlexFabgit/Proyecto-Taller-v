package com.alumno.proyecto.dto;

public class ApiResponse<T> {
    private String message;
    private int code;
    private T data;

    public ApiResponse() {
    }

    public ApiResponse(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("Operación exitosa", 200, data);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, 200, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return new ApiResponse<>("Recurso creado exitosamente", 201, data);
    }

    public static <T> ApiResponse<T> error(String message, int code) {
        return new ApiResponse<>(message, code, null);
    }

    public static <T> ApiResponse<T> notFound(String message) {
        return new ApiResponse<>(message, 404, null);
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
