package basededatos.entidad;

public class Usuario {
    private int id;
    private String usuario;
    private String contrasena;
    private String rol;
    private Integer alumnoId; // puede ser null

    public Usuario(int id, String usuario, String contrasena, String rol, Integer alumnoId) {
        this.id = id;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.alumnoId = alumnoId;
    }

    public Usuario(String usuario, String contrasena, String rol, Integer alumnoId) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
        this.alumnoId = alumnoId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Integer getAlumnoId() { return alumnoId; }
    public void setAlumnoId(Integer alumnoId) { this.alumnoId = alumnoId; }
}
