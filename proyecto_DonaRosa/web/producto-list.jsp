<%-- 
    Document   : producto-list
    Created on : 24/11/2025, 11:13:59 a. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Producto" %>
<%
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    
    // Manejar mensajes
    String message = request.getParameter("message");
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Productos</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 1600px;
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
        .badge-stock-ok { background: #d4edda; color: #155724; }
        .badge-stock-low { background: #fff3cd; color: #856404; }
        .badge-stock-critical { background: #f8d7da; color: #721c24; }
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
        .precio {
            text-align: right;
            font-weight: bold;
            color: #28a745;
        }
        .stock {
            text-align: center;
            font-weight: bold;
        }
        .descripcion {
            max-width: 200px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📦 Gestión de Productos</h1>
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

        <a href="producto?action=nuevo" class="btn btn-success">➕ Nuevo Producto</a>
        
        <table>
            <thead>
                <tr>
                    <th>Código</th>
                    <th>Descripción</th>
                    <th>Precio</th>
                    <th>Stock Actual</th>
                    <th>Stock Mínimo</th>
                    <th>Unidad</th>
                    <th>Línea</th>
                    <th>Impuesto</th>
                    <th>Estado Stock</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                if (productos != null && !productos.isEmpty()) { 
                    for (Producto producto : productos) { 
                        String badgeClass = "badge-stock-ok";
                        String estadoStock = "Normal";
                        
                        if (producto.getSacPro() < producto.getSmiPro()) {
                            badgeClass = "badge-stock-critical";
                            estadoStock = "Crítico";
                        } else if (producto.getSacPro() <= producto.getSmiPro() * 1.5) {
                            badgeClass = "badge-stock-low";
                            estadoStock = "Bajo";
                        }
                %>
                    <tr>
                        <td><strong><%= producto.getCodPro() %></strong></td>
                        <td class="descripcion" title="<%= producto.getDesPro() %>"><%= producto.getDesPro() %></td>
                        <td class="precio">S/. <%= String.format("%.2f", producto.getPrePro()) %></td>
                        <td class="stock"><%= producto.getSacPro() %></td>
                        <td class="stock"><%= producto.getSmiPro() %></td>
                        <td><%= producto.getUniPro() %></td>
                        <td><%= producto.getLinPro() != null ? producto.getLinPro() : "N/A" %></td>
                        <td><%= producto.getImpPro() != null ? producto.getImpPro() : "N/A" %></td>
                        <td>
                            <span class="badge <%= badgeClass %>">
                                <%= estadoStock %>
                            </span>
                        </td>
                        <td class="actions">
                            <a href="producto?action=editar&codPro=<%= producto.getCodPro() %>" 
                               class="btn btn-warning">✏️ Editar</a>
                            <a href="producto?action=eliminar&codPro=<%= producto.getCodPro() %>" 
                               class="btn btn-danger" 
                               onclick="return confirm('¿Está seguro de eliminar este producto?')">🗑️ Eliminar</a>
                        </td>
                    </tr>
                <% } 
                } else { %>
                    <tr>
                        <td colspan="10" style="text-align: center; padding: 40px;">
                            📦 No hay productos registrados
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>