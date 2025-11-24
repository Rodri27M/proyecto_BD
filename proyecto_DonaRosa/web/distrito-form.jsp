<%-- 
    Document   : distrito-form
    Created on : 23/11/2025, 11:10:13 p. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Distrito" %>
<%
    Distrito distrito = (Distrito) request.getAttribute("distrito");
    boolean esEdicion = (distrito != null);
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title><%= esEdicion ? "Editar Distrito" : "Nuevo Distrito" %></title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; background: #f4f4f4; }
        .container { max-width: 600px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; color: #555; }
        input[type="text"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }
        .btn { padding: 10px 15px; background: #007bff; color: white; text-decoration: none; border-radius: 4px; border: none; cursor: pointer; margin-right: 10px; }
        .btn-success { background: #28a745; }
        .btn-secondary { background: #6c757d; }
        .form-actions { text-align: center; margin-top: 30px; }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><%= esEdicion ? "✏️ Editar Distrito" : "➕ Nuevo Distrito" %></h1>
            <a href="distrito" class="btn btn-secondary">← Volver</a>
        </div>

        <form action="distrito" method="post">
            <div class="form-group">
                <label for="codDis">Código del Distrito *</label>
                <input type="text" id="codDis" name="codDis" 
                       value="<%= esEdicion ? distrito.getCodDis() : "" %>" 
                       <%= esEdicion ? "readonly" : "" %>
                       required maxlength="5">
            </div>
            
            <div class="form-group">
                <label for="nomDis">Nombre del Distrito *</label>
                <input type="text" id="nomDis" name="nomDis" 
                       value="<%= esEdicion ? distrito.getNomDis() : "" %>" 
                       required maxlength="50">
            </div>
            
            <div class="form-actions">
                <% if (esEdicion) { %>
                    <input type="hidden" name="action" value="actualizar">
                    <button type="submit" class="btn btn-success">💾 Actualizar Distrito</button>
                <% } else { %>
                    <input type="hidden" name="action" value="insertar">
                    <button type="submit" class="btn btn-success">💾 Guardar Distrito</button>
                <% } %>
                <a href="distrito" class="btn btn-secondary">❌ Cancelar</a>
            </div>
        </form>
    </div>
</body>
</html>