package com.universidad.mvc.controller;

import com.universidad.mvc.model.Producto;
import com.universidad.mvc.service.ProductoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Servlet controlador que gestiona las peticiones HTTP para el CRUD de productos.
 * Implementa el patrón Front Controller y delegación a la capa de servicio.
 * Incluye autenticación con sesión y validaciones robustas.
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
        if (!verificarSesion(req, resp)) return;

        String accion = req.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "listar" -> listar(req, resp);
            case "formulario" -> mostrarFormulario(req, resp);
            case "editar" -> mostrarEdicion(req, resp);
            case "eliminar" -> eliminar(req, resp);
            case "cerrar" -> cerrarSesion(req, resp);
            default -> resp.sendError(404, "Acción no encontrada");
        }
    }

    /**
     * Maneja las peticiones POST para guardar y actualizar productos.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        if (!verificarSesion(req, resp)) return;

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
     * Verifica que el usuario tenga una sesión activa.
     * Si no hay sesión, redirige al login.
     */
    private boolean verificarSesion(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("usuarioActual") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return false;
        }
        return true;
    }

    /**
     * Cierra la sesión del usuario y redirige al login.
     */
    private void cerrarSesion(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession s = req.getSession(false);
        if (s != null) {
            s.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login");
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
     * Guarda un nuevo producto con validación robusta.
     * Si hay errores, remuestra el formulario con los errores y valores previos.
     * Si es exitoso, implementa el patrón Post-Redirect-Get.
     */
    private void guardar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String nombre = req.getParameter("nombre");
        String precioStr = req.getParameter("precio");
        String stockStr = req.getParameter("stock");
        String categoria = req.getParameter("categoria");

        Map<String, String> errores = new LinkedHashMap<>();

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "El nombre del producto es obligatorio.");
        } else if (nombre.trim().length() > 100) {
            errores.put("nombre", "El nombre no debe superar los 100 caracteres.");
        }

        double precio = 0;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                errores.put("precio", "El precio no puede ser negativo.");
            }
        } catch (NumberFormatException e) {
            errores.put("precio", "El precio debe ser un número válido (ej: 19.99).");
        }

        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                errores.put("stock", "El stock no puede ser negativo.");
            }
        } catch (NumberFormatException e) {
            errores.put("stock", "El stock debe ser un número entero.");
        }

        if (!errores.isEmpty()) {
            req.setAttribute("errores", errores);
            req.setAttribute("nombre", nombre);
            req.setAttribute("precio", precioStr);
            req.setAttribute("stock", stockStr);
            req.setAttribute("categoria", categoria);
            forward(req, resp, "/WEB-INF/views/formulario.jsp");
            return;
        }

        service.guardar(new Producto(0, nombre.trim(), categoria, precio, stock));
        resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+guardado+exitosamente");
    }

    /**
     * Actualiza un producto existente con validación robusta.
     * Si hay errores, remuestra el formulario con los errores y valores previos.
     * Si es exitoso, implementa el patrón Post-Redirect-Get.
     */
    private void actualizar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        String idStr = req.getParameter("id");
        String nombre = req.getParameter("nombre");
        String precioStr = req.getParameter("precio");
        String stockStr = req.getParameter("stock");
        String categoria = req.getParameter("categoria");

        Map<String, String> errores = new LinkedHashMap<>();

        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            resp.sendError(400, "ID de producto inválido");
            return;
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.put("nombre", "El nombre del producto es obligatorio.");
        } else if (nombre.trim().length() > 100) {
            errores.put("nombre", "El nombre no debe superar los 100 caracteres.");
        }

        double precio = 0;
        try {
            precio = Double.parseDouble(precioStr);
            if (precio < 0) {
                errores.put("precio", "El precio no puede ser negativo.");
            }
        } catch (NumberFormatException e) {
            errores.put("precio", "El precio debe ser un número válido (ej: 19.99).");
        }

        int stock = 0;
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                errores.put("stock", "El stock no puede ser negativo.");
            }
        } catch (NumberFormatException e) {
            errores.put("stock", "El stock debe ser un número entero.");
        }

        if (!errores.isEmpty()) {
            req.setAttribute("errores", errores);
            req.setAttribute("producto", new Producto(id, nombre, categoria, precio, stock));
            forward(req, resp, "/WEB-INF/views/formulario.jsp");
            return;
        }

        service.actualizar(new Producto(id, nombre.trim(), categoria, precio, stock));
        resp.sendRedirect(req.getContextPath() + "/productos?mensaje=Producto+actualizado+exitosamente");
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

