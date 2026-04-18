package com.universidad.mvc.service;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.model.ProductoDAO;
import java.util.List;

/**
 * Servicio de negocio para operaciones con productos.
 * Centraliza la lógica de negocio y validaciones.
 */
public class ProductoService {

    private final ProductoDAO dao = new ProductoDAO();

    /**
     * Obtiene todos los productos del inventario.
     *
     * @return lista de todos los productos
     */
    public List<Producto> obtenerTodos() {
        return dao.findAll();
    }

    /**
     * Obtiene un producto específico por su identificador.
     *
     * @param id identificador del producto
     * @return el producto encontrado o null si no existe
     */
    public Producto obtenerPorId(int id) {
        return dao.findById(id);
    }

    /**
     * Guarda un nuevo producto con validaciones de negocio.
     *
     * @param p el producto a guardar
     * @throws IllegalArgumentException si el nombre está vacío o el precio es negativo
     */
    public void guardar(Producto p) {
        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }
        if (p.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        dao.save(p);
    }

    /**
     * Actualiza un producto existente.
     *
     * @param p el producto con los datos actualizados
     * @throws IllegalArgumentException si el producto no existe
     */
    public void actualizar(Producto p) {
        if (dao.findById(p.getId()) == null) {
            throw new IllegalArgumentException("Producto no encontrado.");
        }
        dao.update(p);
    }

    /**
     * Elimina un producto del inventario.
     *
     * @param id identificador del producto a eliminar
     */
    public void eliminar(int id) {
        dao.delete(id);
    }
}

