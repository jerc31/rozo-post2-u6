<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="app.titulo"/></title>
    <link rel="stylesheet" href="<c:url value="/css/estilos.css"/>">
</head>
<body>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'es'}"/>
    <fmt:setBundle basename="messages"/>

    <header>
        <div class="header-container">
            <div>
                <h1><fmt:message key="app.titulo"/></h1>
                <p class="subtitle"><fmt:message key="app.bienvenida"/>, ${sessionScope.usuarioActual.username}</p>
            </div>
            <div class="header-actions">
                <div class="language-selector">
                    <a href="<c:url value="/idioma?lang=es"/>" class="lang-link">Español</a>
                    <span class="separator">|</span>
                    <a href="<c:url value="/idioma?lang=en"/>" class="lang-link">English</a>
                </div>
                <a href="<c:url value="/productos?accion=cerrar"/>" class="btn btn-danger btn-sm">
                    <fmt:message key="app.cerrar"/>
                </a>
            </div>
        </div>
    </header>

    <main>
        <div class="container">
            <c:if test="${not empty mensaje}">
                <div class="alert alert-success">
                    ${mensaje}
                </div>
            </c:if>

            <div class="actions">
                <a href="<c:url value="/productos?accion=formulario"/>" class="btn btn-primary">
                    <span class="icon">+</span> <fmt:message key="menu.nuevo"/>
                </a>
            </div>

            <section class="table-section">
                <c:choose>
                    <c:when test="${not empty productos}">
                        <table class="products-table">
                            <thead>
                                <tr>
                                    <th><fmt:message key="tabla.id"/></th>
                                    <th><fmt:message key="tabla.nombre"/></th>
                                    <th><fmt:message key="tabla.categoria"/></th>
                                    <th><fmt:message key="tabla.precio"/></th>
                                    <th><fmt:message key="tabla.stock"/></th>
                                    <th><fmt:message key="tabla.acciones"/></th>
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
                                               class="btn btn-sm btn-secondary" title="<fmt:message key="btn.editar"/>">
                                                <fmt:message key="btn.editar"/>
                                            </a>
                                            <a href="<c:url value="/productos?accion=eliminar&id=${p.id}"/>"
                                               class="btn btn-sm btn-danger"
                                               title="<fmt:message key="btn.eliminar"/>"
                                               onclick="return confirm('¿Estás seguro de que deseas eliminar &quot;${p.nombre}&quot;?')">
                                                <fmt:message key="btn.eliminar"/>
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

