package basededatos.entidad;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private int id;
    private String nombre;
    private double precio;
    private int cupoMaximo;
    private double notaAprobacion;
    private List<Inscripcion> inscripciones;
    private Profesor profesor;

    public Curso() {
        this.inscripciones = new ArrayList<>();
    }

    public Curso(int id, String nombre, double precio, int cupoMaximo, double notaAprobacion, Profesor profesor) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cupoMaximo = cupoMaximo;
        this.notaAprobacion = notaAprobacion;
        this.profesor = profesor;
        this.inscripciones = new ArrayList<>();
    }
    public Curso(String nombre, double precio, int cupoMaximo, double notaAprobacion, Profesor profesor) {
        this.nombre = nombre;
        this.precio = precio;
        this.cupoMaximo = cupoMaximo;
        this.notaAprobacion = notaAprobacion;
        this.profesor = profesor;
        this.inscripciones = new ArrayList<>();
    }


    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }


    public boolean tieneCupoDisponible() {
        return inscripciones.size() < cupoMaximo;
    }

    public void agregarInscripcion(Inscripcion inscripcion) {
        inscripciones.add(inscripcion);
    }

    public void removerInscripcion(Inscripcion inscripcion) {
        inscripciones.remove(inscripcion);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public double getNotaAprobacion() { return notaAprobacion; }
    public void setNotaAprobacion(double notaAprobacion) { this.notaAprobacion = notaAprobacion; }

    public List<Inscripcion> getInscripciones() { return inscripciones; }
    public void setInscripciones(List<Inscripcion> inscripciones) {
        if (inscripciones != null) {
            this.inscripciones = inscripciones;
        }
    }
}
