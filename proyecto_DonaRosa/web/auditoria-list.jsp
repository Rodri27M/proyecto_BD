<%-- 
    Document   : auditoria-list
    Created on : 24/11/2025, 12:06:46 a. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    List<Object[]> registrosAuditoria = (List<Object[]>) request.getAttribute("registrosAuditoria");
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Auditoría del Sistema</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
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
            word-wrap: break-word;
        }
        th {
            background-color: #2c3e50;
            color: white;
            position: sticky;
            top: 0;
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
        .badge-insert { background: #d4edda; color: #155724; }
        .badge-update { background: #fff3cd; color: #856404; }
        .badge-delete { background: #f8d7da; color: #721c24; }
        .json-data {
            max-width: 300px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            cursor: pointer;
        }
        .json-data:hover {
            white-space: normal;
            overflow: visible;
            background: #f8f9fa;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📊 Auditoría del Sistema</h1>
            <div>
                <a href="index.html" class="btn">🏠 Inicio</a>
                <a href="distrito" class="btn btn-info">📍 Distritos</a>
            </div>
        </div>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Tabla</th>
                    <th>Clave Primaria</th>
                    <th>Operación</th>
                    <th>Usuario</th>
                    <th>Fecha</th>
                    <th>Datos Anteriores</th>
                    <th>Datos Nuevos</th>
                </tr>
            </thead>
            <tbody>
                <% if (registrosAuditoria != null && !registrosAuditoria.isEmpty()) { 
                    for (Object[] registro : registrosAuditoria) { 
                        String operacion = (String) registro[3];
                        String badgeClass = "";
                        if ("INSERT".equals(operacion)) badgeClass = "badge-insert";
                        else if ("UPDATE".equals(operacion)) badgeClass = "badge-update";
                        else if ("DELETE".equals(operacion)) badgeClass = "badge-delete";
                %>
                    <tr>
                        <td><%= registro[0] %></td>
                        <td><strong><%= registro[1] %></strong></td>
                        <td><code><%= registro[2] %></code></td>
                        <td>
                            <span class="badge <%= badgeClass %>">
                                <%= operacion %>
                            </span>
                        </td>
                        <td><%= registro[4] %></td>
                        <td>
                            <% if (registro[5] != null) { 
                                Date fecha = (Date) registro[5];
                                out.print(sdf.format(fecha));
                            } %>
                        </td>
                        <td class="json-data" title="<%= registro[6] != null ? registro[6].toString() : "" %>">
                            <%= registro[6] != null ? registro[6].toString() : "N/A" %>
                        </td>
                        <td class="json-data" title="<%= registro[7] != null ? registro[7].toString() : "" %>">
                            <%= registro[7] != null ? registro[7].toString() : "N/A" %>
                        </td>
                    </tr>
                <% } 
                } else { %>
                    <tr>
                        <td colspan="8" style="text-align: center; padding: 40px;">
                            📝 No hay registros de auditoría
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        
        <% if (registrosAuditoria != null && !registrosAuditoria.isEmpty()) { %>
            <div style="text-align: center; margin-top: 20px; color: #666;">
                Mostrando <%= registrosAuditoria.size() %> registros de auditoría
            </div>
        <% } %>
    </div>

    <script>
        // Mejorar la visualización de datos JSON
        document.querySelectorAll('.json-data').forEach(element => {
            element.addEventListener('click', function() {
                const text = this.getAttribute('title');
                if (text && text !== 'N/A') {
                    try {
                        const jsonObj = JSON.parse(text);
                        const formatted = JSON.stringify(jsonObj, null, 2);
                        alert('Datos completos:\n\n' + formatted);
                    } catch (e) {
                        alert('Datos completos:\n\n' + text);
                    }
                }
            });
        });
    </script>
</body>
</html>