package basededatos.servicios;

import basededatos.dao.DAOException;
import basededatos.dao.ProfesorDAO;
import basededatos.entidad.Profesor;

import java.util.ArrayList;

public class ProfesorService {
    private final ProfesorDAO dao;

    public ProfesorService() throws ServiceException {
        try {
            this.dao = new ProfesorDAO();
        } catch (DAOException e) {
            throw new ServiceException("Error al inicializar ProfesorDAO", e);
        }
    }

    private void validar(Profesor profesor) throws ServiceException {
        if (profesor.getNombre() == null || profesor.getNombre().trim().isEmpty()) {
            throw new ServiceException("El nombre del profesor no puede estar vacío.");
        }
        if (profesor.getEmail() == null || !profesor.getEmail().contains("@")) {
            throw new ServiceException("El email del profesor no es válido.");
        }
    }

    public void guardar(Profesor profesor) throws ServiceException {
        validar(profesor);
        try {
            dao.guardar(profesor);
        } catch (DAOException e) {
            throw new ServiceException("Error al guardar el profesor", e);
        }
    }

    public void modificar(Profesor profesor) throws ServiceException {
        validar(profesor);
        try {
            dao.modificar(profesor);
        } catch (DAOException e) {
            throw new ServiceException("Error al modificar el profesor", e);
        }
    }

    public void eliminar(int id) throws ServiceException {
        try {
            dao.eliminar(id);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar el profesor", e);
        }
    }

    public Profesor buscarPorId(int id) throws ServiceException {
        try {
            return dao.buscar(id);
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar el profesor", e);
        }
    }

    public ArrayList<Profesor> obtenerTodos() throws ServiceException {
        try {
            return dao.buscarTodos();
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener la lista de profesores", e);
        }
    }

    public void guardarProfesor(Profesor profesor) throws ServiceException {
        try {
            dao.guardar(profesor);
        } catch (DAOException e) {
            throw new ServiceException("Error al guardar profesor", e);
        }
    }

}
