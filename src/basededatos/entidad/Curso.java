package basededatos.entidad;

public class Curso {
    private int id;
    private String nombre;
    private double precio;
    private int cupoMaximo;
    private double notaAprobacion;

    public Curso() {}

    public Curso(int id, String nombre, double precio, int cupoMaximo, double notaAprobacion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cupoMaximo = cupoMaximo;
        this.notaAprobacion = notaAprobacion;
    }

    public Curso(String nombre, double precio, int cupoMaximo, double notaAprobacion) {
        this.nombre = nombre;
        this.precio = precio;
        this.cupoMaximo = cupoMaximo;
        this.notaAprobacion = notaAprobacion;
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
}
