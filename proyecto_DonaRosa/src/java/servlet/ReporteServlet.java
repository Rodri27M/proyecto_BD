package servlet;

import controladorDAO.ReporteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ReporteServlet", urlPatterns = {"/reporte-vendedores"})
public class ReporteServlet extends HttpServlet {
    private ReporteDAO reporteDAO;
    
    @Override
    public void init() {
        reporteDAO = new ReporteDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Object[]> vendedores = reporteDAO.obtenerVendedoresMenoresVentas();
        request.setAttribute("vendedores", vendedores);
        RequestDispatcher dispatcher = request.getRequestDispatcher("reporte-vendedores.jsp");
        dispatcher.forward(request, response);
    }
}