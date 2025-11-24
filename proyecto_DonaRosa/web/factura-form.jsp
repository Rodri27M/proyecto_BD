<%-- 
    Document   : factura-form
    Created on : 24/11/2025, 1:38:21 p. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Factura" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Factura factura = (Factura) request.getAttribute("factura");
    List<String[]> clientes = (List<String[]>) request.getAttribute("clientes");
    List<String[]> vendedores = (List<String[]>) request.getAttribute("vendedores");
    boolean esEdicion = (factura != null && factura.getNumFac() != null);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= esEdicion ? "Editar Factura" : "Nueva Factura" %></title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 900px;
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
        .help-text {
            font-size: 12px;
            color: #666;
            margin-top: 3px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1><%= esEdicion ? "✏️ Editar Factura" : "➕ Nueva Factura" %></h1>
        <a href="factura" class="btn btn-secondary">← Volver</a>

        <% if (error != null) { %>
            <div class="error">❌ <%= error %></div>
        <% } %>

        <form action="factura" method="post" style="margin-top: 20px;">
            <div class="form-group">
                <label for="numFac">Número Factura <span class="required">*</span></label>
                <input type="text" id="numFac" name="numFac" 
                       value="<%= esEdicion ? factura.getNumFac() : "" %>" 
                       <%= esEdicion ? "readonly" : "" %> 
                       required maxlength="5" placeholder="Ej: F0001">
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="fecFac">Fecha Factura <span class="required">*</span></label>
                    <input type="date" id="fecFac" name="fecFac" 
                           value="<%= esEdicion ? sdf.format(factura.getFecFac()) : "" %>" 
                           required>
                </div>
                
                <div class="form-group">
                    <label for="fecCan">Fecha Cancelación</label>
                    <input type="date" id="fecCan" name="fecCan" 
                           value="<%= esEdicion && factura.getFecCan() != null ? sdf.format(factura.getFecCan()) : "" %>">
                    <div class="help-text">Solo para facturas pagadas</div>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="codCli">Cliente <span class="required">*</span></label>
                    <select id="codCli" name="codCli" required>
                        <option value="">Seleccione cliente</option>
                        <% 
                        if (clientes != null) {
                            for (String[] cliente : clientes) {
                                String selected = "";
                                if (esEdicion && cliente[0].equals(factura.getCodCli())) {
                                    selected = "selected";
                                }
                        %>
                        <option value="<%= cliente[0] %>" <%= selected %>><%= cliente[1] %></option>
                        <% }} %>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="codVen">Vendedor <span class="required">*</span></label>
                    <select id="codVen" name="codVen" required>
                        <option value="">Seleccione vendedor</option>
                        <% 
                        if (vendedores != null) {
                            for (String[] vendedor : vendedores) {
                                String selected = "";
                                if (esEdicion && vendedor[0].equals(factura.getCodVen())) {
                                    selected = "selected";
                                }
                        %>
                        <option value="<%= vendedor[0] %>" <%= selected %>><%= vendedor[1] %></option>
                        <% }} %>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="estFac">Estado <span class="required">*</span></label>
                    <select id="estFac" name="estFac" required>
                        <option value="">Seleccione estado</option>
                        <option value="PENDIENTE" <%= esEdicion && "PENDIENTE".equals(factura.getEstFac()) ? "selected" : "" %>>Pendiente</option>
                        <option value="PAGADA" <%= esEdicion && "PAGADA".equals(factura.getEstFac()) ? "selected" : "" %>>Pagada</option>
                        <option value="ANULADA" <%= esEdicion && "ANULADA".equals(factura.getEstFac()) ? "selected" : "" %>>Anulada</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="porIgv">IGV % <span class="required">*</span></label>
                    <input type="number" id="porIgv" name="porIgv" 
                           value="<%= esEdicion ? String.format("%.2f", factura.getPorIgv() * 100) : "18.00" %>" 
                           required step="0.01" min="0" max="100" placeholder="Ej: 18.00">
                    <div class="help-text">Porcentaje de IGV (ej: 18.00 para 18%)</div>
                </div>
            </div>

            <div style="margin-top: 20px;">
                <% if (esEdicion) { %>
                    <input type="hidden" name="action" value="actualizar">
                    <button type="submit" class="btn btn-success">💾 Actualizar Factura</button>
                <% } else { %>
                    <input type="hidden" name="action" value="insertar">
                    <button type="submit" class="btn btn-success">💾 Guardar Factura</button>
                <% } %>
                <a href="factura" class="btn btn-secondary">❌ Cancelar</a>
            </div>
        </form>
    </div>

    <script>
        // Validación del formulario
        document.querySelector('form').addEventListener('submit', function(e) {
            const numFac = document.getElementById('numFac').value.trim();
            const fecFac = document.getElementById('fecFac').value.trim();
            const codCli = document.getElementById('codCli').value.trim();
            const codVen = document.getElementById('codVen').value.trim();
            const estFac = document.getElementById('estFac').value.trim();
            const porIgv = document.getElementById('porIgv').value.trim();
            
            if (!numFac || !fecFac || !codCli || !codVen || !estFac || !porIgv) {
                e.preventDefault();
                alert('Por favor, complete todos los campos requeridos.');
                return false;
            }
            
            if (parseFloat(porIgv) < 0 || parseFloat(porIgv) > 100) {
                e.preventDefault();
                alert('El IGV debe estar entre 0% y 100%.');
                return false;
            }
        });
    </script>
</body>
</html>