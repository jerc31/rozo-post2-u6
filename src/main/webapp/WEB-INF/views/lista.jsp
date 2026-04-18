<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inventario de Productos</title>
    <link rel="stylesheet" href="<c:url value="/css/estilos.css"/>">
</head>
<body>
    <header>
        <h1>Inventario de Productos</h1>
        <p class="subtitle">Gestión de catálogo de productos</p>
    </header>

    <main>
        <div class="container">
            <!-- Mensaje de éxito -->
            <c:if test="${not empty mensaje}">
                <div class="alert alert-success" role="alert">
                    <strong>¡Éxito!</strong> ${mensaje}
                </div>
            </c:if>

            <!-- Botón para crear nuevo producto -->
            <div class="actions">
                <a href="<c:url value="/productos?accion=formulario"/>" class="btn btn-primary">
                    <span class="icon">+</span> Nuevo Producto
                </a>
            </div>

            <!-- Tabla de productos -->
            <section class="table-section">
                <c:choose>
                    <c:when test="${not empty productos}">
                        <table class="products-table">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Categoría</th>
                                    <th>Precio</th>
                                    <th>Stock</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="p" items="${productos}" varStatus="status">
                                    <tr class="${status.index % 2 == 0 ? 'par' : 'impar'}">
                                        <td><strong>${p.id}</strong></td>
                                        <td><c:out value="${p.nombre}"/></td>
                                        <td><c:out value="${p.categoria}"/></td>
                                        <td class="precio">
                                            <fmt:formatNumber value="${p.precio}" type="currency" currencySymbol="$" minFractionDigits="2"/>
                                        </td>
                                        <td class="stock">
                                            <c:choose>
                                                <c:when test="${p.stock > 0}">
                                                    <span class="badge badge-success">${p.stock}</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <span class="badge badge-danger">Agotado</span>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="actions-cell">
                                            <a href="<c:url value="/productos?accion=editar&id=${p.id}"/>"
                                               class="btn btn-sm btn-secondary" title="Editar producto">
                                                Editar
                                            </a>
                                            <a href="<c:url value="/productos?accion=eliminar&id=${p.id}"/>"
                                               class="btn btn-sm btn-danger"
                                               title="Eliminar producto"
                                               onclick="return confirm('¿Estás seguro de que deseas eliminar &quot;${p.nombre}&quot;?')">
                                                Eliminar
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info">
                            <p>No hay productos en el inventario. <a href="<c:url value="/productos?accion=formulario"/>">Crear uno ahora</a></p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>
        </div>
    </main>

    <footer>
        <p>&copy; 2026 Sistema de Gestión de Productos | Ingeniería de Sistemas</p>
    </footer>
</body>
</html>

