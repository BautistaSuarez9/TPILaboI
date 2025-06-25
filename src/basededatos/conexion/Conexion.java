package basededatos.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:h2:C:/Users/bauti/Documents/Facultad/labo I/TPI/TPILaboI/basedatos/alumnos";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}