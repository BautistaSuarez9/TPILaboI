package basededatos.servicios;

import basededatos.dao.DAOException;
import basededatos.dao.UsuarioDAO;
import basededatos.entidad.Usuario;

public class UsuarioService {
    private final UsuarioDAO dao;

    public UsuarioService() throws ServiceException {
        try {
            this.dao = new UsuarioDAO();
        } catch (DAOException e) {
            throw new ServiceException("Error al inicializar UsuarioDAO", e);
        }
    }


    public Usuario login(String usuario, String contrasena) throws ServiceException {
        if (usuario == null || usuario.trim().isEmpty() || contrasena == null || contrasena.trim().isEmpty()) {
            throw new ServiceException("Debe ingresar usuario y contraseña.");
        }

        try {
            Usuario u = dao.buscarPorCredenciales(usuario.trim(), contrasena.trim());

            if (u == null) {
                throw new ServiceException("Usuario o contraseña incorrectos.");
            }

            return u;
        } catch (DAOException e) {
            throw new ServiceException("Error al intentar iniciar sesión", e);
        }
    }

    public void registrarUsuario(Usuario u) throws ServiceException {
        try {
            dao.guardar(u);
        } catch (DAOException e) {
            throw new ServiceException("Error al registrar usuario", e);
        }
    }
}
