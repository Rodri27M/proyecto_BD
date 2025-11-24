package servlet;

import controladorDAO.DetalleFacturaDAO;
import controladorDAO.FacturaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.DetalleFactura;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import modelo.Factura;

@WebServlet(name = "DetalleFacturaServlet", urlPatterns = {"/detalle-factura"})
public class DetalleFacturaServlet extends HttpServlet {
    private DetalleFacturaDAO detalleFacturaDAO;
    private FacturaDAO facturaDAO;
    
    @Override
    public void init() {
        detalleFacturaDAO = new DetalleFacturaDAO();
        facturaDAO = new FacturaDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("factura");
            return;
        }
        
        try {
            switch (action) {
                case "nuevo":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "editar":
                    editarDetalleFactura(request, response);
                    break;
                case "eliminar":
                    eliminarDetalleFactura(request, response);
                    break;
                default:
                    response.sendRedirect("factura");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "insertar":
                    insertarDetalleFactura(request, response);
                    break;
                case "actualizar":
                    actualizarDetalleFactura(request, response);
                    break;
            }
        } catch (SQLException e) {
            // Capturar error de stock insuficiente
            if (e.getMessage().contains("Stock insuficiente")) {
                response.sendRedirect("detalle-factura?action=nuevo&numFac=" + 
                    request.getParameter("numFac") + "&error=" + e.getMessage());
            } else {
                e.printStackTrace();
                response.sendRedirect("factura?error=Error: " + e.getMessage());
            }
        }
    }
    
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        String numFac = request.getParameter("numFac");
        List<String[]> productos = detalleFacturaDAO.obtenerProductos();
        
        request.setAttribute("numFac", numFac);
        request.setAttribute("productos", productos);
        
        // Pasar mensaje de error si existe
        String error = request.getParameter("error");
        if (error != null) {
            request.setAttribute("error", error);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("detalle-factura-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editarDetalleFactura(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        String numFac = request.getParameter("numFac");
        String codPro = request.getParameter("codPro");
        DetalleFactura detalle = detalleFacturaDAO.obtenerDetalle(numFac, codPro);
        List<String[]> productos = detalleFacturaDAO.obtenerProductos();
        
        if (detalle != null) {
            request.setAttribute("detalle", detalle);
        }
        request.setAttribute("productos", productos);
        request.setAttribute("numFac", numFac);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("detalle-factura-form.jsp");
        dispatcher.forward(request, response);
    }
    
   private void insertarDetalleFactura(HttpServletRequest request, HttpServletResponse response) 
        throws SQLException, IOException {
    
    String numFac = request.getParameter("numFac");
    
    // VALIDAR que numFac no sea null o vacío
    if (numFac == null || numFac.trim().isEmpty()) {
        response.sendRedirect("factura?error=Error: Número de factura no especificado");
        return;
    }
    
    // Verificar que la factura existe
    FacturaDAO facturaDAO = new FacturaDAO();
    Factura factura = facturaDAO.obtenerFacturaPorNumero(numFac);
    if (factura == null) {
        response.sendRedirect("factura?error=Error: La factura " + numFac + " no existe");
        return;
    }
    
    DetalleFactura detalle = obtenerDetalleDesdeRequest(request);
    boolean resultado = detalleFacturaDAO.insertarDetalleFactura(detalle);
    
    if (resultado) {
        response.sendRedirect("factura?action=verDetalles&numFac=" + detalle.getNumFac() + 
                            "&message=Producto agregado exitosamente");
    } else {
        response.sendRedirect("factura?action=verDetalles&numFac=" + detalle.getNumFac() + 
                            "&error=Error al agregar el producto");
    }
}
    private void actualizarDetalleFactura(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        DetalleFactura detalle = obtenerDetalleDesdeRequest(request);
        boolean resultado = detalleFacturaDAO.actualizarDetalleFactura(detalle);
        
        if (resultado) {
            response.sendRedirect("factura?action=verDetalles&numFac=" + detalle.getNumFac() + 
                                "&message=Producto actualizado exitosamente");
        } else {
            response.sendRedirect("factura?action=verDetalles&numFac=" + detalle.getNumFac() + 
                                "&error=Error al actualizar el producto");
        }
    }
    
    private void eliminarDetalleFactura(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String numFac = request.getParameter("numFac");
        String codPro = request.getParameter("codPro");
        boolean resultado = detalleFacturaDAO.eliminarDetalleFactura(numFac, codPro);
        
        if (resultado) {
            response.sendRedirect("factura?action=verDetalles&numFac=" + numFac + 
                                "&message=Producto eliminado exitosamente");
        } else {
            response.sendRedirect("factura?action=verDetalles&numFac=" + numFac + 
                                "&error=Error al eliminar el producto");
        }
    }
    
    private DetalleFactura obtenerDetalleDesdeRequest(HttpServletRequest request) {
        String numFac = request.getParameter("numFac");
        String codPro = request.getParameter("codPro");
        String canVenStr = request.getParameter("canVen");
        String preVenStr = request.getParameter("preVen");
        
        // Convertir valores numéricos
        int canVen = 0;
        if (canVenStr != null && !canVenStr.trim().isEmpty()) {
            canVen = Integer.parseInt(canVenStr);
        }
        
        double preVen = 0.0;
        if (preVenStr != null && !preVenStr.trim().isEmpty()) {
            preVen = Double.parseDouble(preVenStr);
        }
        
        return new DetalleFactura(numFac, codPro, canVen, preVen);
    }
}