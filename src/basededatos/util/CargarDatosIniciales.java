package basededatos.util;

import basededatos.entidad.Usuario;
import basededatos.servicios.UsuarioService;
import basededatos.servicios.ServiceException;

public class CargarDatosIniciales {
    public static void main(String[] args) {
        try {
            UsuarioService servicio = new UsuarioService();

            Usuario admin = new Usuario("admin", "admin123", "admin", null);
            Usuario alumno = new Usuario("alumno", "alumno123", "alumno", null);
            Usuario profesor = new Usuario("profesor", "profesor123", "profesor", null);

            servicio.registrarUsuario(admin);
            servicio.registrarUsuario(alumno);
            servicio.registrarUsuario(profesor);

            System.out.println("Usuarios de prueba creados correctamente.");

        } catch (ServiceException e) {
            System.err.println("Error al insertar usuarios: " + e.getMessage());
        }
    }
}
