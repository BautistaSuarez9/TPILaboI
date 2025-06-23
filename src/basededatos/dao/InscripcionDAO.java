package basededatos.dao;

import basededatos.conexion.Conexion;
import basededatos.entidad.Inscripcion;

import java.sql.*;
import java.util.ArrayList;

public class InscripcionDAO implements IDAO<Inscripcion> {

    public InscripcionDAO() throws DAOException {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() throws DAOException {
        String sql = "CREATE TABLE IF NOT EXISTS inscripciones (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "alumno_id INT NOT NULL," +
                "curso_id INT NOT NULL," +
                "nota_final DOUBLE," +
                "estado VARCHAR(20) DEFAULT 'pendiente'," +
                "FOREIGN KEY (alumno_id) REFERENCES alumnos(id)," +
                "FOREIGN KEY (curso_id) REFERENCES cursos(id)" +
                ")";
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DAOException("Error al crear tabla inscripciones", e);
        }
    }

    public void actualizarNotaEstado(int alumnoId, int cursoId, int nota, String estado) throws DAOException {
        String sql = "UPDATE inscripciones SET nota_final = ?, estado = ? WHERE alumno_id = ? AND curso_id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, nota);
            stmt.setString(2, estado);
            stmt.setInt(3, alumnoId);
            stmt.setInt(4, cursoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al actualizar nota/estado", e);
        }
    }


    @Override
    public void guardar(Inscripcion i) throws DAOException {
        String sql = "INSERT INTO inscripciones (alumno_id, curso_id, nota_final, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, i.getAlumnoId());
            stmt.setInt(2, i.getCursoId());
            if (i.getNotaFinal() != null) {
                stmt.setDouble(3, i.getNotaFinal());
            } else {
                stmt.setNull(3, Types.DOUBLE);
            }
            stmt.setString(4, i.getEstado());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al guardar inscripción", e);
        }
    }

    @Override
    public void modificar(Inscripcion i) throws DAOException {
        /*String sql = "UPDATE inscripciones SET alumno_id = ?, curso_id = ?, nota_final = ?, estado = ? WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, i.getAlumnoId());
            stmt.setInt(2, i.getCursoId());
            if (i.getNotaFinal() != null) {
                stmt.setDouble(3, i.getNotaFinal());
            } else {
                stmt.setNull(3, Types.DOUBLE);
            }
            stmt.setString(4, i.getEstado());
            stmt.setInt(5, i.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al modificar inscripción", e);
        }*/
        throw new UnsupportedOperationException("No implementado.");
    }

    @Override
    public void eliminar(int id) throws DAOException {
        /*String sql = "DELETE FROM inscripciones WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar inscripción", e);
        }*/
        throw new UnsupportedOperationException("No implementado.");
    }

    @Override
    public Inscripcion buscar(int id) throws DAOException {
        /*String sql = "SELECT * FROM inscripciones WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Inscripcion(
                        rs.getInt("id"),
                        rs.getInt("alumno_id"),
                        rs.getInt("curso_id"),
                        rs.getDouble("nota_final"),
                        rs.getString("estado")
                );
            }
            return null;

        } catch (SQLException e) {
            throw new DAOException("Error al buscar inscripción", e);
        }*/
        throw new UnsupportedOperationException("No implementado.");
    }

    @Override
    public ArrayList<Inscripcion> buscarTodos() throws DAOException {
        ArrayList<Inscripcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM inscripciones";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Inscripcion i = new Inscripcion(
                        rs.getInt("id"),
                        rs.getInt("alumno_id"),
                        rs.getInt("curso_id"),
                        rs.getDouble("nota_final"),
                        rs.getString("estado")
                );
                lista.add(i);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar inscripciones", e);
        }

        return lista;
    }
    public void inscribir(int idAlumno, int idCurso) throws DAOException {
        String sql = "INSERT INTO inscripciones (alumno_id, curso_id, estado) VALUES (?, ?, 'inscripto')";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlumno);
            stmt.setInt(2, idCurso);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al inscribir alumno al curso", e);
        }
    }

}
