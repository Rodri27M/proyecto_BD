package controladorDAO;

import modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class ProductoDAO {

    private AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
    private Conexion conexion = new Conexion();

    public boolean insertarProducto(Producto producto) {
        String sql = "INSERT INTO PRODUCTO (COD_PRO, DES_PRO, PRE_PRO, SAC_PRO, SMI_PRO, UNI_PRO, LIN_PRO, IMP_PRO) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        boolean insercion = false;
        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, producto.getCodPro());
            pstmt.setString(2, producto.getDesPro());
            pstmt.setDouble(3, producto.getPrePro());
            pstmt.setInt(4, producto.getSacPro());
            pstmt.setInt(5, producto.getSmiPro());
            pstmt.setString(6, producto.getUniPro());
            pstmt.setString(7, producto.getLinPro());
            pstmt.setString(8, producto.getImpPro());

            if (pstmt.executeUpdate() > 0) {

                insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear producto: " + e.getMessage());
        }
        return insercion;
    }

    public List<Producto> obtenerTodosProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTO ORDER BY COD_PRO";

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setCodPro(rs.getString("COD_PRO"));
                producto.setDesPro(rs.getString("DES_PRO"));
                producto.setPrePro(rs.getDouble("PRE_PRO"));
                producto.setSacPro(rs.getInt("SAC_PRO"));
                producto.setSmiPro(rs.getInt("SMI_PRO"));
                producto.setUniPro(rs.getString("UNI_PRO"));
                producto.setLinPro(rs.getString("LIN_PRO"));
                producto.setImpPro(rs.getString("IMP_PRO"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e.getMessage());
        }
        return productos;
    }

    public Producto obtenerProductoPorCodigo(String codPro) {
        String sql = "SELECT * FROM PRODUCTO WHERE COD_PRO = ?";
        Producto producto = null;

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, codPro);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                producto = new Producto();
                producto.setCodPro(rs.getString("COD_PRO"));
                producto.setDesPro(rs.getString("DES_PRO"));
                producto.setPrePro(rs.getDouble("PRE_PRO"));
                producto.setSacPro(rs.getInt("SAC_PRO"));
                producto.setSmiPro(rs.getInt("SMI_PRO"));
                producto.setUniPro(rs.getString("UNI_PRO"));
                producto.setLinPro(rs.getString("LIN_PRO"));
                producto.setImpPro(rs.getString("IMP_PRO"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e.getMessage());

        }
        return producto;
    }

    public boolean actualizarProducto(Producto producto) {

        Producto productoAnterior = obtenerProductoPorCodigo(producto.getCodPro());
        String datosAnteriores = null;
        if (productoAnterior != null) {
            datosAnteriores = String.format("Código: %s, Descripción: %s, Precio: %.2f, Stock: %d",
                    productoAnterior.getCodPro(), productoAnterior.getDesPro(),
                    productoAnterior.getPrePro(), productoAnterior.getSacPro());
        }

        String sql = "UPDATE PRODUCTO SET DES_PRO=?, PRE_PRO=?, SAC_PRO=?, SMI_PRO=?, UNI_PRO=?, LIN_PRO=?, IMP_PRO=? WHERE COD_PRO=?";
        boolean insercion = false;
        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, producto.getDesPro());
            pstmt.setDouble(2, producto.getPrePro());
            pstmt.setInt(3, producto.getSacPro());
            pstmt.setInt(4, producto.getSmiPro());
            pstmt.setString(5, producto.getUniPro());
            pstmt.setString(6, producto.getLinPro());
            pstmt.setString(7, producto.getImpPro());
            pstmt.setString(8, producto.getCodPro());

            if (pstmt.executeUpdate() > 0) {
             
                insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e.getMessage());

        }
        return insercion;
    }

    public boolean eliminarProducto(String codPro) {

        Producto producto = obtenerProductoPorCodigo(codPro);
        String datosAnteriores = null;
        if (producto != null) {
            datosAnteriores = String.format("Código: %s, Descripción: %s, Precio: %.2f, Stock: %d",
                    producto.getCodPro(), producto.getDesPro(), producto.getPrePro(), producto.getSacPro());
        }

        String sql = "DELETE FROM PRODUCTO WHERE COD_PRO = ?";
        boolean insercion = true;
        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setString(1, codPro);

            if (pstmt.executeUpdate() > 0) {
              
                insercion = true;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e.getMessage());
        }
        return insercion;
    }

    public boolean validarStock(String codPro, int cantidadRequerida) throws SQLException {
        Producto producto = obtenerProductoPorCodigo(codPro);
        if (producto != null) {
            return producto.getSacPro() >= cantidadRequerida;
        }
        return false;
    }

    public boolean actualizarStock(String codPro, int cantidadVendida) throws SQLException {
        String sql = "UPDATE PRODUCTO SET SAC_PRO = SAC_PRO - ? WHERE COD_PRO = ? AND SAC_PRO >= ?";

        try (
                PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {

            pstmt.setInt(1, cantidadVendida);
            pstmt.setString(2, codPro);
            pstmt.setInt(3, cantidadVendida);

            return pstmt.executeUpdate() > 0;
        }
    }
}
