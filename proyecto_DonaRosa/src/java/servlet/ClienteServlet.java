package servlet;

import controladorDAO.ClienteDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import modelo.Cliente;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

@WebServlet(name = "ClienteServlet", urlPatterns = {"/cliente"})
public class ClienteServlet extends HttpServlet {
    private ClienteDAO clienteDAO;
    
    @Override
    public void init() {
        clienteDAO = new ClienteDAO();
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
                    editarCliente(request, response);
                    break;
                case "eliminar":
                    eliminarCliente(request, response);
                    break;
                default:
                    listarClientes(request, response);
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
                    insertarCliente(request, response);
                    break;
                case "actualizar":
                    actualizarCliente(request, response);
                    break;
            }
        } catch (SQLException | ParseException e) {
            e.printStackTrace();
            request.setAttribute("error", "Error al procesar los datos: " + e.getMessage());
            try {
                mostrarFormulario(request, response);
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }
    }
    
    private void listarClientes(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<Cliente> clientes = clienteDAO.obtenerTodosClientes();
        request.setAttribute("clientes", clientes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("cliente-list.jsp");
        dispatcher.forward(request, response);
    }
    
    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
        List<String[]> distritos = clienteDAO.obtenerDistritos();
    request.setAttribute("distritos", distritos);
    
    // SOLUCIÓN: Solo crear cliente vacío si es NUEVO, no para edición
    // No establecer cliente en el request para nuevo cliente
    // request.setAttribute("cliente", null); // Esto ya es el comportamiento por defecto
    
    RequestDispatcher dispatcher = request.getRequestDispatcher("cliente-form.jsp");
    dispatcher.forward(request, response);
    }
    
    private void editarCliente(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        
    String codCli = request.getParameter("codCli");
    Cliente cliente = clienteDAO.obtenerClientePorCodigo(codCli);
    List<String[]> distritos = clienteDAO.obtenerDistritos();
    
    // SOLUCIÓN: Solo establecer cliente si existe
    if (cliente != null) {
        request.setAttribute("cliente", cliente);
    }
    request.setAttribute("distritos", distritos);
    
    RequestDispatcher dispatcher = request.getRequestDispatcher("cliente-form.jsp");
    dispatcher.forward(request, response);
    }
    
    private void insertarCliente(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ParseException {
        
        Cliente cliente = obtenerClienteDesdeRequest(request);
        boolean resultado = clienteDAO.insertarCliente(cliente);
        
        if (resultado) {
            response.sendRedirect("cliente?message=Cliente creado exitosamente");
        } else {
            response.sendRedirect("cliente?error=Error al crear el cliente");
        }
     
    }
    
    private void actualizarCliente(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException, ParseException {
        
        Cliente cliente = obtenerClienteDesdeRequest(request);
        boolean resultado = clienteDAO.actualizarCliente(cliente);
        
        if (resultado) {
            response.sendRedirect("cliente?message=Cliente actualizado exitosamente");
        } else {
            response.sendRedirect("cliente?error=Error al actualizar el cliente");
        }
    }
    
    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        
        String codCli = request.getParameter("codCli");
        boolean resultado = clienteDAO.eliminarCliente(codCli);
        
        if (resultado) {
            response.sendRedirect("cliente?message=Cliente eliminado exitosamente");
        } else {
            response.sendRedirect("cliente?error=Error al eliminar el cliente");
        }
    }
    
    private Cliente obtenerClienteDesdeRequest(HttpServletRequest request) throws ParseException {
        String codCli = request.getParameter("codCli");
        String rsoCli = request.getParameter("rsoCli");
        String dirCli = request.getParameter("dirCli");
        String tlfCli = request.getParameter("tlfCli");
        String rucCli = request.getParameter("rucCli");
        String codDis = request.getParameter("codDis");
        String fecRegStr = request.getParameter("fecReg");
        String tipCli = request.getParameter("tipCli");
        String conCli = request.getParameter("conCli");
    
        Date fecReg;
        if (fecRegStr != null && !fecRegStr.trim().isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fecReg = sdf.parse(fecRegStr);
            } catch (ParseException e) {
            
                fecReg = new Date();
            }
        } else {
            
            fecReg = new Date();
        }
        
        Cliente cliente = new Cliente();
        cliente.setCodCli(codCli);
        cliente.setRsoCli(rsoCli);
        cliente.setDirCli(dirCli);
        cliente.setTlfCli(tlfCli);
        cliente.setRucCli(rucCli);
        cliente.setCodDis(codDis);
        cliente.setFecReg(fecReg);
        cliente.setTipCli(tipCli);
        cliente.setConCli(conCli);
        
        return cliente;
    }
}