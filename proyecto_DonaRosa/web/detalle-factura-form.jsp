<%-- 
    Document   : detalle-factura-form
    Created on : 24/11/2025, 1:53:29 p. m.
    Author     : rodri
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.DetalleFactura" %>
<%
    DetalleFactura detalle = (DetalleFactura) request.getAttribute("detalle");
    List<String[]> productos = (List<String[]>) request.getAttribute("productos");
    String numFac = (String) request.getAttribute("numFac");
    boolean esEdicion = (detalle != null && detalle.getNumFac() != null);
    
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= esEdicion ? "Editar Detalle" : "Agregar Producto a Factura" %></title>
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
        .info { background: #d1ecf1; color: #0c5460; padding: 10px; margin-bottom: 15px; border-radius: 4px; }
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
        .product-info {
            background: #f8f9fa;
            padding: 10px;
            border-radius: 4px;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1><%= esEdicion ? "✏️ Editar Producto en Factura" : "➕ Agregar Producto a Factura" %></h1>
        <a href="factura?action=verDetalles&numFac=<%= numFac %>" class="btn btn-secondary">← Volver a Detalles</a>

        <% if (error != null) { %>
            <div class="error">❌ <%= error %></div>
        <% } %>

        <div class="info">
            <strong>Factura:</strong> <%= numFac %>
        </div>

        <form action="detalle-factura" method="post" style="margin-top: 20px;">
            <input type="hidden" name="numFac" value="<%= numFac %>">
            
            <div class="form-group">
                <label for="codPro">Producto <span class="required">*</span></label>
                <select id="codPro" name="codPro" required <%= esEdicion ? "disabled" : "" %>>
                    <option value="">Seleccione producto</option>
                    <% 
                    if (productos != null) {
                        for (String[] producto : productos) {
                            String selected = "";
                            if (esEdicion && producto[0].equals(detalle.getCodPro())) {
                                selected = "selected";
                            }
                            String info = producto[1] + " - S/." + producto[2] + " - Stock: " + producto[3];
                    %>
                    <option value="<%= producto[0] %>" 
                            data-precio="<%= producto[2] %>" 
                            data-stock="<%= producto[3] %>"
                            <%= selected %>>
                        <%= info %>
                    </option>
                    <% }} %>
                </select>
                <% if (esEdicion) { %>
                    <input type="hidden" name="codPro" value="<%= detalle.getCodPro() %>">
                <% } %>
                <div class="help-text">Seleccione un producto para ver precio y stock disponible</div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="canVen">Cantidad <span class="required">*</span></label>
                    <input type="number" id="canVen" name="canVen" 
                           value="<%= esEdicion ? detalle.getCanVen() : "" %>" 
                           required min="1" placeholder="Ej: 5">
                    <div class="help-text" id="stock-info">Stock disponible: -</div>
                </div>
                
                <div class="form-group">
                    <label for="preVen">Precio Unitario <span class="required">*</span></label>
                    <input type="number" id="preVen" name="preVen" 
                           value="<%= esEdicion ? String.format("%.2f", detalle.getPreVen()) : "" %>" 
                           required step="0.01" min="0" placeholder="Ej: 25.50">
                    <div class="help-text" id="precio-info">Precio sugerido: -</div>
                </div>
            </div>

            <div class="form-group" id="subtotal-section" style="display: none;">
                <div class="product-info">
                    <strong>Subtotal:</strong> <span id="subtotal">S/. 0.00</span>
                </div>
            </div>

            <div style="margin-top: 20px;">
                <% if (esEdicion) { %>
                    <input type="hidden" name="action" value="actualizar">
                    <button type="submit" class="btn btn-success">💾 Actualizar Producto</button>
                <% } else { %>
                    <input type="hidden" name="action" value="insertar">
                    <button type="submit" class="btn btn-success">💾 Agregar Producto</button>
                <% } %>
                <a href="factura?action=verDetalles&numFac=<%= numFac %>" class="btn btn-secondary">❌ Cancelar</a>
            </div>
        </form>
    </div>

    <script>
        // Elementos del DOM
        const codProSelect = document.getElementById('codPro');
        const canVenInput = document.getElementById('canVen');
        const preVenInput = document.getElementById('preVen');
        const stockInfo = document.getElementById('stock-info');
        const precioInfo = document.getElementById('precio-info');
        const subtotalSection = document.getElementById('subtotal-section');
        const subtotalSpan = document.getElementById('subtotal');

        // Actualizar información cuando se selecciona un producto
        codProSelect.addEventListener('change', function() {
            const selectedOption = this.options[this.selectedIndex];
            const precio = selectedOption.getAttribute('data-precio');
            const stock = selectedOption.getAttribute('data-stock');
            
            if (precio && stock) {
                preVenInput.value = parseFloat(precio).toFixed(2);
                stockInfo.textContent = 'Stock disponible: ' + stock;
                precioInfo.textContent = 'Precio sugerido: S/.' + parseFloat(precio).toFixed(2);
                subtotalSection.style.display = 'block';
                calcularSubtotal();
            } else {
                stockInfo.textContent = 'Stock disponible: -';
                precioInfo.textContent = 'Precio sugerido: -';
                subtotalSection.style.display = 'none';
            }
        });

        // Calcular subtotal cuando cambia cantidad o precio
        canVenInput.addEventListener('input', calcularSubtotal);
        preVenInput.addEventListener('input', calcularSubtotal);

        function calcularSubtotal() {
            const cantidad = parseInt(canVenInput.value) || 0;
            const precio = parseFloat(preVenInput.value) || 0;
            const subtotal = cantidad * precio;
            subtotalSpan.textContent = 'S/.' + subtotal.toFixed(2);
        }

        // Validación del formulario
        document.querySelector('form').addEventListener('submit', function(e) {
            const codPro = document.getElementById('codPro').value.trim();
            const canVen = document.getElementById('canVen').value.trim();
            const preVen = document.getElementById('preVen').value.trim();
            
            if (!codPro || !canVen || !preVen) {
                e.preventDefault();
                alert('Por favor, complete todos los campos requeridos.');
                return false;
            }
            
            const cantidad = parseInt(canVen);
            const precio = parseFloat(preVen);
            
            if (cantidad <= 0) {
                e.preventDefault();
                alert('La cantidad debe ser mayor a 0.');
                return false;
            }
            
            if (precio < 0) {
                e.preventDefault();
                alert('El precio no puede ser negativo.');
                return false;
            }

            // Validar stock (validación del lado del cliente)
            const selectedOption = codProSelect.options[codProSelect.selectedIndex];
            const stockDisponible = parseInt(selectedOption.getAttribute('data-stock')) || 0;
            
            if (cantidad > stockDisponible) {
                e.preventDefault();
                alert('Stock insuficiente. Stock disponible: ' + stockDisponible);
                return false;
            }
        });

        // Inicializar si es edición
        <% if (esEdicion) { %>
            window.addEventListener('load', function() {
                const selectedOption = codProSelect.options[codProSelect.selectedIndex];
                const precio = selectedOption.getAttribute('data-precio');
                const stock = selectedOption.getAttribute('data-stock');
                
                if (precio && stock) {
                    stockInfo.textContent = 'Stock disponible: ' + stock;
                    precioInfo.textContent = 'Precio sugerido: S/.' + parseFloat(precio).toFixed(2);
                    subtotalSection.style.display = 'block';
                    calcularSubtotal();
                }
            });
        <% } %>
    </script>
</body>
</html>