package basededatos;

import basededatos.entidad.Alumno;
import basededatos.entidad.Curso;
import basededatos.entidad.Profesor;
import basededatos.servicios.AlumnoService;
import basededatos.servicios.CursoService;
import basededatos.servicios.ProfesorService;

public class TestModelo {
    public static void main(String[] args) throws Exception {
        ProfesorService ps = new ProfesorService();
        Profesor profe = new Profesor(0, "Carlos", "carlos@uni.edu");
        ps.guardarProfesor(profe);

        CursoService cs = new CursoService();
        Curso curso = new Curso(1, "Java Avanzado", 15000, 25, 6, profe);

        cs.guardarCurso(curso);

        AlumnoService as = new AlumnoService();
        Alumno alumno = new Alumno(0, "Lucía", "lucia@mail.com", 3);
        as.guardarAlumno(alumno);

        cs.inscribirAlumno(alumno.getId(), curso.getId());

        System.out.println("Todo correcto ✔️");
    }
}
