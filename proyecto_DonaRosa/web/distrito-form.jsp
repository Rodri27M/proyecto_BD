<%-- 
    Document   : distrito-form
    Created on : 23/11/2025, 11:10:13 p. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <c:choose>
            <c:when test="${not empty distrito}">Editar Distrito</c:when>
            <c:otherwise>Nuevo Distrito</c:otherwise>
        </c:choose>
    </title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }
        input[type="text"]:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 5px rgba(0,123,255,0.3);
        }
        .btn {
            display: inline-block;
            padding: 10px 15px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            margin-right: 10px;
        }
        .btn-success { background: #28a745; }
        .btn-secondary { background: #6c757d; }
        .btn:hover { opacity: 0.8; }
        .form-actions {
            text-align: center;
            margin-top: 30px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .required {
            color: red;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>
                <c:choose>
                    <c:when test="${not empty distrito}">✏️ Editar Distrito</c:when>
                    <c:otherwise>➕ Nuevo Distrito</c:otherwise>
                </c:choose>
            </h1>
            <a href="distrito" class="btn btn-secondary">← Volver</a>
        </div>

        <form action="distrito" method="post">
            <div class="form-group">
                <label for="codDis">Código del Distrito <span class="required">*</span></label>
                <input type="text" id="codDis" name="codDis" 
                       value="${distrito.codDis}" 
                       <c:if test="${not empty distrito}">readonly</c:if>
                       required maxlength="5">
            </div>
            
            <div class="form-group">
                <label for="nomDis">Nombre del Distrito <span class="required">*</span></label>
                <input type="text" id="nomDis" name="nomDis" 
                       value="${distrito.nomDis}" 
                       required maxlength="50">
            </div>
            
            <div class="form-actions">
                <c:choose>
                    <c:when test="${not empty distrito}">
                        <input type="hidden" name="action" value="actualizar">
                        <button type="submit" class="btn btn-success">💾 Actualizar Distrito</button>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="action" value="insertar">
                        <button type="submit" class="btn btn-success">💾 Guardar Distrito</button>
                    </c:otherwise>
                </c:choose>
                <a href="distrito" class="btn btn-secondary">❌ Cancelar</a>
            </div>
        </form>
    </div>

    <script>
        // Validación básica del formulario
        document.querySelector('form').addEventListener('submit', function(e) {
            const codDis = document.getElementById('codDis').value.trim();
            const nomDis = document.getElementById('nomDis').value.trim();
            
            if (!codDis || !nomDis) {
                e.preventDefault();
                alert('Por favor, complete todos los campos requeridos.');
                return false;
            }
            
            if (codDis.length > 5) {
                e.preventDefault();
                alert('El código no puede tener más de 5 caracteres.');
                return false;
            }
        });
    </script>
</body>
</html>
