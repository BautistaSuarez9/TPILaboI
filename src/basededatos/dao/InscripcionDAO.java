package basededatos.dao;

import basededatos.conexion.Conexion;
import basededatos.entidad.Alumno;
import basededatos.entidad.Curso;
import basededatos.entidad.Inscripcion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAO implements IDAO<Inscripcion> {

    public InscripcionDAO() throws DAOException {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() throws DAOException {
        String sql = """
            CREATE TABLE IF NOT EXISTS inscripciones (
                id INT AUTO_INCREMENT PRIMARY KEY,
                alumno_id INT NOT NULL,
                curso_id INT NOT NULL,
                nota_final DOUBLE,
                estado VARCHAR(20) DEFAULT 'pendiente',
                FOREIGN KEY (alumno_id) REFERENCES alumnos(id),
                FOREIGN KEY (curso_id) REFERENCES cursos(id)
            )""";
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DAOException("Error al crear la tabla inscripciones", e);
        }
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
            throw new DAOException("Error al actualizar nota y estado", e);
        }
    }

    @Override
    public void guardar(Inscripcion inscripcion) throws DAOException {
        String sql = "INSERT INTO inscripciones (alumno_id, curso_id, nota_final, estado) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, inscripcion.getAlumno().getId());
            stmt.setInt(2, inscripcion.getCurso().getId());


            if (inscripcion.getNotaFinal() != null) {
                stmt.setDouble(3, inscripcion.getNotaFinal());
            } else {
                stmt.setNull(3, Types.DOUBLE);
            }

            stmt.setString(4, inscripcion.getEstado());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al guardar inscripción", e);
        }
    }

    @Override
    public void modificar(Inscripcion inscripcion) {
        throw new UnsupportedOperationException("No implementado.");
    }

    @Override
    public void eliminar(int id) {
        throw new UnsupportedOperationException("No implementado.");
    }

    @Override
    public Inscripcion buscar(int id) {
        throw new UnsupportedOperationException("No implementado.");
    }

    @Override
    public ArrayList<Inscripcion> buscarTodos() throws DAOException {
        List<Inscripcion> lista = new ArrayList<>();
        String sql = "SELECT * FROM inscripciones";

        try (
                Connection conn = Conexion.getConexion();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)
        ) {
            AlumnoDAO alumnoDAO = new AlumnoDAO();
            CursoDAO cursoDAO = new CursoDAO();

            while (rs.next()) {
                int id = rs.getInt("id");
                int alumnoId = rs.getInt("alumno_id");
                int cursoId = rs.getInt("curso_id");
                Double nota = rs.getObject("nota_final") != null ? rs.getDouble("nota_final") : null;
                String estado = rs.getString("estado");

                Alumno alumno = alumnoDAO.buscar(alumnoId);
                Curso curso = cursoDAO.buscar(cursoId);

                Inscripcion inscripcion = new Inscripcion(id, alumno, curso, nota, estado);
                lista.add(inscripcion);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener inscripciones", e);
        }

        return new ArrayList<>(lista);
    }

}
