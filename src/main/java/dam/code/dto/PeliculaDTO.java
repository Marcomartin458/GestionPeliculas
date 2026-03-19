package dam.code.dto;

import dam.code.models.Pelicula;

import java.time.LocalDate;

public class PeliculaDTO {
    private String id;
    private String nombre;
    private String director;
    private Integer duracion;
    private LocalDate fechaPublicacion;

    public PeliculaDTO(String id, String nombre, String director, Integer duracion, LocalDate fechaPublicacion) {
        this.id = id;
        this.nombre = nombre;
        this.director = director;
        this.duracion = duracion;
        this.fechaPublicacion = fechaPublicacion;
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

    public Integer getDuracion() {
        return duracion;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        PeliculaDTO p = (PeliculaDTO) obj;
        return this.id != null ? this.id.equals(p.id) : p.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
