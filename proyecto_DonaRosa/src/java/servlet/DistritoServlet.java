package servlet;

import controladorDAO.DistritoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Distrito;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DistritoServlet", urlPatterns = {"/distrito"})
public class DistritoServlet extends HttpServlet {
    private DistritoDAO distritoDAO;
    
    @Override
    public void init() {
        distritoDAO = new DistritoDAO();
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
                    mostrarFormulario(request, response);
                    break;
                case "editar":
                    editarDistrito(request, response);
                    break;
                case "eliminar":
                    eliminarDistrito(request, response);
                    break;
                default:
                    listarDistritos(request, response);
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
                    insertarDistrito(request, response);
                    break;
                case "actualizar":
                    actualizarDistrito(request, response);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    
    private void listarDistritos(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<Distrito> distritos = distritoDAO.obtenerTodosDistritos();
        request.setAttribute("distritos", distritos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("distrito-list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("distrito-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editarDistrito(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        String codDis = request.getParameter("codDis");
        Distrito distrito = distritoDAO.obtenerDistritoPorCodigo(codDis);
        request.setAttribute("distrito", distrito);
        RequestDispatcher dispatcher = request.getRequestDispatcher("distrito-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void insertarDistrito(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String codDis = request.getParameter("codDis");
        String nomDis = request.getParameter("nomDis");
        
        Distrito nuevoDistrito = new Distrito(codDis, nomDis);
        distritoDAO.insertarDistrito(nuevoDistrito);
        response.sendRedirect("distrito");
    }
    
    private void actualizarDistrito(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String codDis = request.getParameter("codDis");
        String nomDis = request.getParameter("nomDis");
        
        Distrito distrito = new Distrito(codDis, nomDis);
        distritoDAO.actualizarDistrito(distrito);
        response.sendRedirect("distrito");
    }
    
    private void eliminarDistrito(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String codDis = request.getParameter("codDis");
        distritoDAO.eliminarDistrito(codDis);
        response.sendRedirect("distrito");
    }
}