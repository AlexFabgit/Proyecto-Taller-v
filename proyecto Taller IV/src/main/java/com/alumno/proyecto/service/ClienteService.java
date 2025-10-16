package com.alumno.proyecto.service;

import com.alumno.proyecto.entity.Cliente;
import com.alumno.proyecto.repository.ClienteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class ClienteService {

    @Inject
    ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        return clienteRepository.listAll();
    }

    public Cliente obtenerPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new NotFoundException("Cliente no encontrado con id: " + id);
        }
        return cliente;
    }

    public Cliente buscarPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContaining(nombre);
    }

    @Transactional
    public Cliente crear(Cliente cliente) {
        clienteRepository.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente actualizar(Long id, Cliente clienteActualizado) {
        Cliente cliente = obtenerPorId(id);
        cliente.setNombre(clienteActualizado.getNombre());
        cliente.setTelefono(clienteActualizado.getTelefono());
        cliente.setDireccion(clienteActualizado.getDireccion());
        cliente.setEmail(clienteActualizado.getEmail());
        return cliente;
    }

    @Transactional
    public void eliminar(Long id) {
        Cliente cliente = obtenerPorId(id);
        clienteRepository.delete(cliente);
    }
}
