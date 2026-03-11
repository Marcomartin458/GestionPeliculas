package dam.code.dto;

import java.time.LocalDate;

public class PeliculaDTO {
    private String id;
    private String nombre;
    private String director;
    private double duracion;
    private String fechaPublicacion;

    public PeliculaDTO(String id, String nombre, String director, double duracion, LocalDate fechaPublicacion) {
        this.id = id;
        this.nombre = nombre;
        this.director = director;
        this.duracion = duracion;
        this.fechaPublicacion = fechaPublicacion.toString();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDirector() {
        return director;
    }

    public double getDuracion() {
        return duracion;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }
}
