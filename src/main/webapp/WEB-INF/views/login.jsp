<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Iniciar Sesión</title>
    <link rel="stylesheet" href="<c:url value="/css/estilos.css"/>">
</head>
<body>
    <main class="login-container">
        <div class="login-box">
            <h1>Sistema de Inventario</h1>
            <p class="login-subtitle">Ingrese sus credenciales</p>

            <c:if test="${not empty errorLogin}">
                <div class="alert alert-error">
                    ${errorLogin}
                </div>
            </c:if>

            <form method="post" action="<c:url value="/login"/>">
                <div class="form-group">
                    <label for="username">Usuario:</label>
                    <input type="text" id="username" name="username" required
                           placeholder="admin o viewer">
                </div>

                <div class="form-group">
                    <label for="password">Contraseña:</label>
                    <input type="password" id="password" name="password" required
                           placeholder="Ingrese su contraseña">
                </div>

                <button type="submit" class="btn btn-primary">Iniciar Sesión</button>
            </form>

            <div class="login-help">
                <p><strong>Usuarios de prueba:</strong></p>
                <ul>
                    <li>Usuario: <code>admin</code> | Contraseña: <code>Admin123!</code></li>
                    <li>Usuario: <code>viewer</code> | Contraseña: <code>View456!</code></li>
                </ul>
            </div>
        </div>
    </main>
</body>
</html>

