<%-- 
    Document   : factura-list
    Created on : 24/11/2025, 1:37:55 p. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Factura" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    List<Factura> facturas = (List<Factura>) request.getAttribute("facturas");
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
    <title>Gestión de Facturas</title>
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
        .btn-secondary { background: #6c757d; }
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
        .badge-pendiente { background: #fff3cd; color: #856404; }
        .badge-pagada { background: #d4edda; color: #155724; }
        .badge-anulada { background: #f8d7da; color: #721c24; }
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
        .numero-factura {
            font-weight: bold;
            color: #007bff;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🧾 Gestión de Facturas</h1>
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

        <a href="factura?action=nuevo" class="btn btn-success">➕ Nueva Factura</a>
        <a href="detalle-factura?action=nuevo" class="btn btn-secondary">➕ Agregar Detalle</a>
        
        <table>
            <thead>
                <tr>
                    <th>Número</th>
                    <th>Fecha</th>
                    <th>Cliente</th>
                    <th>Vendedor</th>
                    <th>F. Cancelación</th>
                    <th>Estado</th>
                    <th>IGV %</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                if (facturas != null && !facturas.isEmpty()) { 
                    for (Factura factura : facturas) { 
                        String badgeClass = "";
                        if ("PAGADA".equals(factura.getEstFac())) {
                            badgeClass = "badge-pagada";
                        } else if ("ANULADA".equals(factura.getEstFac())) {
                            badgeClass = "badge-anulada";
                        } else {
                            badgeClass = "badge-pendiente";
                        }
                %>
                    <tr>
                        <td class="numero-factura"><%= factura.getNumFac() %></td>
                        <td><%= sdf.format(factura.getFecFac()) %></td>
                        <td><%= factura.getCodCli() %></td>
                        <td><%= factura.getCodVen() %></td>
                        <td><%= factura.getFecCan() != null ? sdf.format(factura.getFecCan()) : "N/A" %></td>
                        <td>
                            <span class="badge <%= badgeClass %>">
                                <%= factura.getEstFac() %>
                            </span>
                        </td>
                        <td><%= (factura.getPorIgv() * 100) %>%</td>
                        <td class="actions">
                            <a href="factura?action=verDetalles&numFac=<%= factura.getNumFac() %>" 
                               class="btn btn-info">📋 Detalles</a>
                            <a href="factura?action=editar&numFac=<%= factura.getNumFac() %>" 
                               class="btn btn-warning">✏️ Editar</a>
                            <a href="factura?action=eliminar&numFac=<%= factura.getNumFac() %>" 
                               class="btn btn-danger" 
                               onclick="return confirm('¿Está seguro de eliminar esta factura?')">🗑️ Eliminar</a>
                        </td>
                    </tr>
                <% } 
                } else { %>
                    <tr>
                        <td colspan="8" style="text-align: center; padding: 40px;">
                            🧾 No hay facturas registradas
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>