package controladorDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class ReporteDAO {
    private Conexion conexion = new Conexion();
    public List<Object[]> obtenerVendedoresMenoresVentas(){
        List<Object[]> resultados = new ArrayList<>();
        String sql = "SELECT v.COD_VEN, v.NOM_VEN || ' ' || v.APE_VEN AS NOMBRE, " +
                     "COALESCE(SUM(df.CAN_VEN * df.PRE_VEN), 0) AS TOTAL_VENTAS, " +
                     "COUNT(DISTINCT f.NUM_FAC) AS CANT_FACTURAS " +
                     "FROM VENDEDOR v " +
                     "LEFT JOIN FACTURA f ON v.COD_VEN = f.COD_VEN " +
                     "LEFT JOIN DETALLE_FACTURA df ON f.NUM_FAC = df.NUM_FAC " +
                     "GROUP BY v.COD_VEN, v.NOM_VEN, v.APE_VEN " +
                     "ORDER BY TOTAL_VENTAS ASC " +
                     "LIMIT 3";
        
        try (
             PreparedStatement pstmt = conexion.getConnection().prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("COD_VEN"),
                    rs.getString("NOMBRE"),
                    rs.getDouble("TOTAL_VENTAS"),
                    rs.getInt("CANT_FACTURAS")
                };
                resultados.add(fila);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al generar reportes: " + e.getMessage());
        }
        return resultados;
    }
}