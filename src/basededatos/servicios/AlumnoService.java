package basededatos.servicios;

import basededatos.dao.AlumnoDAO;
import basededatos.dao.DAOException;
import basededatos.entidad.Alumno;

import java.util.ArrayList;

public class AlumnoService {
    private final AlumnoDAO dao;

    public AlumnoService() {
        this.dao = new AlumnoDAO();
    }

    private void validarAlumno(Alumno alumno) throws ServiceException {
        if (alumno.getNombre() == null || alumno.getNombre().trim().isEmpty()) {
            throw new ServiceException("El nombre no puede estar vacío.");
        }
        if (alumno.getEmail() == null || !alumno.getEmail().contains("@")) {
            throw new ServiceException("El email no es válido.");
        }
        if (alumno.getLimiteCursos() <= 0) {
            throw new ServiceException("El límite de cursos debe ser mayor a cero.");
        }
    }



    public Alumno buscarPorId(int id) throws ServiceException {
        try {
            return dao.buscar(id);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo buscar el alumno con ID " + id, e);
        }
    }

    public ArrayList<Alumno> obtenerTodos() throws ServiceException {
        try {
            return dao.buscarTodos();
        } catch (DAOException e) {
            throw new ServiceException("No se pudo obtener la lista de alumnos", e);
        }
    }

    public void guardarAlumno(Alumno alumno) throws ServiceException {
        validarAlumno(alumno);
        try {
            dao.guardar(alumno);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo guardar el alumno", e);
        }
    }

    public void modificarAlumno(Alumno alumno) throws ServiceException {
        validarAlumno(alumno);
        try {
            dao.modificar(alumno);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo modificar el alumno", e);
        }
    }

    public void eliminarAlumno(int id) throws ServiceException {
        try {
            dao.eliminar(id);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo eliminar el alumno con ID " + id, e);
        }
    }
}
