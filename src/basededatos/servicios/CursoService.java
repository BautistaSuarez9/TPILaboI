package basededatos.servicios;

import basededatos.dao.CursoDAO;
import basededatos.dao.DAOException;
import basededatos.dao.InscripcionDAO;
import basededatos.entidad.Curso;
import basededatos.entidad.Inscripcion;

import java.util.ArrayList;

public class CursoService {
    private final CursoDAO dao;

    public CursoService() throws ServiceException {
        try {
            this.dao = new CursoDAO();
        } catch (DAOException e) {
            throw new ServiceException("Error al inicializar CursoDAO", e);
        }
    }

    public void guardarCurso(Curso curso) throws ServiceException {
        validarCurso(curso);
        try {
            dao.guardar(curso);
        } catch (DAOException e) {
            throw new ServiceException("Error al guardar curso", e);
        }
    }

    public void modificarCurso(Curso curso) throws ServiceException {
        validarCurso(curso);
        try {
            dao.modificar(curso);
        } catch (DAOException e) {
            throw new ServiceException("Error al modificar curso", e);
        }
    }

    public void eliminarCurso(int id) throws ServiceException {
        try {
            dao.eliminar(id);
        } catch (DAOException e) {
            throw new ServiceException("Error al eliminar curso", e);
        }
    }

    public Curso buscarPorId(int id) throws ServiceException {
        try {
            return dao.buscar(id);
        } catch (DAOException e) {
            throw new ServiceException("Error al buscar curso", e);
        }
    }

    public ArrayList<Curso> obtenerTodos() throws ServiceException {
        try {
            return dao.buscarTodos();
        } catch (DAOException e) {
            throw new ServiceException("Error al obtener cursos", e);
        }
    }

    public void inscribirAlumno(int idAlumno, int idCurso) throws ServiceException {
        try {
            Curso curso = dao.buscar(idCurso);
            if (curso == null) {
                throw new ServiceException("El curso no existe.");
            }

            int inscriptos = dao.contarInscriptos(idCurso);
            if (inscriptos >= curso.getCupoMaximo()) {
                throw new ServiceException("No hay cupos disponibles en este curso.");
            }

            InscripcionDAO inscDao = new InscripcionDAO();
            ArrayList<Inscripcion> inscripciones = inscDao.buscarTodos();

            for (Inscripcion i : inscripciones) {
                if (i.getAlumnoId() == idAlumno && i.getCursoId() == idCurso) {
                    throw new ServiceException("Ya está inscrito en este curso.");
                }
            }

            long activas = inscripciones.stream()
                    .filter(i -> i.getAlumnoId() == idAlumno && "pendiente".equalsIgnoreCase(i.getEstado()))
                    .count();

            if (activas >= 3) {
                throw new ServiceException("Ya está inscrito en 3 cursos activos.");
            }

            inscDao.inscribir(idAlumno, idCurso);

        } catch (DAOException e) {
            throw new ServiceException("Error al inscribir alumno", e);
        }
    }


    public void validarCurso(Curso curso) throws ServiceException {
        if (curso.getNombre() == null || curso.getNombre().trim().isEmpty()) {
            throw new ServiceException("El nombre del curso no puede estar vacío.");
        }
        if (curso.getPrecio() < 0) {
            throw new ServiceException("El precio debe ser un número positivo.");
        }
        if (curso.getCupoMaximo() <= 0) {
            throw new ServiceException("El cupo debe ser mayor a 0.");
        }
        if (curso.getNotaAprobacion() < 1 || curso.getNotaAprobacion() > 10) {
            throw new ServiceException("La nota de aprobación debe estar entre 1 y 10.");
        }
    }
}
