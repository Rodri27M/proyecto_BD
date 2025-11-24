<%-- 
    Document   : factura-detalles
    Created on : 24/11/2025, 1:39:06 p. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.DetalleFactura" %>
<%
    List<DetalleFactura> detalles = (List<DetalleFactura>) request.getAttribute("detalles");
    Double total = (Double) request.getAttribute("total");
    String numFac = (String) request.getAttribute("numFac");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalles de Factura</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 1200px;
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
        .btn-secondary { background: #6c757d; }
        .btn-success { background: #28a745; }
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
        .total-section {
            margin-top: 20px;
            padding: 15px;
            background: #f8f9fa;
            border-radius: 4px;
            text-align: right;
        }
        .total-amount {
            font-size: 24px;
            font-weight: bold;
            color: #28a745;
        }
        .actions {
            white-space: nowrap;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📋 Detalles de Factura: <%= numFac %></h1>
            <div>
                <a href="detalle-factura?action=nuevo&numFac=<%= numFac %>" class="btn btn-success">➕ Agregar Producto</a>
                <a href="factura" class="btn btn-secondary">← Volver a Facturas</a>
            </div>
        </div>

        <table>
            <thead>
                <tr>
                    <th>Producto</th>
                    <th>Descripción</th>
                    <th>Cantidad</th>
                    <th>Precio Unit.</th>
                    <th>Subtotal</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <% 
                if (detalles != null && !detalles.isEmpty()) { 
                    for (DetalleFactura detalle : detalles) { 
                %>
                    <tr>
                        <td><strong><%= detalle.getCodPro() %></strong></td>
                        <td><%= detalle.getDesPro() %></td>
                        <td><%= detalle.getCanVen() %></td>
                        <td>S/. <%= String.format("%.2f", detalle.getPreVen()) %></td>
                        <td>S/. <%= String.format("%.2f", detalle.getSubtotal()) %></td>
                        <td class="actions">
                            <a href="detalle-factura?action=editar&numFac=<%= detalle.getNumFac() %>&codPro=<%= detalle.getCodPro() %>" 
                               class="btn btn-warning">✏️ Editar</a>
                            <a href="detalle-factura?action=eliminar&numFac=<%= detalle.getNumFac() %>&codPro=<%= detalle.getCodPro() %>" 
                               class="btn btn-danger" 
                               onclick="return confirm('¿Está seguro de eliminar este producto de la factura?')">🗑️ Eliminar</a>
                        </td>
                    </tr>
                <% } 
                } else { %>
                    <tr>
                        <td colspan="6" style="text-align: center; padding: 40px;">
                            📦 No hay productos en esta factura
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>

        <% if (total != null && total > 0) { %>
            <div class="total-section">
                <h3>Total Factura: <span class="total-amount">S/. <%= String.format("%.2f", total) %></span></h3>
            </div>
        <% } %>
    </div>
</body>
</html>