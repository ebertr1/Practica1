package models;

public class Sintetica {
    private int idSintetica;
    private String nombre;
    private String direccion;
    private String telefono;
    private Double latitud = 0.0;  // Corrected
    private Double longitud = 0.0; // Corrected
    private String horario;

    public Sintetica() {
    }

    // Constructor
    public Sintetica(int idSintetica, String nombre, String direccion, String telefono, Double latitud, Double longitud, String horario) {
        this.idSintetica = idSintetica;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
        this.horario = horario;
    }

    public int getIdSintetica() {
        return idSintetica;
    }

    public void setIdSintetica(int idSintetica) {
        this.idSintetica = idSintetica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {  // Fixed parameter type
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Sintetica [direccion=" + direccion + ", horario=" + horario + ", idSintetica=" + idSintetica
                + ", latitud=" + latitud + ", longitud=" + longitud + ", nombre=" + nombre + ", telefono=" + telefono
                + "]";
    }
}
