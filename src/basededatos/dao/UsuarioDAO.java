package basededatos.dao;

import basededatos.conexion.Conexion;
import basededatos.entidad.Alumno;
import basededatos.entidad.Usuario;

import java.sql.*;

public class UsuarioDAO implements IDAO<Usuario> {

    private final AlumnoDAO alumnoDAO;

    public UsuarioDAO() throws DAOException {
        crearTablaSiNoExiste();
        this.alumnoDAO = new AlumnoDAO();  // Podés inyectarlo si querés más testabilidad
    }

    private void crearTablaSiNoExiste() throws DAOException {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INT AUTO_INCREMENT PRIMARY KEY,
                usuario VARCHAR(50) NOT NULL UNIQUE,
                contrasena VARCHAR(50) NOT NULL,
                rol VARCHAR(20) NOT NULL,
                alumno_id INT
            )
        """;
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DAOException("Error al crear la tabla usuarios", e);
        }
    }

    public Usuario buscarPorCredenciales(String usuario, String contrasena) throws DAOException {
        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND contrasena = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Alumno alumno = null;
                Object rawAlumnoId = rs.getObject("alumno_id");
                if (rawAlumnoId != null) {
                    int alumnoId = rs.getInt("alumno_id");
                    alumno = alumnoDAO.buscar(alumnoId);
                }

                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol"),
                        alumno
                );
            }

            return null;

        } catch (SQLException e) {
            throw new DAOException("Error al buscar usuario por credenciales", e);
        }
    }

    @Override
    public void guardar(Usuario usuario) throws DAOException {
        String sql = "INSERT INTO usuarios (usuario, contrasena, rol, alumno_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getUsuario());
            stmt.setString(2, usuario.getContrasena());
            stmt.setString(3, usuario.getRol());

            if (usuario.getAlumno() != null && usuario.getAlumno().getId() > 0) {
                stmt.setInt(4, usuario.getAlumno().getId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al guardar usuario", e);
        }
    }

    @Override
    public void eliminar(int id) throws DAOException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error al eliminar usuario", e);
        }
    }

    // Métodos no utilizados
    @Override public void modificar(Usuario usuario) { throw new UnsupportedOperationException("No implementado."); }
    @Override public Usuario buscar(int id) { throw new UnsupportedOperationException("No implementado."); }
    @Override public java.util.ArrayList<Usuario> buscarTodos() { throw new UnsupportedOperationException("No implementado."); }
}
