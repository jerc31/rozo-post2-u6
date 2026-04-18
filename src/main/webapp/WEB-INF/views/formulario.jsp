<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="${empty producto ? 'form.nuevo' : 'form.editar'}"/></title>
    <link rel="stylesheet" href="<c:url value="/css/estilos.css"/>">
</head>
<body>
    <fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'es'}"/>
    <fmt:setBundle basename="messages"/>

    <header>
        <h1><fmt:message key="${empty producto ? 'form.registrar' : 'form.editar'}"/></h1>
        <p class="subtitle"><fmt:message key="app.bienvenida"/>, ${sessionScope.usuarioActual.username}</p>
    </header>

    <main>
        <div class="container">
            <section class="form-section">
                <c:if test="${not empty errores}">
                    <div class="alert alert-error">
                        <strong>Por favor corrige los siguientes errores:</strong>
                        <ul>
                            <c:forEach var="e" items="${errores}">
                                <li>${e.value}</li>
                            </c:forEach>
                        </ul>
                    </div>
                </c:if>

                <form method="post" action="<c:url value="/productos"/>" class="product-form" novalidate>
                    <c:if test="${not empty producto}">
                        <input type="hidden" name="id" value="${producto.id}">
                        <input type="hidden" name="accion" value="actualizar">
                    </c:if>
                    <c:if test="${empty producto}">
                        <input type="hidden" name="accion" value="guardar">
                    </c:if>

                    <div class="form-group">
                        <label for="nombre"><fmt:message key="form.nombre"/> <span class="required">*</span></label>
                        <input
                            type="text"
                            id="nombre"
                            name="nombre"
                            class="form-control ${not empty errores.nombre ? 'input-error' : ''}"
                            placeholder="Ej: Laptop Pro 15"
                            required
                            maxlength="100"
                            value="<c:out value="${not empty producto ? producto.nombre : nombre}"/>">
                        <c:if test="${not empty errores.nombre}">
                            <span class="campo-error">${errores.nombre}</span>
                        </c:if>
                    </div>

                    <div class="form-group">
                        <label for="categoria"><fmt:message key="form.categoria"/> <span class="required">*</span></label>
                        <input
                            type="text"
                            id="categoria"
                            name="categoria"
                            class="form-control ${not empty errores.categoria ? 'input-error' : ''}"
                            placeholder="Ej: Computadoras"
                            maxlength="50"
                            value="<c:out value="${not empty producto ? producto.categoria : categoria}"/>">
                    </div>

                    <div class="form-group">
                        <label for="precio"><fmt:message key="form.precio"/> ($) <span class="required">*</span></label>
                        <input
                            type="number"
                            id="precio"
                            name="precio"
                            class="form-control ${not empty errores.precio ? 'input-error' : ''}"
                            placeholder="0.00"
                            step="0.01"
                            min="0"
                            required
                            value="${not empty producto ? producto.precio : precioStr}">
                        <c:if test="${not empty errores.precio}">
                            <span class="campo-error">${errores.precio}</span>
                        </c:if>
                    </div>

                    <div class="form-group">
                        <label for="stock"><fmt:message key="form.stock"/> <span class="required">*</span></label>
                        <input
                            type="number"
                            id="stock"
                            name="stock"
                            class="form-control ${not empty errores.stock ? 'input-error' : ''}"
                            placeholder="0"
                            min="0"
                            required
                            value="${not empty producto ? producto.stock : stockStr}">
                        <c:if test="${not empty errores.stock}">
                            <span class="campo-error">${errores.stock}</span>
                        </c:if>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-primary btn-large">
                            <fmt:message key="${empty producto ? 'btn.guardar' : 'btn.actualizar'}"/>
                        </button>
                        <a href="<c:url value="/productos"/>" class="btn btn-secondary btn-large">
                            <fmt:message key="btn.cancelar"/>
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

