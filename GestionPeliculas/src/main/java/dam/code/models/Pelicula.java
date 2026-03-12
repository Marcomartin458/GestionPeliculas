package dam.code.models;

import dam.code.dto.PeliculaDTO;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Pelicula {
    private StringProperty id;
    private StringProperty nombre;
    private StringProperty director;
    private DoubleProperty duracion;
    private StringProperty fechaPublicacion;

    public Pelicula(String id, String nombre, String director, Double duracion, LocalDate fechaPublicacion) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.director = new SimpleStringProperty(director);
        this.duracion = new SimpleDoubleProperty(duracion);
        this.fechaPublicacion = new SimpleStringProperty(fechaPublicacion.toString());
    }
    //Getters Normales :)
    public String getId() {
        return this.id.get();
    }
    public String getNombre() {
        return this.nombre.get();
    }
    public String getDirector() {
        return this.director.get();
    }
    public Double getDuracion() {
        return this.duracion.get();
    }
    public LocalDate getFechaPublicacion() {
        return LocalDate.parse(this.fechaPublicacion.get(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    //Getters Properties
    public StringProperty idProperty() {
        return this.id;
    }
    public StringProperty nombreProperty() {
        return this.nombre;
    }
    public StringProperty directorProperty() {
        return this.director;
    }
    public DoubleProperty duracionProperty() {
        return this.duracion;
    }
    public StringProperty fechaPublicacionProperty() {
        return this.fechaPublicacion;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion.set(fechaPublicacion);
    }


    public PeliculaDTO toDTO() {
        return new PeliculaDTO(
                getId(),
                getNombre(),
                getDirector(),
                getDuracion(),
                getFechaPublicacion()
        );
    }

    public static Pelicula fromDTO(PeliculaDTO dto) {
        return new Pelicula(
                dto.getId(),
                dto.getNombre(),
                dto.getDirector(),
                dto.getDuracion(),
                dto.getFechaPublicacion()
        );
    }

    
}
