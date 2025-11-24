package controladorDAO;

import modelo.Cliente;
import modelo.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClienteDAO {

    private AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
    private Conexion conexion = new Conexion();

    public boolean insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO CLIENTE (COD_CLI, RSO_CLI, DIR_CLI, TLF_CLI, RUC_CLI, COD_DIS, FEC_REG, TIP_CLI, CON_CLI) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        boolean insercion = false;

        try (PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, cliente.getCodCli());
            pstmt.setString(2, cliente.getRsoCli());
            pstmt.setString(3, cliente.getDirCli());
            pstmt.setString(4, cliente.getTlfCli());
            pstmt.setString(5, cliente.getRucCli());
            pstmt.setString(6, cliente.getCodDis());
           if (cliente.getFecReg() != null) {
                pstmt.setDate(7, new java.sql.Date(cliente.getFecReg().getTime()));
            } else {
                pstmt.setDate(7, new java.sql.Date(System.currentTimeMillis()));
            }
            pstmt.setString(8, cliente.getTipCli());
            pstmt.setString(9, cliente.getConCli());

            if (pstmt.executeUpdate() > 0) {
                String datosNuevos = String.format("Código: %s, Razón Social: %s, RUC: %s",
                        cliente.getCodCli(), cliente.getRsoCli(), cliente.getRucCli());
                auditoriaDAO.registrarAuditoria("CLIENTE", cliente.getCodCli(),
                        "INSERT", "Sistema", null, datosNuevos);
                insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar: " + e.getSQLState());
        }
        return insercion;
    }

    public List<Cliente> obtenerTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT c.*, d.NOM_DIS FROM CLIENTE c LEFT JOIN DISTRITO d ON c.COD_DIS = d.COD_DIS ORDER BY c.COD_CLI";

        try (
                Statement stmt = conexion.getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setCodCli(rs.getString("COD_CLI"));
                cliente.setRsoCli(rs.getString("RSO_CLI"));
                cliente.setDirCli(rs.getString("DIR_CLI"));
                cliente.setTlfCli(rs.getString("TLF_CLI"));
                cliente.setRucCli(rs.getString("RUC_CLI"));
                cliente.setCodDis(rs.getString("COD_DIS"));
                cliente.setFecReg(rs.getDate("FEC_REG"));
                cliente.setTipCli(rs.getString("TIP_CLI"));
                cliente.setConCli(rs.getString("CON_CLI"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return clientes;
    }

    public Cliente obtenerClientePorCodigo(String codCli) {
        String sql = "SELECT * FROM CLIENTE WHERE COD_CLI = ?";
        Cliente cliente = null;

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, codCli);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setCodCli(rs.getString("COD_CLI"));
                cliente.setRsoCli(rs.getString("RSO_CLI"));
                cliente.setDirCli(rs.getString("DIR_CLI"));
                cliente.setTlfCli(rs.getString("TLF_CLI"));
                cliente.setRucCli(rs.getString("RUC_CLI"));
                cliente.setCodDis(rs.getString("COD_DIS"));
                cliente.setFecReg(rs.getDate("FEC_REG"));
                cliente.setTipCli(rs.getString("TIP_CLI"));
                cliente.setConCli(rs.getString("CON_CLI"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return cliente;
    }

    public boolean actualizarCliente(Cliente cliente) {

        Cliente clienteAnterior = obtenerClientePorCodigo(cliente.getCodCli());
        String datosAnteriores = null;
        if (clienteAnterior != null) {
            datosAnteriores = String.format("Código: %s, Razón Social: %s, RUC: %s",
                    clienteAnterior.getCodCli(), clienteAnterior.getRsoCli(), clienteAnterior.getRucCli());
        }

        String sql = "UPDATE CLIENTE SET RSO_CLI=?, DIR_CLI=?, TLF_CLI=?, RUC_CLI=?, COD_DIS=?, FEC_REG=?, TIP_CLI=?, CON_CLI=? WHERE COD_CLI=?";
        boolean insercion = false;
        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, cliente.getRsoCli());
            pstmt.setString(2, cliente.getDirCli());
            pstmt.setString(3, cliente.getTlfCli());
            pstmt.setString(4, cliente.getRucCli());
            pstmt.setString(5, cliente.getCodDis());
            pstmt.setDate(6, new java.sql.Date(cliente.getFecReg().getTime()));
            pstmt.setString(7, cliente.getTipCli());
            pstmt.setString(8, cliente.getConCli());
            pstmt.setString(9, cliente.getCodCli());

            if (pstmt.executeUpdate() > 0) {
                String datosNuevos = String.format("Código: %s, Razón Social: %s, RUC: %s",
                        cliente.getCodCli(), cliente.getRsoCli(), cliente.getRucCli());
                auditoriaDAO.registrarAuditoria("CLIENTE", cliente.getCodCli(),
                        "UPDATE", "Sistema", datosAnteriores, datosNuevos);
                insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return insercion;
    }

    public boolean eliminarCliente(String codCli) {

        Cliente cliente = obtenerClientePorCodigo(codCli);
        String datosAnteriores = null;
        if (cliente != null) {
            datosAnteriores = String.format("Código: %s, Razón Social: %s, RUC: %s",
                    cliente.getCodCli(), cliente.getRsoCli(), cliente.getRucCli());
        }
        boolean insercion = false;
        String sql = "DELETE FROM CLIENTE WHERE COD_CLI = ?";

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, codCli);

            if (pstmt.executeUpdate() > 0) {
                auditoriaDAO.registrarAuditoria("CLIENTE", codCli,
                        "DELETE", "Sistema", datosAnteriores, null);
                insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return insercion;
    }

    public List<String[]> obtenerDistritos() {
 List<String[]> distritos = new ArrayList<>();
    String sql = "SELECT COD_DIS, NOM_DIS FROM DISTRITO ORDER BY NOM_DIS";
    
    try (
         PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        
        while (rs.next()) {
            String codDis = rs.getString("COD_DIS");
            String nomDis = rs.getString("NOM_DIS");
            String[] distrito = {codDis, nomDis};
            distritos.add(distrito);
        }
    }catch(SQLException e){
       JOptionPane.showMessageDialog(null,"Error:" + e.getMessage());
    }
    return distritos;  
    }
}
