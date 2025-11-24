package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;   
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
public class Conexion {
  
   private static Connection conexion = null;
   private final String url = "jdbc:postgresql://localhost:5432/inventario";
   private final String usuario = "postgres";
   private final String contraseña = "2003rodri"; 
   
   public Connection getConnection() {
       //Declara una variable conexion de tipo Connection que almacenará la conexión con la base de datos.
       if(conexion == null){
       try {
           // Cargar el driver de PostgreSQL
           Class.forName("org.postgresql.Driver");

           // Establecer conexión
          //Establece la conexión con los atributos.
           conexion = DriverManager.getConnection(url, usuario, contraseña);
//           ventana.mostrarMensaje("Conexión exitosa");

       } catch (ClassNotFoundException e) {
            //Si ocurre un error al cargar el driver.
//          ventana.mostrarMensaje("Error: No se encontró el driver de PostgreSQL");
            JOptionPane.showMessageDialog(null, "Error: No se encontró el driver de PostgreSQL");
       } catch (SQLException e) {
           //Si ocurre un problema con la conexión (credenciales incorrectas, base de datos caída, etc.).
//          ventana.mostrarMensaje("Error de conexión: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage());
       }
       }
       return conexion;
   }

    
}

