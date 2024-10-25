package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
/**
 *
 * @author JHULINHO
 */
public class Conectar {
    Connection cn;
    Statement st;
    public Connection conexion(){
        try {
          cn=DriverManager.getConnection("jdbc:mysql://localhost/sistema","root","");
            System.out.println("conectado correctamente");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return cn;
    }
}
