package basededatos.entidad;

import java.util.ArrayList;
import java.util.List;

public class Profesor extends Persona {
    private List<Curso> cursosAsignados;

    public Profesor() {
    }

    public Profesor(int id, String nombre, String email) {
        super(id, nombre, email);
        this.cursosAsignados = new ArrayList<>();
    }

    public List<Curso> getCursosAsignados() {
        return cursosAsignados;
    }

    public void setCursosAsignados(List<Curso> cursosAsignados) {
        this.cursosAsignados = cursosAsignados;
    }

    public void asignarCurso(Curso curso) {
        cursosAsignados.add(curso);
    }

    public void quitarCurso(Curso curso) {
        cursosAsignados.remove(curso);
    }

    @Override
    public String toString() {
        return getNombre() + " (" + getEmail() + ")";
    }
}
