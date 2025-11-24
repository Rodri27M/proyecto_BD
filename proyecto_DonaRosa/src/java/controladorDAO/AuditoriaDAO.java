package controladorDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class AuditoriaDAO {
    private Conexion conexion = new Conexion();
    public void registrarAuditoria(String nomTabla, String clavePrimaria, 
                                 String operacion, String usuario,
                                 String datosAnteriores, String datosNuevos) {
        String sql = "INSERT INTO AUDITORIA (NOM_TABLA, CLAVE_PRIMARIA, OPERACION, USUARIO, DATOS_ANTERIORES, DATOS_NUEVOS) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, nomTabla);
            pstmt.setString(2, clavePrimaria);
            pstmt.setString(3, operacion);
            pstmt.setString(4, usuario);
            pstmt.setString(5, datosAnteriores);
            pstmt.setString(6, datosNuevos);
            
            pstmt.executeUpdate();
        }catch(SQLException e){
                   JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());

        }
    }
    
    public List<Object[]> obtenerAuditoria() {
        List<Object[]> auditoria = new ArrayList<>();
        String sql = "SELECT * FROM AUDITORIA ORDER BY FECHA DESC";
        
        try (
             Statement stmt = conexion.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("ID_AUDIT"),
                    rs.getString("NOM_TABLA"),
                    rs.getString("CLAVE_PRIMARIA"),
                    rs.getString("OPERACION"),
                    rs.getString("USUARIO"),
                    rs.getTimestamp("FECHA"),
                    rs.getString("DATOS_ANTERIORES"),
                    rs.getString("DATOS_NUEVOS")
                };
                auditoria.add(fila);
            }
        }catch(SQLException e){
              JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());

        }
        return auditoria;
    }
}