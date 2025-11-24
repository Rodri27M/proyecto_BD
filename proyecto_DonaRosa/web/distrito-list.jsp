<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Distrito" %>
<%
    List<Distrito> distritos = (List<Distrito>) request.getAttribute("distritos");
    String message = (String) request.getAttribute("message");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Distritos</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f4f4f4; }
        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; }
        .btn { display: inline-block; padding: 10px 15px; background: #007bff; color: white; 
               text-decoration: none; border-radius: 4px; border: none; cursor: pointer; margin: 5px; }
        .btn-success { background: #28a745; }
        .btn-warning { background: #ffc107; color: black; }
        .btn-danger { background: #dc3545; }
        .btn:hover { opacity: 0.8; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        th { background-color: #f8f9fa; }
        tr:hover { background-color: #f5f5f5; }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📍 Gestión de Distritos</h1>
            <a href="index.html" class="btn">🏠 Inicio</a>
        </div>

        <!-- Mensajes -->
        <% if (message != null) { %>
            <div style="background: #d4edda; color: #155724; padding: 10px; margin: 10px 0; border-radius: 4px;">
                <%= message %>
            </div>
        <% } %>
        
        <% if (error != null) { %>
            <div style="background: #f8d7da; color: #721c24; padding: 10px; margin: 10px 0; border-radius: 4px;">
                <%= error %>
            </div>
        <% } %>

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
                <% if (distritos != null && !distritos.isEmpty()) { 
                    for (Distrito distrito : distritos) { %>
                    <tr>
                        <td><%= distrito.getCodDis() %></td>
                        <td><%= distrito.getNomDis() %></td>
                        <td>
                            <a href="distrito?action=editar&codDis=<%= distrito.getCodDis() %>" 
                               class="btn btn-warning">✏️ Editar</a>
                            <a href="distrito?action=eliminar&codDis=<%= distrito.getCodDis() %>" 
                               class="btn btn-danger" 
                               onclick="return confirm('¿Está seguro de eliminar este distrito?')">🗑️ Eliminar</a>
                        </td>
                    </tr>
                <% } 
                } else { %>
                    <tr>
                        <td colspan="3" style="text-align: center;">No hay distritos registrados</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>