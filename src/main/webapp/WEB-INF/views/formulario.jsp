<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty producto ? "Nuevo Producto" : "Editar Producto"}</title>
    <link rel="stylesheet" href="<c:url value="/css/estilos.css"/>">
</head>
<body>
    <header>
        <h1>${empty producto ? "Registrar Nuevo Producto" : "Editar Producto"}</h1>
        <p class="subtitle">${empty producto ? "Añade un nuevo producto al inventario" : "Modifica los datos del producto"}</p>
    </header>

    <main>
        <div class="container">
            <section class="form-section">
                <form method="post" action="<c:url value="/productos"/>" class="product-form" novalidate>
                    <!-- Campo de acción (guardar o actualizar) -->
                    <c:if test="${not empty producto}">
                        <input type="hidden" name="id" value="${producto.id}">
                        <input type="hidden" name="accion" value="actualizar">
                    </c:if>
                    <c:if test="${empty producto}">
                        <input type="hidden" name="accion" value="guardar">
                    </c:if>

                    <!-- Campo: Nombre -->
                    <div class="form-group">
                        <label for="nombre">Nombre del Producto <span class="required">*</span></label>
                        <input
                            type="text"
                            id="nombre"
                            name="nombre"
                            class="form-control"
                            placeholder="Ej: Laptop Pro 15"
                            required
                            maxlength="100"
                            value="<c:out value="${producto.nombre}"/>">
                        <small class="help-text">Campo requerido. Máximo 100 caracteres.</small>
                    </div>

                    <!-- Campo: Categoría -->
                    <div class="form-group">
                        <label for="categoria">Categoría <span class="required">*</span></label>
                        <input
                            type="text"
                            id="categoria"
                            name="categoria"
                            class="form-control"
                            placeholder="Ej: Computadoras"
                            required
                            maxlength="50"
                            value="<c:out value="${producto.categoria}"/>">
                        <small class="help-text">Campo requerido. Máximo 50 caracteres.</small>
                    </div>

                    <!-- Campo: Precio -->
                    <div class="form-group">
                        <label for="precio">Precio Unitario ($) <span class="required">*</span></label>
                        <input
                            type="number"
                            id="precio"
                            name="precio"
                            class="form-control"
                            placeholder="0.00"
                            step="0.01"
                            min="0"
                            required
                            value="${not empty producto ? producto.precio : ''}">
                        <small class="help-text">Campo requerido. Valores positivos con hasta 2 decimales.</small>
                    </div>

                    <!-- Campo: Stock -->
                    <div class="form-group">
                        <label for="stock">Stock (Cantidad) <span class="required">*</span></label>
                        <input
                            type="number"
                            id="stock"
                            name="stock"
                            class="form-control"
                            placeholder="0"
                            min="0"
                            required
                            value="${not empty producto ? producto.stock : ''}">
                        <small class="help-text">Campo requerido. Cantidad disponible en inventario.</small>
                    </div>

                    <!-- Botones de acción -->
                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary btn-large">
                            ${empty producto ? "Guardar Producto" : "Actualizar Producto"}
                        </button>
                        <a href="<c:url value="/productos"/>" class="btn btn-secondary btn-large">
                            Cancelar
                        </a>
                    </div>
                </form>
            </section>
        </div>
    </main>

    <footer>
        <p>&copy; 2026 Sistema de Gestión de Productos | Ingeniería de Sistemas</p>
    </footer>
</body>
</html>

