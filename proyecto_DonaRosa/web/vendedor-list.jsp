<%-- 
    Document   : vendedor-list
    Created on : 24/11/2025, 10:41:01 a. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Vendedor" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    List<Vendedor> vendedores = (List<Vendedor>) request.getAttribute("vendedores");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    // Manejar mensajes
    String message = request.getParameter("message");
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Vendedores</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 1400px;
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
        .btn-info { background: #17a2b8; }
        .btn:hover { opacity: 0.8; }
        
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            font-size: 14px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #2c3e50;
            color: white;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }
        .badge {
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
        }
        .badge-interno { background: #d4edda; color: #155724; }
        .badge-externo { background: #fff3cd; color: #856404; }
        .actions {
            white-space: nowrap;
        }
        .message {
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
        }
        .success { background: #d4edda; color: #155724; }
        .error { background: #f8d7da; color: #721c24; }
        .sueldo {
            text-align: right;
            font-weight: bold;
            color: #28a745;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>👨‍💼 Gestión de Vendedores</h1>
            <div>
                <a href="index.html" class="btn">🏠 Inicio</a>
                <a href="auditoria" class="btn btn-info">📊 Auditoría</a>
            </div>
        </div>

        <!-- Mensajes -->
        <% if (message != null) { %>
            <div class="message success">✅ <%= message %></div>
        <% } %>
        
        <% if (error != null) { %>
            <div class="message error">❌ <%= error %></div>
        <% } %>

        <a href="vendedor?action=nuevo" class="btn btn-success">➕ Nuevo Vendedor</a>
        
        <table>
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Sueldo</th>
                    <th>F. Ingreso</th>
                    <th>Tipo</th>
                    <th>Distrito</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                if (vendedores != null && !vendedores.isEmpty()) { 
                    for (Vendedor vendedor : vendedores) { 
                        String badgeClass = "INTERNO".equals(vendedor.getTipVen()) ? "badge-interno" : "badge-externo";
                %>
                    <tr>
                        <td><strong><%= vendedor.getCodVen() %></strong></td>
                        <td><%= vendedor.getNomVen() %></td>
                        <td><%= vendedor.getApeVen() %></td>
                        <td class="sueldo">S/. <%= String.format("%.2f", vendedor.getSueVen()) %></td>
                        <td><%= vendedor.getFinVen() != null ? sdf.format(vendedor.getFinVen()) : "N/A" %></td>
                        <td>
                            <span class="badge <%= badgeClass %>">
                                <%= vendedor.getTipVen() %>
                            </span>
                        </td>
                        <td><%= vendedor.getCodDis() %></td>
                        <td class="actions">
                            <a href="vendedor?action=editar&codVen=<%= vendedor.getCodVen() %>" 
                               class="btn btn-warning">✏️ Editar</a>
                            <a href="vendedor?action=eliminar&codVen=<%= vendedor.getCodVen() %>" 
                               class="btn btn-danger" 
                               onclick="return confirm('¿Está seguro de eliminar este vendedor?')">🗑️ Eliminar</a>
                        </td>
                    </tr>
                <% } 
                } else { %>
                    <tr>
                        <td colspan="8" style="text-align: center; padding: 40px;">
                            👨‍💼 No hay vendedores registrados
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>
