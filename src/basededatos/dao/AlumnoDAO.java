package basededatos.dao;
import basededatos.dao.IDAO;


import basededatos.entidad.Alumno;
import basededatos.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO implements IDAO<Alumno> {

    public AlumnoDAO() {
    }

    public void crearTablaSiNoExiste() throws DAOException {
        String sql = "CREATE TABLE IF NOT EXISTS alumnos (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nombre VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "limite_cursos INT NOT NULL)";
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DAOException("Error al crear la tabla alumnos", e);
        }
    }
    @Override
    public void guardar(Alumno alumno) throws DAOException {
        String sql = "INSERT INTO alumnos (nombre, email, limite_cursos) VALUES (?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getEmail());
            stmt.setInt(3, alumno.getLimiteCursos());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al guardar alumno", e);
        }
    }

    @Override
    public void modificar(Alumno alumno) throws DAOException {
        String sql = "UPDATE alumnos SET nombre = ?, email = ?, limite_cursos = ? WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getEmail());
            stmt.setInt(3, alumno.getLimiteCursos());
            stmt.setInt(4, alumno.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al modificar alumno", e);
        }
    }


    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM alumnos WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al eliminar alumno", e);
        }
    }


    @Override
    public Alumno buscar(int id) throws DAOException {
        String sql = "SELECT * FROM alumnos WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Alumno(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getInt("limite_cursos")
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar alumno", e);
        }
    }

    @Override
    public ArrayList<Alumno> buscarTodos() throws DAOException {
        ArrayList<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(rs.getInt("id"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setEmail(rs.getString("email"));
                alumno.setLimiteCursos(rs.getInt("limite_cursos"));
                lista.add(alumno);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar todos los alumnos", e);
        }

        return lista;
    }
}
