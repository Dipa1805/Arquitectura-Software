package Conexion;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author diego
 */
public class ConexionBD {

    public Connection conectar() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=VentaCreCon;encrypt=false;trustServerCertificate=true"; 
        String usuario = "sa";
        String contraseña = "12345";

        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }

        return conexion;
    }
    
    public boolean insertarVenta(String cliente, String ruc, String producto, int cantidad, double precioUnitario, Date fecha) {
        String sql = "INSERT INTO Ventas (Cliente, RUC, Producto, Cantidad, precio, fecha) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = conectar(); PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setString(1, cliente);
            statement.setString(2, ruc);
            statement.setString(3, producto);
            statement.setInt(4, cantidad);
            statement.setDouble(5, precioUnitario);
            statement.setDate(6, fecha);

            int filasInsertadas = statement.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Venta insertada correctamente en la base de datos.");
                return true;
            } else {
                System.out.println("No se insertó ninguna fila en la base de datos.");
                return false; // Inserción fallida
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar la venta: " + e.getMessage());
            return false;
        }
    }
}
