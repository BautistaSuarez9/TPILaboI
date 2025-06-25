package basededatos.servicios;

import basededatos.dao.DAOException;
import basededatos.dao.InscripcionDAO;
import basededatos.entidad.Alumno;
import basededatos.entidad.Curso;
import basededatos.entidad.Inscripcion;

import java.util.ArrayList;

public class InscripcionService {
    private final InscripcionDAO dao;

    public InscripcionService() throws ServiceException {
        try {
            this.dao = new InscripcionDAO();
        } catch (DAOException e) {
            throw new ServiceException("Error al inicializar InscripcionDAO", e);
        }
    }

    public void guardarInscripcion(Inscripcion inscripcion) throws ServiceException {
        if (inscripcion.getEstado() == null || inscripcion.getEstado().isBlank()) {
            inscripcion.setEstado("pendiente");
        }

        try {
            dao.guardar(inscripcion);
        } catch (DAOException e) {
            throw new ServiceException("No se pudo guardar la inscripción", e);
        }
    }

    public void actualizarNotaYDeterminarEstado(Alumno alumno, Curso curso, int nota) throws ServiceException {
        try {
            String estado = (nota >= curso.getNotaAprobacion()) ? "aprobado" : "reprobado";
            dao.actualizarNotaEstado(alumno.getId(), curso.getId(), nota, estado);
        } catch (DAOException e) {
            throw new ServiceException("Error al actualizar nota", e);
        }
    }

    public ArrayList<Inscripcion> obtenerTodas() throws ServiceException {
        try {
            return dao.buscarTodos();
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener inscripciones", e);
        }
    }

    public int contarInscripcionesPendientes(Alumno alumno) throws ServiceException {
        try {
            int contador = 0;
            for (Inscripcion i : dao.buscarTodos()) {
                if (i.getAlumno().getId() == alumno.getId() && "pendiente".equalsIgnoreCase(i.getEstado())) {
                    contador++;
                }
            }
            return contador;
        } catch (DAOException e) {
            throw new ServiceException("Error al contar inscripciones activas", e);
        }
    }

    public boolean puedeInscribirse(Alumno alumno) throws ServiceException {
        return contarInscripcionesPendientes(alumno) < alumno.getLimiteCursos();
    }
}
