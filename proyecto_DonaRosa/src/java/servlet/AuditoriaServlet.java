package servlet;

import controladorDAO.AuditoriaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

@WebServlet(name = "AuditoriaServlet", urlPatterns = {"/auditoria"})
public class AuditoriaServlet extends HttpServlet {
    private AuditoriaDAO auditoriaDAO;
    
    @Override
    public void init() {
        auditoriaDAO = new AuditoriaDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            List<Object[]> registrosAuditoria = auditoriaDAO.obtenerAuditoria();
            request.setAttribute("registrosAuditoria", registrosAuditoria);
            RequestDispatcher dispatcher = request.getRequestDispatcher("auditoria-list.jsp");
            dispatcher.forward(request, response);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}