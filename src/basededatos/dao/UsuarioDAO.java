package basededatos.dao;

import basededatos.entidad.Usuario;
import basededatos.conexion.Conexion;

import java.sql.*;
import java.util.ArrayList;

public class UsuarioDAO implements IDAO<Usuario> {

    public UsuarioDAO() throws DAOException {
        crearTablaSiNoExiste();
    }

    private void crearTablaSiNoExiste() throws DAOException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "usuario VARCHAR(50) NOT NULL UNIQUE," +
                "contrasena VARCHAR(50) NOT NULL," +
                "rol VARCHAR(20) NOT NULL," +
                "alumno_id INT)";

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
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol"),
                        rs.getObject("alumno_id") != null ? rs.getInt("alumno_id") : null
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar usuario", e);
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

            if (usuario.getAlumnoId() != null) {
                stmt.setInt(4, usuario.getAlumnoId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al guardar usuario", e);
        }
    }

    @Override
    public void modificar(Usuario usuario) throws DAOException {
        throw new UnsupportedOperationException("No implementado.");
        /*String sql = "UPDATE usuarios SET contrasena = ?, rol = ?, alumno_id = ? WHERE usuario = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getContrasena());
            stmt.setString(2, usuario.getRol());

            if (usuario.getAlumnoId() != null) {
                stmt.setInt(3, usuario.getAlumnoId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }

            stmt.setString(4, usuario.getUsuario());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Error al modificar usuario", e);
        }*/
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

    @Override
    public Usuario buscar(int id) throws DAOException {
        throw new UnsupportedOperationException("No implementado.");
        /*String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol"),
                        rs.getObject("alumno_id") != null ? rs.getInt("alumno_id") : null
                );
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar usuario por ID", e);
        }*/
    }

    @Override
    public ArrayList<Usuario> buscarTodos() throws DAOException {
        throw new UnsupportedOperationException("No implementado.");
        /*ArrayList<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("usuario"),
                        rs.getString("contrasena"),
                        rs.getString("rol"),
                        rs.getObject("alumno_id") != null ? rs.getInt("alumno_id") : null
                );
                lista.add(usuario);
            }

        } catch (SQLException e) {
            throw new DAOException("Error al buscar todos los usuarios", e);
        }

        return lista;*/
    }
}
