package basededatos.dao;

import basededatos.conexion.Conexion;
import basededatos.entidad.Profesor;

import java.sql.*;
import java.util.ArrayList;

public class ProfesorDAO implements IDAO<Profesor> {

    public ProfesorDAO() throws DAOException {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() throws DAOException {
        String sql = """
            CREATE TABLE IF NOT EXISTS profesores (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(100) NOT NULL,
                email VARCHAR(100) NOT NULL
            )
        """;

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DAOException("Error al crear la tabla profesores", e);
        }
    }

    @Override
    public void guardar(Profesor profesor) throws DAOException {
        String sql = "INSERT INTO profesores (nombre, email) VALUES (?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, profesor.getNombre());
            stmt.setString(2, profesor.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al guardar profesor", e);
        }
    }

    @Override
    public void modificar(Profesor profesor) throws DAOException {
        String sql = "UPDATE profesores SET nombre = ?, email = ? WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, profesor.getNombre());
            stmt.setString(2, profesor.getEmail());
            stmt.setInt(3, profesor.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al modificar profesor", e);
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM profesores WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al eliminar profesor", e);
        }
    }

    @Override
    public Profesor buscar(int id) throws DAOException {
        String sql = "SELECT * FROM profesores WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Profesor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email")
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar profesor", e);
        }
    }

    @Override
    public ArrayList<Profesor> buscarTodos() throws DAOException {
        ArrayList<Profesor> lista = new ArrayList<>();
        String sql = "SELECT * FROM profesores";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Profesor profesor = new Profesor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email")
                );
                lista.add(profesor);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al listar profesores", e);
        }

        return lista;
    }
}
