<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Sistema de Gestión de Productos</title>
    <link rel="stylesheet" href="/mvc-productos/css/estilos.css">
    <style>
        .error-container {
            text-align: center;
            padding: 3rem 2rem;
            max-width: 600px;
            margin: 2rem auto;
            background: white;
            border-radius: 0.75rem;
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
        }
        .error-code {
            font-size: 4rem;
            font-weight: bold;
            color: #ef4444;
            margin-bottom: 1rem;
        }
        .error-message {
            font-size: 1.25rem;
            color: #6b7280;
            margin-bottom: 2rem;
        }
    </style>
</head>
<body>
    <header>
        <h1>Error</h1>
        <p class="subtitle">Ha ocurrido un problema</p>
    </header>

    <main>
        <div class="error-container">
            <div class="error-code">
                <%
                    Object statusCode = request.getAttribute("jakarta.servlet.error.status_code");
                    if (statusCode != null) {
                        out.print(statusCode);
                    } else {
                        out.print("Error");
                    }
                %>
            </div>
            <div class="error-message">
                <%
                    Object errorMsg = request.getAttribute("jakarta.servlet.error.message");
                    if (errorMsg != null) {
                        out.print(errorMsg);
                    } else {
                        out.print("Lo sentimos, algo salió mal.");
                    }
                %>
            </div>
            <a href="<%= request.getContextPath() %>/productos" class="btn btn-primary btn-large">
                Volver al Inventario
            </a>
        </div>
    </main>

    <footer>
        <p>&copy; 2026 Sistema de Gestión de Productos | Ingeniería de Sistemas</p>
    </footer>
</body>
</html>

