package com.alumno.proyecto.service;

import com.alumno.proyecto.entity.Categoria;
import com.alumno.proyecto.repository.CategoriaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class CategoriaService {

    @Inject
    CategoriaRepository categoriaRepository;

    public List<Categoria> listar() {
        return categoriaRepository.listAll();
    }

    public Categoria obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id);
        if (categoria == null) {
            throw new NotFoundException("Categoría no encontrada con id: " + id);
        }
        return categoria;
    }

    @Transactional
    public Categoria crear(Categoria categoria) {
        categoriaRepository.persist(categoria);
        return categoria;
    }

    @Transactional
    public Categoria actualizar(Long id, Categoria categoriaActualizada) {
        Categoria categoria = obtenerPorId(id);
        categoria.setNombre(categoriaActualizada.getNombre());
        return categoria;
    }

    @Transactional
    public void eliminar(Long id) {
        Categoria categoria = obtenerPorId(id);
        categoriaRepository.delete(categoria);
    }
}
