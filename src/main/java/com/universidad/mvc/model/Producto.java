package com.universidad.mvc.model;

/**
 * Entidad que representa un producto en el inventario.
 * Implementa Serializable para permitir la persistencia en sesión.
 */
public class Producto implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;

    /**
     * Constructor vacío requerido por el contenedor de servlets.
     */
    public Producto() {
    }

    /**
     * Constructor con parámetros para inicializar un producto.
     *
     * @param id        identificador único del producto
     * @param nombre    nombre del producto
     * @param categoria categoría a la que pertenece
     * @param precio    precio unitario del producto
     * @param stock     cantidad disponible en inventario
     */
    public Producto(int id, String nombre, String categoria, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
    }

    // ============ Getters y Setters ============

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

