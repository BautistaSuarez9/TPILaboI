package basededatos;

import basededatos.conexion.Conexion;

import java.sql.Connection;
import java.sql.Statement;

public class TestConexion {
    public static void main(String[] args) {
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {
            System.out.println("Conexión exitosa.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
