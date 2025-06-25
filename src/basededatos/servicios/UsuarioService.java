package basededatos.servicios;

import basededatos.dao.AlumnoDAO;
import basededatos.dao.DAOException;
import basededatos.dao.UsuarioDAO;
import basededatos.entidad.Alumno;
import basededatos.entidad.Usuario;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO;
    private final AlumnoDAO alumnoDAO;

    public UsuarioService() throws ServiceException {
        try {
            this.usuarioDAO = new UsuarioDAO();
            this.alumnoDAO = new AlumnoDAO();
        } catch (DAOException e) {
            throw new ServiceException("Error al inicializar DAOs", e);
        }
    }

    public Usuario login(String usuario, String contrasena) throws ServiceException {
        if (usuario == null || usuario.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            throw new ServiceException("Debe ingresar usuario y contraseña.");
        }

        try {
            Usuario u = usuarioDAO.buscarPorCredenciales(usuario.trim(), contrasena.trim());

            if (u == null) {
                throw new ServiceException("Usuario o contraseña incorrectos.");
            }

            if ("alumno".equalsIgnoreCase(u.getRol()) && u.getAlumno() != null && u.getAlumno().getId() > 0) {
                Alumno alumnoCompleto = alumnoDAO.buscar(u.getAlumno().getId());
                u.setAlumno(alumnoCompleto);
            }

            return u;

        } catch (DAOException e) {
            throw new ServiceException("Error al intentar iniciar sesión", e);
        }
    }

    public void registrarUsuario(Usuario u) throws ServiceException {
        try {
            usuarioDAO.guardar(u);
        } catch (DAOException e) {
            throw new ServiceException("Error al registrar usuario", e);
        }
    }
}
