package basededatos.entidad;

import java.util.ArrayList;

public class Alumno extends Persona {
    private int limiteCursos;
    private ArrayList<Inscripcion> cursos;

    public Alumno() {
        super();
    }

    public Alumno(int id, String nombre, String email, int limiteCursos) {
        super(id, nombre, email);
        this.limiteCursos = limiteCursos;
    }

    public int getLimiteCursos() {
        return limiteCursos;
    }

    public void setLimiteCursos(int limiteCursos) {
        this.limiteCursos = limiteCursos;
    }

    @Override
    public String toString() {
        return getId() + " - " + getNombre() + super.toString();
    }
}
