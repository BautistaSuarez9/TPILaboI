package basededatos.entidad;

public class Usuario {
    private int id;
    private String usuario;
    private String contrasena;
    private String rol;
    private Alumno alumno;
    private Profesor profesor;

    public Usuario() {
    }

    public Usuario(int id,String usuario, String contrasena, String rol, Alumno alumno) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.alumno = alumno;
    }

    public Usuario(int id, String usuario, String contrasena, String rol, Alumno alumno, Profesor profesor) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.alumno = alumno;
        this.profesor = profesor;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }
}
