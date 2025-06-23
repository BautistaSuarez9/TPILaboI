package basededatos.servicios;

import basededatos.dao.AlumnoDAO;
import basededatos.dao.CursoDAO;
import basededatos.dao.DAOException;
import basededatos.dao.InscripcionDAO;
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



    public void actualizarNotaYDeterminarEstado(int idAlumno, int idCurso, int nota) throws ServiceException {
        try {
            Curso curso = new CursoDAO().buscar(idCurso); // o pasalo por parámetro si ya lo tenés
            String estado = (nota >= curso.getNotaAprobacion()) ? "aprobado" : "reprobado";
            dao.actualizarNotaEstado(idAlumno, idCurso, nota, estado);
        } catch (DAOException e) {
            throw new ServiceException("Error al actualizar nota", e);
        }
    }


    /*public void guardarInscripcion(Inscripcion inscripcion) throws ServiceException {
        if (inscripcion.getEstado() == null || inscripcion.getEstado().trim().isEmpty()) {
            inscripcion.setEstado("pendiente");
        }
        try {
            dao.guardar(inscripcion);
        } catch (DAOException e) {
            throw new ServiceException("Error al guardar inscripción", e);
        }
    }*/


    public ArrayList<Inscripcion> obtenerTodas() throws ServiceException {
        try {
            return dao.buscarTodos();
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener inscripciones", e);
        }
    }

    /*public int contarInscripcionesActivasDeAlumno(int alumnoId) throws ServiceException {
        try {
            int contador = 0;
            for (Inscripcion i : dao.buscarTodos()) {
                if (i.getAlumnoId() == alumnoId && "pendiente".equalsIgnoreCase(i.getEstado())) {
                    contador++;
                }
            }
            return contador;
        } catch (DAOException e) {
            throw new ServiceException("Error al contar inscripciones activas", e);
        }
    }

    public boolean puedeInscribirse(int alumnoId) throws ServiceException {
        try {
            int activas = contarInscripcionesActivasDeAlumno(alumnoId);

            AlumnoDAO alumnoDAO = new AlumnoDAO();
            int limite = alumnoDAO.buscar(alumnoId).getLimiteCursos();

            return activas < limite;

        } catch (DAOException e) {
            throw new ServiceException("Error al validar si puede inscribirse", e);
        }
    }*/

}
