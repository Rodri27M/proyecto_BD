
package controladorDAO;

import modelo.Distrito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;
public class DistritoDAO {
    private Conexion conexion = new Conexion();
    
    
        public boolean insertarDistrito(Distrito distrito) throws SQLException {
        String sql = "INSERT INTO DISTRITO (COD_DIS, NOM_DIS) VALUES (?, ?)";
        boolean insercion = false;
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, distrito.getCodDis());
            pstmt.setString(2, distrito.getNomDis());
            
         if(pstmt.executeUpdate() > 0){
             insercion = true;
         }
        }catch(SQLException e){
       JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());

        }
        return insercion;
    
}
           public List<Distrito> obtenerTodosDistritos() throws SQLException {
        List<Distrito> distritos = new ArrayList<>();
        String sql = "SELECT * FROM DISTRITO ORDER BY COD_DIS";
        
        try (
             Statement stmt = conexion.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Distrito distrito = new Distrito();
                distrito.setCodDis(rs.getString("COD_DIS"));
                distrito.setNomDis(rs.getString("NOM_DIS"));
                distritos.add(distrito);
            }
        }catch(SQLException e){
         JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return distritos;
    }

             public Distrito obtenerDistritoPorCodigo(String codDis) throws SQLException {
        String sql = "SELECT * FROM DISTRITO WHERE COD_DIS = ?";
        Distrito distrito = null;
        
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, codDis);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                distrito = new Distrito();
                distrito.setCodDis(rs.getString("COD_DIS"));
                distrito.setNomDis(rs.getString("NOM_DIS"));
            }
        }catch (SQLException e){
              JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return distrito;
    }
             
    public boolean actualizarDistrito(Distrito distrito) throws SQLException {
        String sql = "UPDATE DISTRITO SET NOM_DIS = ? WHERE COD_DIS = ?";
        boolean insercion = false;
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, distrito.getNomDis());
            pstmt.setString(2, distrito.getCodDis());
            
         if(pstmt.executeUpdate() > 0){
             insercion = true;
         }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return insercion;
    }
    
    public boolean eliminarDistrito(String codDis) throws SQLException {
        String sql = "DELETE FROM DISTRITO WHERE COD_DIS = ?";
        boolean insercion = false;
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql)) {
            
            pstmt.setString(1, codDis);
         if (pstmt.executeUpdate() > 0){
             insercion = true;
         }
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return insercion;
    }

             
}
