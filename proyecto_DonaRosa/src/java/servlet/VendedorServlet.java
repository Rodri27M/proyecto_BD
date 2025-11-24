package servlet;

import controladorDAO.VendedorDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Vendedor;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "VendedorServlet", urlPatterns = {"/vendedor"})
public class VendedorServlet extends HttpServlet {
    private VendedorDAO vendedorDAO;
    
    @Override
    public void init() {
        vendedorDAO = new VendedorDAO();
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
                    editarVendedor(request, response);
                    break;
                case "eliminar":
                    eliminarVendedor(request, response);
                    break;
                default:
                    listarVendedores(request, response);
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
                    insertarVendedor(request, response);
                    break;
                case "actualizar":
                    actualizarVendedor(request, response);
                    break;
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            response.sendRedirect("vendedor?error=Error: " + e.getMessage());
        }
    }
    
    private void listarVendedores(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<Vendedor> vendedores = vendedorDAO.obtenerTodosVendedores();
        request.setAttribute("vendedores", vendedores);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vendedor-list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<String[]> distritos = vendedorDAO.obtenerDistritos();
        request.setAttribute("distritos", distritos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("vendedor-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editarVendedor(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        String codVen = request.getParameter("codVen");
        Vendedor vendedor = vendedorDAO.obtenerVendedorPorCodigo(codVen);
        List<String[]> distritos = vendedorDAO.obtenerDistritos();
        
        if (vendedor != null) {
            request.setAttribute("vendedor", vendedor);
        }
        request.setAttribute("distritos", distritos);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("vendedor-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void insertarVendedor(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ParseException {
        
        Vendedor vendedor = obtenerVendedorDesdeRequest(request);
        boolean resultado = vendedorDAO.insertarVendedor(vendedor);
        
        if (resultado) {
            response.sendRedirect("vendedor?message=Vendedor creado exitosamente");
        } else {
            response.sendRedirect("vendedor?error=Error al crear el vendedor");
        }
    }
    
    private void actualizarVendedor(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ParseException {
        
        Vendedor vendedor = obtenerVendedorDesdeRequest(request);
        boolean resultado = vendedorDAO.actualizarVendedor(vendedor);
        
        if (resultado) {
            response.sendRedirect("vendedor?message=Vendedor actualizado exitosamente");
        } else {
            response.sendRedirect("vendedor?error=Error al actualizar el vendedor");
        }
    }
    
    private void eliminarVendedor(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String codVen = request.getParameter("codVen");
        boolean resultado = vendedorDAO.eliminarVendedor(codVen);
        
        if (resultado) {
            response.sendRedirect("vendedor?message=Vendedor eliminado exitosamente");
        } else {
            response.sendRedirect("vendedor?error=Error al eliminar el vendedor");
        }
    }
    
    private Vendedor obtenerVendedorDesdeRequest(HttpServletRequest request) throws ParseException {
        String codVen = request.getParameter("codVen");
        String nomVen = request.getParameter("nomVen");
        String apeVen = request.getParameter("apeVen");
        String sueVenStr = request.getParameter("sueVen");
        String finVenStr = request.getParameter("finVen");
        String tipVen = request.getParameter("tipVen");
        String codDis = request.getParameter("codDis");
        
        // Convertir sueldo
        double sueVen = 0.0;
        if (sueVenStr != null && !sueVenStr.trim().isEmpty()) {
            sueVen = Double.parseDouble(sueVenStr);
        }
        
        // Convertir fecha (puede ser nula)
        Date finVen = null;
        if (finVenStr != null && !finVenStr.trim().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            finVen = sdf.parse(finVenStr);
        }
        
        return new Vendedor(codVen, nomVen, apeVen, sueVen, finVen, tipVen, codDis);
    }
}