package basededatos.dao;

import basededatos.entidad.Curso;
import basededatos.entidad.Profesor;
import basededatos.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;

public class CursoDAO implements IDAO<Curso> {

    private final ProfesorDAO profesorDAO;

    public CursoDAO() throws DAOException {
        this.profesorDAO = new ProfesorDAO();
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() throws DAOException {
        String sql = """
            CREATE TABLE IF NOT EXISTS cursos (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nombre VARCHAR(100) NOT NULL,
                precio DOUBLE NOT NULL,
                cupo_maximo INT NOT NULL,
                nota_aprobacion DOUBLE NOT NULL,
                profesor_id INT,
                FOREIGN KEY (profesor_id) REFERENCES profesores(id)
            )
        """;
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DAOException("Error al crear tabla cursos", e);
        }
    }

    @Override
    public void guardar(Curso curso) throws DAOException {
        String sql = "INSERT INTO cursos (nombre, precio, cupo_maximo, nota_aprobacion, profesor_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNombre());
            stmt.setDouble(2, curso.getPrecio());
            stmt.setInt(3, curso.getCupoMaximo());
            stmt.setDouble(4, curso.getNotaAprobacion());

            if (curso.getProfesor() != null) {
                stmt.setInt(5, curso.getProfesor().getId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al guardar curso", e);
        }
    }

    @Override
    public void modificar(Curso curso) throws DAOException {
        String sql = "UPDATE cursos SET nombre = ?, precio = ?, cupo_maximo = ?, nota_aprobacion = ?, profesor_id = ? WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, curso.getNombre());
            stmt.setDouble(2, curso.getPrecio());
            stmt.setInt(3, curso.getCupoMaximo());
            stmt.setDouble(4, curso.getNotaAprobacion());

            if (curso.getProfesor() != null) {
                stmt.setInt(5, curso.getProfesor().getId());
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setInt(6, curso.getId());

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
                Profesor profesor = null;
                if (rs.getObject("profesor_id") != null) {
                    profesor = profesorDAO.buscar(rs.getInt("profesor_id"));
                }

                return new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("cupo_maximo"),
                        rs.getDouble("nota_aprobacion"),
                        profesor
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
                Profesor profesor = null;
                if (rs.getObject("profesor_id") != null) {
                    profesor = profesorDAO.buscar(rs.getInt("profesor_id"));
                }

                Curso curso = new Curso(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("cupo_maximo"),
                        rs.getDouble("nota_aprobacion"),
                        profesor
                );
                lista.add(curso);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al listar cursos", e);
        }

        return lista;
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

    public ArrayList<Object[]> obtenerReporteCursos() throws DAOException {
        String sql = """
        SELECT 
            c.nombre,
            COUNT(i.id) AS inscriptos,
            c.precio,
            COUNT(i.id) * c.precio AS recaudacion
        FROM cursos c
        LEFT JOIN inscripciones i ON c.id = i.curso_id
        GROUP BY c.id, c.nombre, c.precio
        ORDER BY c.nombre
    """;

        ArrayList<Object[]> reporte = new ArrayList<>();

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nombreCurso = rs.getString("nombre");
                int inscriptos = rs.getInt("inscriptos");
                double precio = rs.getDouble("precio");
                double recaudacion = rs.getDouble("recaudacion");

                reporte.add(new Object[]{nombreCurso, inscriptos, precio, recaudacion});
            }

        } catch (SQLException e) {
            throw new DAOException("Error al obtener el reporte de cursos", e);
        }

        return reporte;
    }

}
