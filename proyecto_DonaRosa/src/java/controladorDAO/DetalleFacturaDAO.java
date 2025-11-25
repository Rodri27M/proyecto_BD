package controladorDAO;

import modelo.DetalleFactura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class DetalleFacturaDAO {
    private AuditoriaDAO auditoriaDAO = new AuditoriaDAO();
    private Conexion conexion = new Conexion();
   
    public boolean insertarDetalleFactura(DetalleFactura detalle) {
        String sql = "INSERT INTO DETALLE_FACTURA (NUM_FAC, COD_PRO, CAN_VEN, PRE_VEN) VALUES (?, ?, ?, ?)";
        boolean insercion = false;
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, detalle.getNumFac());
            pstmt.setString(2, detalle.getCodPro());
            pstmt.setInt(3, detalle.getCanVen());
            pstmt.setDouble(4, detalle.getPreVen());
            
            
            
        
            if (pstmt.executeUpdate() > 0) {
               
                insercion = true;
            }
            
      
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al crear factura: " + e.getMessage());
        }
        return insercion;
    }
    
   
    public List<DetalleFactura> obtenerDetallesPorFactura(String numFac) {
        List<DetalleFactura> detalles = new ArrayList<>();
        String sql = "SELECT df.*, p.DES_PRO FROM DETALLE_FACTURA df " +
                     "LEFT JOIN PRODUCTO p ON df.COD_PRO = p.COD_PRO " +
                     "WHERE df.NUM_FAC = ? ORDER BY df.COD_PRO";
        
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, numFac);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                DetalleFactura detalle = new DetalleFactura();
                detalle.setNumFac(rs.getString("NUM_FAC"));
                detalle.setCodPro(rs.getString("COD_PRO"));
                detalle.setCanVen(rs.getInt("CAN_VEN"));
                detalle.setPreVen(rs.getDouble("PRE_VEN"));
                detalle.setDesPro(rs.getString("DES_PRO"));
                detalle.setSubtotal(detalle.getCanVen() * detalle.getPreVen());
                detalles.add(detalle);
            }
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null, "Error al obtener factura: " + e.getMessage());
        }
        return detalles;
    }
    

    public DetalleFactura obtenerDetalle(String numFac, String codPro) {
        String sql = "SELECT df.*, p.DES_PRO FROM DETALLE_FACTURA df " +
                     "LEFT JOIN PRODUCTO p ON df.COD_PRO = p.COD_PRO " +
                     "WHERE df.NUM_FAC = ? AND df.COD_PRO = ?";
        DetalleFactura detalle = null;
        
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, numFac);
            pstmt.setString(2, codPro);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                detalle = new DetalleFactura();
                detalle.setNumFac(rs.getString("NUM_FAC"));
                detalle.setCodPro(rs.getString("COD_PRO"));
                detalle.setCanVen(rs.getInt("CAN_VEN"));
                detalle.setPreVen(rs.getDouble("PRE_VEN"));
                detalle.setDesPro(rs.getString("DES_PRO"));
                detalle.setSubtotal(detalle.getCanVen() * detalle.getPreVen());
            }
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null, "Error al obtener detalle factura: " + e.getMessage());
        }
        return detalle;
    }
    

    public boolean actualizarDetalleFactura(DetalleFactura detalle)  {
      
        DetalleFactura detalleAnterior = obtenerDetalle(detalle.getNumFac(), detalle.getCodPro());
        String datosAnteriores = null;
        if (detalleAnterior != null) {
            datosAnteriores = String.format("Factura: %s, Producto: %s, Cantidad: %d, Precio: %.2f", 
                detalleAnterior.getNumFac(), detalleAnterior.getCodPro(), 
                detalleAnterior.getCanVen(), detalleAnterior.getPreVen());
        }
        
        String sql = "UPDATE DETALLE_FACTURA SET CAN_VEN=?, PRE_VEN=? WHERE NUM_FAC=? AND COD_PRO=?";
        boolean insercion = false;
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {
            
            pstmt.setInt(1, detalle.getCanVen());
            pstmt.setDouble(2, detalle.getPreVen());
            pstmt.setString(3, detalle.getNumFac());
            pstmt.setString(4, detalle.getCodPro());
            
        
            
          
            if (pstmt.executeUpdate() > 0) {
       
                insercion = true;
            }
            
  
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al obtener detalle factura: " + e.getMessage());
        }
        return insercion;
    }
    

    public boolean eliminarDetalleFactura(String numFac, String codPro)  {
    
        DetalleFactura detalle = obtenerDetalle(numFac, codPro);
        String datosAnteriores = null;
        if (detalle != null) {
            datosAnteriores = String.format("Factura: %s, Producto: %s, Cantidad: %d, Precio: %.2f", 
                detalle.getNumFac(), detalle.getCodPro(), detalle.getCanVen(), detalle.getPreVen());
        }
        
        String sql = "DELETE FROM DETALLE_FACTURA WHERE NUM_FAC = ? AND COD_PRO = ?";
        boolean insercion = false;
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, numFac);
            pstmt.setString(2, codPro);

            
      
            
            if ( pstmt.executeUpdate() > 0) {
              
                insercion = true;
            }
            
    
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al obtener eliminar detalle factura: " + e.getMessage());
        }
        return insercion;
    }
    

    public double calcularTotalFactura(String numFac) {
        String sql = "SELECT SUM(CAN_VEN * PRE_VEN) as TOTAL FROM DETALLE_FACTURA WHERE NUM_FAC = ?";
        double total = 0.0;
        
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, numFac);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                total = rs.getDouble("TOTAL");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al obtener total de la factura: " + e.getMessage());
        }
        return total;
    }
    
 
    public List<String[]> obtenerProductos() {
        List<String[]> productos = new ArrayList<>();
        String sql = "SELECT COD_PRO, DES_PRO, PRE_PRO, SAC_PRO FROM PRODUCTO ORDER BY DES_PRO";
        
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String codPro = rs.getString("COD_PRO");
                String desPro = rs.getString("DES_PRO");
                double prePro = rs.getDouble("PRE_PRO");
                int sacPro = rs.getInt("SAC_PRO");
                String[] producto = {codPro, desPro, String.valueOf(prePro), String.valueOf(sacPro)};
                productos.add(producto);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }
}
