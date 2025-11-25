<%-- 
    Document   : reporte-vendedores
    Created on : 24/11/2025, 8:55:09 p. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%
    List<Object[]> vendedores = (List<Object[]>) request.getAttribute("vendedores");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reporte - Vendedores con Menores Ventas</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
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
            margin: 5px;
        }
        .btn-info { background: #17a2b8; }
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
        .venta-baja {
            color: #dc3545;
            font-weight: bold;
        }
        .venta-media {
            color: #ffc107;
            font-weight: bold;
        }
        .ranking {
            font-weight: bold;
            text-align: center;
        }
        .medalla-1 { color: #ffd700; } /* Oro */
        .medalla-2 { color: #c0c0c0; } /* Plata */
        .medalla-3 { color: #cd7f32; } /* Bronce */
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📊 Reporte: Vendedores con Menores Ventas</h1>
            <div>
                <a href="index.html" class="btn">🏠 Inicio</a>
                <a href="auditoria" class="btn btn-info">📋 Auditoría</a>
            </div>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Ranking</th>
                    <th>Código</th>
                    <th>Nombre Vendedor</th>
                    <th>Total Ventas (S/.)</th>
                    <th>Cant. Facturas</th>
                </tr>
            </thead>
            <tbody>
                <% 
                if (vendedores != null && !vendedores.isEmpty()) { 
                    int ranking = 1;
                    for (Object[] vendedor : vendedores) { 
                        String medallaClass = "";
                        if (ranking == 1) medallaClass = "medalla-1";
                        else if (ranking == 2) medallaClass = "medalla-2";
                        else if (ranking == 3) medallaClass = "medalla-3";
                %>
                    <tr>
                        <td class="ranking <%= medallaClass %>">
                            <% if (ranking == 1) { %>🥇<% } %>
                            <% if (ranking == 2) { %>🥈<% } %>
                            <% if (ranking == 3) { %>🥉<% } %>
                            #<%= ranking %>
                        </td>
                        <td><strong><%= vendedor[0] %></strong></td>
                        <td><%= vendedor[1] %></td>
                        <td class="venta-baja">S/. <%= String.format("%.2f", vendedor[2]) %></td>
                        <td><%= vendedor[3] %></td>
                    </tr>
                <% 
                    ranking++;
                    } 
                } else { %>
                    <tr>
                        <td colspan="5" style="text-align: center; padding: 40px;">
                            📝 No hay datos de ventas para generar el reporte
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>

        <% if (vendedores != null && !vendedores.isEmpty()) { %>
            <div style="text-align: center; margin-top: 20px; color: #666;">
                Mostrando los 3 vendedores con menores ventas totales
            </div>
        <% } %>
    </div>
</body>
</html>