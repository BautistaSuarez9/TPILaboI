package basededatos.entidad;

import java.util.ArrayList;
import java.util.List;

public class Alumno extends Persona {
    private int limiteCursos;
    private List<Inscripcion> inscripciones;

    public Alumno() {
    }

    public Alumno(int id, String nombre, String email, int limiteCursos) {
        super(id, nombre, email);
        this.limiteCursos = limiteCursos;
        this.inscripciones = new ArrayList<>();
    }

    public boolean puedeInscribirse() {
        return inscripciones.stream()
                .filter(i -> "pendiente".equalsIgnoreCase(i.getEstado()))
                .count() < limiteCursos;
    }

    public void agregarInscripcion(Inscripcion inscripcion) {
        this.inscripciones.add(inscripcion);
    }

    public void removerInscripcion(Inscripcion inscripcion) {
        this.inscripciones.remove(inscripcion);
    }

    public int getLimiteCursos() { return limiteCursos; }
    public void setLimiteCursos(int limiteCursos) { this.limiteCursos = limiteCursos; }

    public List<Inscripcion> getInscripciones() { return inscripciones; }
    public void setInscripciones(List<Inscripcion> inscripciones) {
        if (inscripciones != null) {
            this.inscripciones = inscripciones;
        }
    }

    @Override
    public String toString() {
        return getId() + " - " + getNombre();
    }
}
