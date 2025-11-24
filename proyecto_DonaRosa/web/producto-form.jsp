<%-- 
    Document   : producto-form
    Created on : 24/11/2025, 11:13:40 a. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Producto" %>
<%
    Producto producto = (Producto) request.getAttribute("producto");
    boolean esEdicion = (producto != null && producto.getCodPro() != null);
    
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= esEdicion ? "Editar Producto" : "Nuevo Producto" %></title>
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
        <h1><%= esEdicion ? "✏️ Editar Producto" : "➕ Nuevo Producto" %></h1>
        <a href="producto" class="btn btn-secondary">← Volver</a>

        <% if (error != null) { %>
            <div class="error">❌ <%= error %></div>
        <% } %>

        <form action="producto" method="post" style="margin-top: 20px;">
            <div class="form-group">
                <label for="codPro">Código Producto <span class="required">*</span></label>
                <input type="text" id="codPro" name="codPro" 
                       value="<%= esEdicion ? producto.getCodPro() : "" %>" 
                       <%= esEdicion ? "readonly" : "" %> 
                       required maxlength="5" placeholder="Ej: P0001">
            </div>

            <div class="form-group">
                <label for="desPro">Descripción <span class="required">*</span></label>
                <input type="text" id="desPro" name="desPro" 
                       value="<%= esEdicion ? producto.getDesPro() : "" %>" 
                       required maxlength="30" placeholder="Ej: Laptop HP 15-dw1000la">
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="prePro">Precio <span class="required">*</span></label>
                    <input type="number" id="prePro" name="prePro" 
                           value="<%= esEdicion ? String.format("%.2f", producto.getPrePro()) : "" %>" 
                           required step="0.01" min="0" placeholder="Ej: 299.99">
                    <div class="help-text">Precio en soles</div>
                </div>
                
                <div class="form-group">
                    <label for="uniPro">Unidad de Medida <span class="required">*</span></label>
                    <select id="uniPro" name="uniPro" required>
                        <option value="">Seleccione unidad</option>
                        <option value="UNIDAD" <%= esEdicion && "UNIDAD".equals(producto.getUniPro()) ? "selected" : "" %>>Unidad</option>
                        <option value="KILO" <%= esEdicion && "KILO".equals(producto.getUniPro()) ? "selected" : "" %>>Kilo</option>
                        <option value="LITRO" <%= esEdicion && "LITRO".equals(producto.getUniPro()) ? "selected" : "" %>>Litro</option>
                        <option value="METRO" <%= esEdicion && "METRO".equals(producto.getUniPro()) ? "selected" : "" %>>Metro</option>
                        <option value="CAJA" <%= esEdicion && "CAJA".equals(producto.getUniPro()) ? "selected" : "" %>>Caja</option>
                        <option value="PAQUETE" <%= esEdicion && "PAQUETE".equals(producto.getUniPro()) ? "selected" : "" %>>Paquete</option>
                    </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="sacPro">Stock Actual <span class="required">*</span></label>
                    <input type="number" id="sacPro" name="sacPro" 
                           value="<%= esEdicion ? producto.getSacPro() : "" %>" 
                           required min="0" placeholder="Ej: 100">
                    <div class="help-text">Cantidad disponible</div>
                </div>
                
                <div class="form-group">
                    <label for="smiPro">Stock Mínimo <span class="required">*</span></label>
                    <input type="number" id="smiPro" name="smiPro" 
                           value="<%= esEdicion ? producto.getSmiPro() : "" %>" 
                           required min="0" placeholder="Ej: 10">
                    <div class="help-text">Mínimo antes de alerta</div>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="linPro">Línea de Producto</label>
                    <select id="linPro" name="linPro">
                        <option value="">Seleccione línea</option>
                        <option value="TECNOLOGIA" <%= esEdicion && "TECNOLOGIA".equals(producto.getLinPro()) ? "selected" : "" %>>Tecnología</option>
                        <option value="HOGAR" <%= esEdicion && "HOGAR".equals(producto.getLinPro()) ? "selected" : "" %>>Hogar</option>
                        <option value="OFICINA" <%= esEdicion && "OFICINA".equals(producto.getLinPro()) ? "selected" : "" %>>Oficina</option>
                        <option value="ELECTRODOMESTICOS" <%= esEdicion && "ELECTRODOMESTICOS".equals(producto.getLinPro()) ? "selected" : "" %>>Electrodomésticos</option>
                        <option value="ROPA" <%= esEdicion && "ROPA".equals(producto.getLinPro()) ? "selected" : "" %>>Ropa</option>
                        <option value="DEPORTES" <%= esEdicion && "DEPORTES".equals(producto.getLinPro()) ? "selected" : "" %>>Deportes</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="impPro">Impuesto</label>
                    <select id="impPro" name="impPro">
                        <option value="">Seleccione impuesto</option>
                        <option value="IGV 18%" <%= esEdicion && "IGV 18%".equals(producto.getImpPro()) ? "selected" : "" %>>IGV 18%</option>
                        <option value="EXONERADO" <%= esEdicion && "EXONERADO".equals(producto.getImpPro()) ? "selected" : "" %>>Exonerado</option>
                        <option value="INAfecto" <%= esEdicion && "INAfecto".equals(producto.getImpPro()) ? "selected" : "" %>>Inafecto</option>
                    </select>
                </div>
            </div>

            <div style="margin-top: 20px;">
                <% if (esEdicion) { %>
                    <input type="hidden" name="action" value="actualizar">
                    <button type="submit" class="btn btn-success">💾 Actualizar Producto</button>
                <% } else { %>
                    <input type="hidden" name="action" value="insertar">
                    <button type="submit" class="btn btn-success">💾 Guardar Producto</button>
                <% } %>
                <a href="producto" class="btn btn-secondary">❌ Cancelar</a>
            </div>
        </form>
    </div>

    <script>
        // Validación del formulario
        document.querySelector('form').addEventListener('submit', function(e) {
            const codPro = document.getElementById('codPro').value.trim();
            const desPro = document.getElementById('desPro').value.trim();
            const prePro = document.getElementById('prePro').value.trim();
            const sacPro = document.getElementById('sacPro').value.trim();
            const smiPro = document.getElementById('smiPro').value.trim();
            
            if (!codPro || !desPro || !prePro || !sacPro || !smiPro) {
                e.preventDefault();
                alert('Por favor, complete todos los campos requeridos.');
                return false;
            }
            
            if (parseFloat(prePro) < 0) {
                e.preventDefault();
                alert('El precio no puede ser negativo.');
                return false;
            }
            
            if (parseInt(sacPro) < 0 || parseInt(smiPro) < 0) {
                e.preventDefault();
                alert('Los stocks no pueden ser negativos.');
                return false;
            }
        });
    </script>
</body>
</html>