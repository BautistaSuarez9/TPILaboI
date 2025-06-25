package basededatos.entidad;

public class Inscripcion {
    private int id;
    private Alumno alumno;
    private Curso curso;
    private Double notaFinal;
    private String estado;

    public Inscripcion() {
    }

    public Inscripcion(Alumno alumno, Curso curso) {
        this.alumno = alumno;
        this.curso = curso;
        this.estado = "pendiente";
    }
    public Inscripcion(Alumno alumno, Curso curso, Double notaFinal, String estado) {
        this.alumno = alumno;
        this.curso = curso;
        this.notaFinal = notaFinal;
        this.estado = estado;
    }


    public Inscripcion(int id, Alumno alumno, Curso curso, Double notaFinal, String estado) {
        this.id = id;
        this.alumno = alumno;
        this.curso = curso;
        this.notaFinal = notaFinal;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public Double getNotaFinal() { return notaFinal; }
    public void setNotaFinal(Double notaFinal) { this.notaFinal = notaFinal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    @Override
    public String toString() {
        return "Inscripción de " + alumno.getNombre() + " a " + curso.getNombre() +
                " - Estado: " + estado + ", Nota: " + (notaFinal != null ? notaFinal : "Sin nota");
    }
}
