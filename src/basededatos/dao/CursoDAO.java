package basededatos.dao;

import basededatos.entidad.Curso;
import basededatos.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;

public class CursoDAO implements IDAO<Curso> {

    public CursoDAO() throws DAOException {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() throws DAOException {
        String sql = "CREATE TABLE IF NOT EXISTS cursos (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "nombre VARCHAR(100) NOT NULL," +
                "precio DOUBLE NOT NULL," +
                "cupo_maximo INT NOT NULL," +
                "nota_aprobacion DOUBLE NOT NULL" +
                ")";
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DAOException("Error al crear tabla cursos", e);
        }
    }

    public int contarInscriptos(int idCurso) throws DAOException {
        String sql = "SELECT COUNT(*) FROM inscripciones WHERE curso_id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCurso);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException("Error al contar inscriptos del curso", e);
        }
        return 0;
    }


    @Override
    public void guardar(Curso curso) throws DAOException {
        String sql = "INSERT INTO cursos (nombre, precio, cupo_maximo, nota_aprobacion) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNombre());
            stmt.setDouble(2, curso.getPrecio());
            stmt.setInt(3, curso.getCupoMaximo());
            stmt.setDouble(4, curso.getNotaAprobacion());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al guardar curso", e);
        }
    }

    @Override
    public void modificar(Curso curso) throws DAOException {
        String sql = "UPDATE cursos SET nombre = ?, precio = ?, cupo_maximo = ?, nota_aprobacion = ? WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNombre());
            stmt.setDouble(2, curso.getPrecio());
            stmt.setInt(3, curso.getCupoMaximo());
            stmt.setDouble(4, curso.getNotaAprobacion());
            stmt.setInt(5, curso.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al modificar curso", e);
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM cursos WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar curso", e);
        }
    }

    @Override
    public Curso buscar(int id) throws DAOException {
        String sql = "SELECT * FROM cursos WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("cupo_maximo"),
                        rs.getDouble("nota_aprobacion")
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar curso", e);
        }
    }

    @Override
    public ArrayList<Curso> buscarTodos() throws DAOException {
        ArrayList<Curso> lista = new ArrayList<>();
        String sql = "SELECT * FROM cursos";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("cupo_maximo"),
                        rs.getDouble("nota_aprobacion")
                );
                lista.add(curso);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al listar cursos", e);
        }

        return lista;
    }
}
