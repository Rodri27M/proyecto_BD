<%-- 
    Document   : vendedor-form
    Created on : 24/11/2025, 10:41:25 a. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Vendedor" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Vendedor vendedor = (Vendedor) request.getAttribute("vendedor");
    List<String[]> distritos = (List<String[]>) request.getAttribute("distritos");
    boolean esEdicion = (vendedor != null && vendedor.getCodVen() != null);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= esEdicion ? "Editar Vendedor" : "Nuevo Vendedor" %></title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input, select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .btn {
            padding: 10px 15px;
            margin: 5px;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .btn-success { background: #28a745; color: white; }
        .btn-secondary { background: #6c757d; color: white; }
        .error { background: #f8d7da; color: #721c24; padding: 10px; margin-bottom: 15px; border-radius: 4px; }
        .form-row {
            display: flex;
            gap: 15px;
        }
        .form-row .form-group {
            flex: 1;
        }
        .required {
            color: red;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1><%= esEdicion ? "✏️ Editar Vendedor" : "➕ Nuevo Vendedor" %></h1>
        <a href="vendedor" class="btn btn-secondary">← Volver</a>

        <% if (error != null) { %>
            <div class="error">❌ <%= error %></div>
        <% } %>

        <form action="vendedor" method="post" style="margin-top: 20px;">
            <div class="form-group">
                <label for="codVen">Código Vendedor <span class="required">*</span></label>
                <input type="text" id="codVen" name="codVen" 
                       value="<%= esEdicion ? vendedor.getCodVen() : "" %>" 
                       <%= esEdicion ? "readonly" : "" %> 
                       required maxlength="5" placeholder="Ej: V0001">
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="nomVen">Nombre <span class="required">*</span></label>
                    <input type="text" id="nomVen" name="nomVen" 
                           value="<%= esEdicion ? vendedor.getNomVen() : "" %>" 
                           required maxlength="20" placeholder="Ej: Juan">
                </div>
                
                <div class="form-group">
                    <label for="apeVen">Apellido <span class="required">*</span></label>
                    <input type="text" id="apeVen" name="apeVen" 
                           value="<%= esEdicion ? vendedor.getApeVen() : "" %>" 
                           required maxlength="29" placeholder="Ej: Pérez">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="sueVen">Sueldo <span class="required">*</span></label>
                    <input type="number" id="sueVen" name="sueVen" 
                           value="<%= esEdicion ? String.format("%.2f", vendedor.getSueVen()) : "" %>" 
                           required step="0.01" min="0" placeholder="Ej: 1500.00">
                </div>
                
                <div class="form-group">
                    <label for="finVen">Fecha Ingreso</label>
                    <input type="date" id="finVen" name="finVen" 
                           value="<%= esEdicion && vendedor.getFinVen() != null ? sdf.format(vendedor.getFinVen()) : "" %>">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="tipVen">Tipo Vendedor <span class="required">*</span></label>
                    <select id="tipVen" name="tipVen" required>
                        <option value="">Seleccione tipo</option>
                        <option value="INTERNO" <%= esEdicion && "INTERNO".equals(vendedor.getTipVen()) ? "selected" : "" %>>Interno</option>
                        <option value="EXTERNO" <%= esEdicion && "EXTERNO".equals(vendedor.getTipVen()) ? "selected" : "" %>>Externo</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="codDis">Distrito <span class="required">*</span></label>
                    <select id="codDis" name="codDis" required>
                        <option value="">Seleccione distrito</option>
                        <% 
                        if (distritos != null) {
                            for (String[] distrito : distritos) {
                                String selected = "";
                                if (esEdicion && distrito[0].equals(vendedor.getCodDis())) {
                                    selected = "selected";
                                }
                        %>
                        <option value="<%= distrito[0] %>" <%= selected %>><%= distrito[1] %></option>
                        <% }} %>
                    </select>
                </div>
            </div>

            <div style="margin-top: 20px;">
                <% if (esEdicion) { %>
                    <input type="hidden" name="action" value="actualizar">
                    <button type="submit" class="btn btn-success">💾 Actualizar Vendedor</button>
                <% } else { %>
                    <input type="hidden" name="action" value="insertar">
                    <button type="submit" class="btn btn-success">💾 Guardar Vendedor</button>
                <% } %>
                <a href="vendedor" class="btn btn-secondary">❌ Cancelar</a>
            </div>
        </form>
    </div>

    <script>
        // Validación del formulario
        document.querySelector('form').addEventListener('submit', function(e) {
            const codVen = document.getElementById('codVen').value.trim();
            const nomVen = document.getElementById('nomVen').value.trim();
            const apeVen = document.getElementById('apeVen').value.trim();
            const sueVen = document.getElementById('sueVen').value.trim();
            
            if (!codVen || !nomVen || !apeVen || !sueVen) {
                e.preventDefault();
                alert('Por favor, complete todos los campos requeridos.');
                return false;
            }
            
            if (sueVen && parseFloat(sueVen) < 0) {
                e.preventDefault();
                alert('El sueldo no puede ser negativo.');
                return false;
            }
        });
    </script>
</body>
</html>