package servlet;

import controladorDAO.FacturaDAO;
import controladorDAO.DetalleFacturaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Factura;
import modelo.DetalleFactura;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "FacturaServlet", urlPatterns = {"/factura"})
public class FacturaServlet extends HttpServlet {
    private FacturaDAO facturaDAO;
    private DetalleFacturaDAO detalleFacturaDAO;
    
    @Override
    public void init() {
        facturaDAO = new FacturaDAO();
        detalleFacturaDAO = new DetalleFacturaDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "listar";
        }
        
        try {
            switch (action) {
                case "nuevo":
                    mostrarFormularioNuevo(request, response);
                    break;
                case "editar":
                    editarFactura(request, response);
                    break;
                case "eliminar":
                    eliminarFactura(request, response);
                    break;
                case "verDetalles":
                    verDetallesFactura(request, response);
                    break;
                default:
                    listarFacturas(request, response);
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
                    insertarFactura(request, response);
                    break;
                case "actualizar":
                    actualizarFactura(request, response);
                    break;
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            response.sendRedirect("factura?error=Error: " + e.getMessage());
        }
    }
    
    private void listarFacturas(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<Factura> facturas = facturaDAO.obtenerTodasFacturas();
        request.setAttribute("facturas", facturas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("factura-list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<String[]> clientes = facturaDAO.obtenerClientes();
        List<String[]> vendedores = facturaDAO.obtenerVendedores();
        
        request.setAttribute("clientes", clientes);
        request.setAttribute("vendedores", vendedores);
        RequestDispatcher dispatcher = request.getRequestDispatcher("factura-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editarFactura(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        String numFac = request.getParameter("numFac");
        Factura factura = facturaDAO.obtenerFacturaPorNumero(numFac);
        List<String[]> clientes = facturaDAO.obtenerClientes();
        List<String[]> vendedores = facturaDAO.obtenerVendedores();
        
        if (factura != null) {
            request.setAttribute("factura", factura);
        }
        request.setAttribute("clientes", clientes);
        request.setAttribute("vendedores", vendedores);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("factura-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void verDetallesFactura(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        String numFac = request.getParameter("numFac");
        List<DetalleFactura> detalles = detalleFacturaDAO.obtenerDetallesPorFactura(numFac);
        double total = detalleFacturaDAO.calcularTotalFactura(numFac);
        
        request.setAttribute("detalles", detalles);
        request.setAttribute("total", total);
        request.setAttribute("numFac", numFac);
        RequestDispatcher dispatcher = request.getRequestDispatcher("factura-detalles.jsp");
        dispatcher.forward(request, response);
    }
    
    private void insertarFactura(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ParseException {
        
        Factura factura = obtenerFacturaDesdeRequest(request);
        boolean resultado = facturaDAO.insertarFactura(factura);
        
        if (resultado) {
            response.sendRedirect("factura?message=Factura creada exitosamente");
        } else {
            response.sendRedirect("factura?error=Error al crear la factura");
        }
    }
    
    private void actualizarFactura(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ParseException {
        
        Factura factura = obtenerFacturaDesdeRequest(request);
        boolean resultado = facturaDAO.actualizarFactura(factura);
        
        if (resultado) {
            response.sendRedirect("factura?message=Factura actualizada exitosamente");
        } else {
            response.sendRedirect("factura?error=Error al actualizar la factura");
        }
    }
    
    private void eliminarFactura(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String numFac = request.getParameter("numFac");
        boolean resultado = facturaDAO.eliminarFactura(numFac);
        
        if (resultado) {
            response.sendRedirect("factura?message=Factura eliminada exitosamente");
        } else {
            response.sendRedirect("factura?error=Error al eliminar la factura");
        }
    }
    
    private Factura obtenerFacturaDesdeRequest(HttpServletRequest request) throws ParseException {
        String numFac = request.getParameter("numFac");
        String fecFacStr = request.getParameter("fecFac");
        String codCli = request.getParameter("codCli");
        String fecCanStr = request.getParameter("fecCan");
        String estFac = request.getParameter("estFac");
        String codVen = request.getParameter("codVen");
        String porIgvStr = request.getParameter("porIgv");
        
        // Convertir fechas
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecFac = sdf.parse(fecFacStr);
        
        Date fecCan = null;
        if (fecCanStr != null && !fecCanStr.trim().isEmpty()) {
            fecCan = sdf.parse(fecCanStr);
        }
        
        // Convertir porcentaje IGV
        double porIgv = 0.18; // Valor por defecto 18%
        if (porIgvStr != null && !porIgvStr.trim().isEmpty()) {
            porIgv = Double.parseDouble(porIgvStr);
        }
        
        return new Factura(numFac, fecFac, codCli, fecCan, estFac, codVen, porIgv);
    }
}