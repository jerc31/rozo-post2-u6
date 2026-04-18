package com.universidad.mvc.model;

import java.util.*;

/**
 * Data Access Object (DAO) para gestionar productos en memoria.
 * Implementa el patrón Singleton para compartir la lista entre peticiones.
 * En una aplicación real, esto interactuaría con una base de datos.
 */
public class ProductoDAO {

    private static final List<Producto> lista = new ArrayList<>();
    private static int contador = 4;

    /**
     * Bloque inicializador estático que precarga 3 productos de ejemplo.
     */
    static {
        lista.add(new Producto(1, "Laptop Pro 15", "Computadoras", 1299.99, 10));
        lista.add(new Producto(2, "Monitor 27\" 4K", "Monitores", 549.00, 25));
        lista.add(new Producto(3, "Teclado Mecánico", "Periféricos", 89.99, 50));
    }

    /**
     * Obtiene todos los productos de la lista.
     *
     * @return lista inmodificable de todos los productos
     */
    public List<Producto> findAll() {
        return Collections.unmodifiableList(lista);
    }

    /**
     * Busca un producto por su identificador.
     *
     * @param id identificador del producto a buscar
     * @return el producto encontrado o null si no existe
     */
    public Producto findById(int id) {
        return lista.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }

    /**
     * Guarda un nuevo producto en la lista.
     * Asigna automáticamente un identificador único.
     *
     * @param p el producto a guardar
     */
    public void save(Producto p) {
        p.setId(++contador);
        lista.add(p);
    }

    /**
     * Actualiza un producto existente.
     *
     * @param p el producto con los datos actualizados
     */
    public void update(Producto p) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == p.getId()) {
                lista.set(i, p);
                return;
            }
        }
    }

    /**
     * Elimina un producto por su identificador.
     *
     * @param id identificador del producto a eliminar
     */
    public void delete(int id) {
        lista.removeIf(p -> p.getId() == id);
    }
}

