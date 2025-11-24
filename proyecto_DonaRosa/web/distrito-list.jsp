<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Distritos</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 1000px;
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
        .btn {
            display: inline-block;
            padding: 10px 15px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            margin: 5px;
        }
        .btn-success { background: #28a745; }
        .btn-warning { background: #ffc107; color: black; }
        .btn-danger { background: #dc3545; }
        .btn:hover { opacity: 0.8; }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f8f9fa;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .actions {
            white-space: nowrap;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .message {
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
        }
        .success { background: #d4edda; color: #155724; }
        .error { background: #f8d7da; color: #721c24; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📍 Gestión de Distritos</h1>
            <a href="index.html" class="btn">🏠 Inicio</a>
        </div>

        <!-- Mensajes -->
        <c:if test="${not empty message}">
            <div class="message success">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>

        <a href="distrito?action=nuevo" class="btn btn-success">➕ Nuevo Distrito</a>
        
        <table>
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nombre del Distrito</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="distrito" items="${distritos}">
                    <tr>
                        <td>${distrito.codDis}</td>
                        <td>${distrito.nomDis}</td>
                        <td class="actions">
                            <a href="distrito?action=editar&codDis=${distrito.codDis}" 
                               class="btn btn-warning">✏️ Editar</a>
                            <a href="distrito?action=eliminar&codDis=${distrito.codDis}" 
                               class="btn btn-danger" 
                               onclick="return confirm('¿Está seguro de eliminar este distrito?')">🗑️ Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty distritos}">
                    <tr>
                        <td colspan="3" style="text-align: center;">No hay distritos registrados</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</body>
</html>
