package controladorDAO;

import modelo.Conexion;
import modelo.Vendedor;
import org.apache.tomcat.dbcp.dbcp2.SQLExceptionList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class VendedorDAO {

    private AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
    private Conexion conexion = new Conexion();

    public boolean insertarVendedor(Vendedor vendedor) {
        String sql = "INSERT INTO VENDEDOR (COD_VEN, NOM_VEN, APE_VEN, SUE_VEN, FIN_VEN, TIP_VEN, COD_DIS) VALUES (?, ?, ?, ?, ?, ?, ?)";
        boolean insercion = false;
        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, vendedor.getCodVen());
            pstmt.setString(2, vendedor.getNomVen());
            pstmt.setString(3, vendedor.getApeVen());
            pstmt.setDouble(4, vendedor.getSueVen());

            if (vendedor.getFinVen() != null) {
                pstmt.setDate(5, new java.sql.Date(vendedor.getFinVen().getTime()));
            } else {
                pstmt.setNull(5, Types.DATE);
            }

            pstmt.setString(6, vendedor.getTipVen());
            pstmt.setString(7, vendedor.getCodDis());

            if (pstmt.executeUpdate() > 0) {
            
                insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear vendedor: " + e.getMessage());
        }
        return insercion;
    }

    public List<Vendedor> obtenerTodosVendedores() {
        List<Vendedor> vendedores = new ArrayList<>();
        String sql = "SELECT v.*, d.NOM_DIS FROM VENDEDOR v LEFT JOIN DISTRITO d ON v.COD_DIS = d.COD_DIS ORDER BY v.COD_VEN";

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Vendedor vendedor = new Vendedor();
                vendedor.setCodVen(rs.getString("COD_VEN"));
                vendedor.setNomVen(rs.getString("NOM_VEN"));
                vendedor.setApeVen(rs.getString("APE_VEN"));
                vendedor.setSueVen(rs.getDouble("SUE_VEN"));
                vendedor.setFinVen(rs.getDate("FIN_VEN"));
                vendedor.setTipVen(rs.getString("TIP_VEN"));
                vendedor.setCodDis(rs.getString("COD_DIS"));
                vendedores.add(vendedor);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar vendedores: " + e.getMessage());
        }
        return vendedores;
    }

    public Vendedor obtenerVendedorPorCodigo(String codVen) throws SQLException {
        String sql = "SELECT * FROM VENDEDOR WHERE COD_VEN = ?";
        Vendedor vendedor = null;

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, codVen);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                vendedor = new Vendedor();
                vendedor.setCodVen(rs.getString("COD_VEN"));
                vendedor.setNomVen(rs.getString("NOM_VEN"));
                vendedor.setApeVen(rs.getString("APE_VEN"));
                vendedor.setSueVen(rs.getDouble("SUE_VEN"));
                vendedor.setFinVen(rs.getDate("FIN_VEN"));
                vendedor.setTipVen(rs.getString("TIP_VEN"));
                vendedor.setCodDis(rs.getString("COD_DIS"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar vendedor: " + e.getMessage());
        }
        return vendedor;
    }

    public boolean actualizarVendedor(Vendedor vendedor) throws SQLException {

        Vendedor vendedorAnterior = obtenerVendedorPorCodigo(vendedor.getCodVen());
        String datosAnteriores = null;
        if (vendedorAnterior != null) {
            datosAnteriores = String.format("Código: %s, Nombre: %s %s, Sueldo: %.2f",
                    vendedorAnterior.getCodVen(), vendedorAnterior.getNomVen(),
                    vendedorAnterior.getApeVen(), vendedorAnterior.getSueVen());
        }

        String sql = "UPDATE VENDEDOR SET NOM_VEN=?, APE_VEN=?, SUE_VEN=?, FIN_VEN=?, TIP_VEN=?, COD_DIS=? WHERE COD_VEN=?";
        boolean insercion = false;
        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, vendedor.getNomVen());
            pstmt.setString(2, vendedor.getApeVen());
            pstmt.setDouble(3, vendedor.getSueVen());

            if (vendedor.getFinVen() != null) {
                pstmt.setDate(4, new java.sql.Date(vendedor.getFinVen().getTime()));
            } else {
                pstmt.setNull(4, Types.DATE);
            }

            pstmt.setString(5, vendedor.getTipVen());
            pstmt.setString(6, vendedor.getCodDis());
            pstmt.setString(7, vendedor.getCodVen());

            if (pstmt.executeUpdate() > 0) {
             
                insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar vendedor: " + e.getMessage());
        }
        return insercion;
    }

    public boolean eliminarVendedor(String codVen) throws SQLException {

        Vendedor vendedor = obtenerVendedorPorCodigo(codVen);
        String datosAnteriores = null;
        if (vendedor != null) {
            datosAnteriores = String.format("Código: %s, Nombre: %s %s, Sueldo: %.2f",
                    vendedor.getCodVen(), vendedor.getNomVen(), vendedor.getApeVen(), vendedor.getSueVen());
        }

        String sql = "DELETE FROM VENDEDOR WHERE COD_VEN = ?";
        boolean insercion = false;
        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, codVen);
      

            if (pstmt.executeUpdate() > 0) {
               insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar vendedor: " + e.getMessage());

        }
        return insercion;
    }

    public List<String[]> obtenerDistritos() {
        List<String[]> distritos = new ArrayList<>();
        String sql = "SELECT COD_DIS, NOM_DIS FROM DISTRITO ORDER BY NOM_DIS";

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String codDis = rs.getString("COD_DIS");
                String nomDis = rs.getString("NOM_DIS");
                String[] distrito = {codDis, nomDis};
                distritos.add(distrito);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return distritos;
    }
}
