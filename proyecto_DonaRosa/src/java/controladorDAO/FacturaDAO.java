package controladorDAO;

import modelo.Factura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class FacturaDAO {

    private AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
    private Conexion conexion = new Conexion();

    public boolean insertarFactura(Factura factura) {
        String sql = "INSERT INTO FACTURA (NUM_FAC, FEC_FAC, COD_CLI, FEC_CAN, EST_FAC, COD_VEN, POR_IGV) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean insercion = false;
        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, factura.getNumFac());
            pstmt.setDate(2, new java.sql.Date(factura.getFecFac().getTime()));
            pstmt.setString(3, factura.getCodCli());

            if (factura.getFecCan() != null) {
                pstmt.setDate(4, new java.sql.Date(factura.getFecCan().getTime()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }

            pstmt.setString(5, factura.getEstFac());
            pstmt.setString(6, factura.getCodVen());
            pstmt.setDouble(7, factura.getPorIgv());

            if (pstmt.executeUpdate() > 0) {
             
                insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar factura: " + e.getMessage());
        }
        return insercion;
    }

    public List<Factura> obtenerTodasFacturas() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT f.*, c.RSO_CLI as CLIENTE, v.NOM_VEN || ' ' || v.APE_VEN as VENDEDOR "
                + "FROM FACTURA f "
                + "LEFT JOIN CLIENTE c ON f.COD_CLI = c.COD_CLI "
                + "LEFT JOIN VENDEDOR v ON f.COD_VEN = v.COD_VEN "
                + "ORDER BY f.FEC_FAC DESC, f.NUM_FAC";

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Factura factura = new Factura();
                factura.setNumFac(rs.getString("NUM_FAC"));
                factura.setFecFac(rs.getDate("FEC_FAC"));
                factura.setCodCli(rs.getString("COD_CLI"));
                factura.setFecCan(rs.getDate("FEC_CAN"));
                factura.setEstFac(rs.getString("EST_FAC"));
                factura.setCodVen(rs.getString("COD_VEN"));
                factura.setPorIgv(rs.getDouble("POR_IGV"));
                facturas.add(factura);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar facturas: " + e.getMessage());
        }
        return facturas;
    }

    public Factura obtenerFacturaPorNumero(String numFac) {
        String sql = "SELECT * FROM FACTURA WHERE NUM_FAC = ?";
        Factura factura = null;

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, numFac);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                factura = new Factura();
                factura.setNumFac(rs.getString("NUM_FAC"));
                factura.setFecFac(rs.getDate("FEC_FAC"));
                factura.setCodCli(rs.getString("COD_CLI"));
                factura.setFecCan(rs.getDate("FEC_CAN"));
                factura.setEstFac(rs.getString("EST_FAC"));
                factura.setCodVen(rs.getString("COD_VEN"));
                factura.setPorIgv(rs.getDouble("POR_IGV"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener factura: " + e.getMessage());
        }
        return factura;
    }

    public boolean actualizarFactura(Factura factura) {

        Factura facturaAnterior = obtenerFacturaPorNumero(factura.getNumFac());
        String datosAnteriores = null;
        if (facturaAnterior != null) {
            datosAnteriores = String.format("Número: %s, Estado: %s",
                    facturaAnterior.getNumFac(), facturaAnterior.getEstFac());
        }

        String sql = "UPDATE FACTURA SET FEC_FAC=?, COD_CLI=?, FEC_CAN=?, EST_FAC=?, COD_VEN=?, POR_IGV=? WHERE NUM_FAC=?";
        boolean insercion = false;
        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setDate(1, new java.sql.Date(factura.getFecFac().getTime()));
            pstmt.setString(2, factura.getCodCli());

            if (factura.getFecCan() != null) {
                pstmt.setDate(3, new java.sql.Date(factura.getFecCan().getTime()));
            } else {
                pstmt.setNull(3, Types.DATE);
            }

            pstmt.setString(4, factura.getEstFac());
            pstmt.setString(5, factura.getCodVen());
            pstmt.setDouble(6, factura.getPorIgv());
            pstmt.setString(7, factura.getNumFac());

            if (pstmt.executeUpdate() > 0) {
             
                insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar factura: " + e.getMessage());
        }
        return insercion;
    }

    public boolean eliminarFactura(String numFac) {

        Factura factura = obtenerFacturaPorNumero(numFac);
        String datosAnteriores = null;
        if (factura != null) {
            datosAnteriores = String.format("Número: %s, Estado: %s",
                    factura.getNumFac(), factura.getEstFac());
        }

        String sql = "DELETE FROM FACTURA WHERE NUM_FAC = ?";
        boolean insercion = false;
        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, numFac);

            if (pstmt.executeUpdate() > 0) {
           
                insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar factura" + e.getMessage());
        }
        return insercion;
    }

    public List<String[]> obtenerClientes() {
        List<String[]> clientes = new ArrayList<>();
        String sql = "SELECT COD_CLI, RSO_CLI FROM CLIENTE ORDER BY RSO_CLI";

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String codCli = rs.getString("COD_CLI");
                String rsoCli = rs.getString("RSO_CLI");
                String[] cliente = {codCli, rsoCli};
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener clientes: " + e.getMessage());
        }
        return clientes;
    }

    public List<String[]> obtenerVendedores() {
        List<String[]> vendedores = new ArrayList<>();
        String sql = "SELECT COD_VEN, NOM_VEN || ' ' || APE_VEN as NOMBRE FROM VENDEDOR ORDER BY NOM_VEN";

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String codVen = rs.getString("COD_VEN");
                String nombre = rs.getString("NOMBRE");
                String[] vendedor = {codVen, nombre};
                vendedores.add(vendedor);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener clientes: " + e.getMessage());
        }
        return vendedores;
    }
}
