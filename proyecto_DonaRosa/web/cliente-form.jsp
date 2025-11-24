<%-- 
    Document   : cliente-form
    Created on : 24/11/2025, 12:38:30 a. m.
    Author     : rodri
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Cliente" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    List<String[]> distritos = (List<String[]>) request.getAttribute("distritos");
    boolean esEdicion = (cliente != null);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= esEdicion ? "Editar Cliente" : "Nuevo Cliente" %></title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        input[type="text"], input[type="date"], select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
            font-size: 16px;
        }
        input[type="text"]:focus, select:focus, input[type="date"]:focus {
            border-color: #007bff;
            outline: none;
            box-shadow: 0 0 5px rgba(0,123,255,0.3);
        }
        .form-row {
            display: flex;
            gap: 15px;
        }
        .form-row .form-group {
            flex: 1;
        }
        .btn {
            display: inline-block;
            padding: 12px 20px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            margin-right: 10px;
            font-size: 16px;
        }
        .btn-success { background: #28a745; }
        .btn-secondary { background: #6c757d; }
        .btn:hover { opacity: 0.8; }
        .form-actions {
            text-align: center;
            margin-top: 30px;
            border-top: 1px solid #eee;
            padding-top: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }
        .required {
            color: red;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><%= esEdicion ? "✏️ Editar Cliente" : "➕ Nuevo Cliente" %></h1>
            <a href="cliente" class="btn btn-secondary">← Volver</a>
        </div>

        <form action="cliente" method="post">
            <div class="form-row">
                <div class="form-group">
                    <label for="codCli">Código Cliente <span class="required">*</span></label>
                    <input type="text" id="codCli" name="codCli" 
                           value="<%= esEdicion ? cliente.getCodCli() : "" %>" 
                           <%= esEdicion ? "readonly" : "" %>
                           required maxlength="5" placeholder="Ej: C0001">
                </div>
                
                <div class="form-group">
                    <label for="tipCli">Tipo Cliente <span class="required">*</span></label>
                    <select id="tipCli" name="tipCli" required>
                        <option value="">Seleccione tipo</option>
                        <option value="NATURAL" <%= esEdicion && "NATURAL".equals(cliente.getTipCli()) ? "selected" : "" %>>Natural</option>
                        <option value="JURIDICO" <%= esEdicion && "JURIDICO".equals(cliente.getTipCli()) ? "selected" : "" %>>Jurídico</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="rsoCli">Razón Social / Nombre <span class="required">*</span></label>
                <input type="text" id="rsoCli" name="rsoCli" 
                       value="<%= esEdicion ? cliente.getRsoCli() : "" %>" 
                       required maxlength="30" placeholder="Ej: Empresa XYZ S.A.">
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="rucCli">RUC <span class="required">*</span></label>
                    <input type="text" id="rucCli" name="rucCli" 
                           value="<%= esEdicion ? cliente.getRucCli() : "" %>" 
                           required maxlength="11" placeholder="Ej: 20123456789">
                </div>
                
                <div class="form-group">
                    <label for="tlfCli">Teléfono</label>
                    <input type="text" id="tlfCli" name="tlfCli" 
                           value="<%= esEdicion ? cliente.getTlfCli() : "" %>" 
                           maxlength="9" placeholder="Ej: 987654321">
                </div>
            </div>

            <div class="form-group">
                <label for="dirCli">Dirección <span class="required">*</span></label>
                <input type="text" id="dirCli" name="dirCli" 
                       value="<%= esEdicion ? cliente.getDirCli() : "" %>" 
                       required maxlength="100" placeholder="Ej: Av. Principal 123">
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="codDis">Distrito <span class="required">*</span></label>
                    <select id="codDis" name="codDis" required>
                        <option value="">Seleccione distrito</option>
                        <% if (distritos != null) { 
                            for (String[] distrito : distritos) { 
                                String selected = "";
                                if (esEdicion && distrito[0].equals(cliente.getCodDis())) {
                                    selected = "selected";
                                }
                        %>
                            <option value="<%= distrito[0] %>" <%= selected %>>
                                <%= distrito[1] %>
                            </option>
                        <% } 
                        } %>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="fecReg">Fecha Registro <span class="required">*</span></label>
                    <input type="date" id="fecReg" name="fecReg" 
                           value="<%= esEdicion ? sdf.format(cliente.getFecReg()) : "" %>" 
                           required>
                </div>
            </div>

            <div class="form-group">
                <label for="conCli">Contacto</label>
                <input type="text" id="conCli" name="conCli" 
                       value="<%= esEdicion ? cliente.getConCli() : "" %>" 
                       maxlength="30" placeholder="Ej: Juan Pérez">
            </div>
            
            <div class="form-actions">
                <% if (esEdicion) { %>
                    <input type="hidden" name="action" value="actualizar">
                    <button type="submit" class="btn btn-success">💾 Actualizar Cliente</button>
                <% } else { %>
                    <input type="hidden" name="action" value="insertar">
                    <button type="submit" class="btn btn-success">💾 Guardar Cliente</button>
                <% } %>
                <a href="cliente" class="btn btn-secondary">❌ Cancelar</a>
            </div>
        </form>
    </div>

    <script>
        // Validación del formulario
        document.querySelector('form').addEventListener('submit', function(e) {
            const codCli = document.getElementById('codCli').value.trim();
            const rucCli = document.getElementById('rucCli').value.trim();
            const tlfCli = document.getElementById('tlfCli').value.trim();
            
            if (!codCli) {
                e.preventDefault();
                alert('El código de cliente es requerido.');
                return false;
            }
            
            if (rucCli && !/^\d{11}$/.test(rucCli)) {
                e.preventDefault();
                alert('El RUC debe tener exactamente 11 dígitos.');
                return false;
            }
            
            if (tlfCli && !/^\d{7,9}$/.test(tlfCli)) {
                e.preventDefault();
                alert('El teléfono debe contener solo números (7-9 dígitos).');
                return false;
            }
        });
    </script>
</body>
</html>