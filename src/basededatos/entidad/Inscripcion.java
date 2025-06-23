package basededatos.entidad;

public class Inscripcion {
    private int id;
    private int alumnoId;
    private int cursoId;
    private Double notaFinal;
    private String estado;

    public Inscripcion() {}

    public Inscripcion(int alumnoId, int cursoId, Double notaFinal, String estado) {
        this.alumnoId = alumnoId;
        this.cursoId = cursoId;
        this.notaFinal = notaFinal;
        this.estado = estado;
    }

    public Inscripcion(int id, int alumnoId, int cursoId, Double notaFinal, String estado) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.cursoId = cursoId;
        this.notaFinal = notaFinal;
        this.estado = estado;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public int getCursoId() {
        return cursoId;
    }

    public void setCursoId(int cursoId) {
        this.cursoId = cursoId;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
