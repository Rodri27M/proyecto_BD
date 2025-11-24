/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;
import controladorDAO.ProductoDAO;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import modelo.Producto;
@WebServlet(name = "ProductoServlet", urlPatterns = {"/producto"})
public class ProductoServlet extends HttpServlet {
    private ProductoDAO productoDAO;
    
    @Override
    public void init() {
        productoDAO = new ProductoDAO();
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
                    editarProducto(request, response);
                    break;
                case "eliminar":
                    eliminarProducto(request, response);
                    break;
                default:
                    listarProductos(request, response);
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
                    insertarProducto(request, response);
                    break;
                case "actualizar":
                    actualizarProducto(request, response);
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("producto?error=Error: " + e.getMessage());
        }
    }
    
    private void listarProductos(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<Producto> productos = productoDAO.obtenerTodosProductos();
        request.setAttribute("productos", productos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("producto-list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("producto-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        String codPro = request.getParameter("codPro");
        Producto producto = productoDAO.obtenerProductoPorCodigo(codPro);
        
        if (producto != null) {
            request.setAttribute("producto", producto);
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("producto-form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void insertarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        Producto producto = obtenerProductoDesdeRequest(request);
        boolean resultado = productoDAO.insertarProducto(producto);
        
        if (resultado) {
            response.sendRedirect("producto?message=Producto creado exitosamente");
        } else {
            response.sendRedirect("producto?error=Error al crear el producto");
        }
    }
    
    private void actualizarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        Producto producto = obtenerProductoDesdeRequest(request);
        boolean resultado = productoDAO.actualizarProducto(producto);
        
        if (resultado) {
            response.sendRedirect("producto?message=Producto actualizado exitosamente");
        } else {
            response.sendRedirect("producto?error=Error al actualizar el producto");
        }
    }
    
    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String codPro = request.getParameter("codPro");
        boolean resultado = productoDAO.eliminarProducto(codPro);
        
        if (resultado) {
            response.sendRedirect("producto?message=Producto eliminado exitosamente");
        } else {
            response.sendRedirect("producto?error=Error al eliminar el producto");
        }
    }
    
    private Producto obtenerProductoDesdeRequest(HttpServletRequest request) {
        String codPro = request.getParameter("codPro");
        String desPro = request.getParameter("desPro");
        String preProStr = request.getParameter("prePro");
        String sacProStr = request.getParameter("sacPro");
        String smiProStr = request.getParameter("smiPro");
        String uniPro = request.getParameter("uniPro");
        String linPro = request.getParameter("linPro");
        String impPro = request.getParameter("impPro");
        
        // Convertir valores numéricos
        double prePro = 0.0;
        if (preProStr != null && !preProStr.trim().isEmpty()) {
            prePro = Double.parseDouble(preProStr);
        }
        
        int sacPro = 0;
        if (sacProStr != null && !sacProStr.trim().isEmpty()) {
            sacPro = Integer.parseInt(sacProStr);
        }
        
        int smiPro = 0;
        if (smiProStr != null && !smiProStr.trim().isEmpty()) {
            smiPro = Integer.parseInt(smiProStr);
        }
        
        return new Producto(codPro, desPro, prePro, sacPro, smiPro, uniPro, linPro, impPro);
    }
}