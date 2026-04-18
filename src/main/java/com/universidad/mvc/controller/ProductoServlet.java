package com.universidad.mvc.controller;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.service.ProductoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Servlet controlador que gestiona las peticiones HTTP para el CRUD de productos.
 * Implementa el patrón Front Controller y delegación a la capa de servicio.
 */
@WebServlet("/productos")
public class ProductoServlet extends HttpServlet {

    private final ProductoService service = new ProductoService();

    /**
     * Maneja las peticiones GET para listar, mostrar formularios y editar productos.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String accion = req.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "listar" -> listar(req, resp);
            case "formulario" -> mostrarFormulario(req, resp);
            case "editar" -> mostrarEdicion(req, resp);
            case "eliminar" -> eliminar(req, resp);
            default -> resp.sendError(404, "Acción no encontrada");
        }
    }

    /**
     * Maneja las peticiones POST para guardar y actualizar productos.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String accion = req.getParameter("accion");
        if ("guardar".equals(accion)) {
            guardar(req, resp);
        } else if ("actualizar".equals(accion)) {
            actualizar(req, resp);
        } else {
            resp.sendError(400, "Acción inválida");
        }
    }

    /**
     * Lista todos los productos disponibles.
     */
    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("productos", service.obtenerTodos());
        String msg = req.getParameter("mensaje");
        if (msg != null) {
            req.setAttribute("mensaje", msg);
        }
        forward(req, resp, "/WEB-INF/views/lista.jsp");
    }

    /**
     * Muestra el formulario vacío para crear un nuevo producto.
     */
    private void mostrarFormulario(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        forward(req, resp, "/WEB-INF/views/formulario.jsp");
    }

    /**
     * Muestra el formulario precargado con los datos del producto a editar.
     */
    private void mostrarEdicion(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("producto", service.obtenerPorId(id));
            forward(req, resp, "/WEB-INF/views/formulario.jsp");
        } catch (NumberFormatException e) {
            resp.sendError(400, "ID de producto inválido");
        }
    }

    /**
     * Guarda un nuevo producto y redirige al listado con mensaje de éxito.
     * Implementa el patrón Post-Redirect-Get.
     */
    private void guardar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            Producto p = extraerProducto(req, 0);
            service.guardar(p);
            resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+guardado+exitosamente");
        } catch (Exception e) {
            resp.sendError(400, "Datos inválidos: " + e.getMessage());
        }
    }

    /**
     * Actualiza un producto existente y redirige al listado con mensaje de éxito.
     * Implementa el patrón Post-Redirect-Get.
     */
    private void actualizar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Producto p = extraerProducto(req, id);
            service.actualizar(p);
            resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+actualizado");
        } catch (Exception e) {
            resp.sendError(400, "Datos inválidos: " + e.getMessage());
        }
    }

    /**
     * Elimina un producto y redirige al listado con mensaje de éxito.
     * Implementa el patrón Post-Redirect-Get.
     */
    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            service.eliminar(id);
            resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+eliminado");
        } catch (NumberFormatException e) {
            resp.sendError(400, "ID de producto inválido");
        }
    }

    /**
     * Extrae los datos del producto desde los parámetros del formulario.
     *
     * @param req petición HTTP
     * @param id  identificador del producto (0 para nuevos productos)
     * @return objeto Producto con los datos extraídos
     */
    private Producto extraerProducto(HttpServletRequest req, int id) {
        String nombre = req.getParameter("nombre");
        String categoria = req.getParameter("categoria");
        double precio = Double.parseDouble(req.getParameter("precio"));
        int stock = Integer.parseInt(req.getParameter("stock"));

        return new Producto(id, nombre, categoria, precio, stock);
    }

    /**
     * Reenvía la petición a la vista (JSP) especificada.
     *
     * @param req  petición HTTP
     * @param resp respuesta HTTP
     * @param path ruta relativa a la JSP
     */
    private void forward(HttpServletRequest req, HttpServletResponse resp, String path)
            throws ServletException, IOException {
        req.getRequestDispatcher(path).forward(req, resp);
    }
}

