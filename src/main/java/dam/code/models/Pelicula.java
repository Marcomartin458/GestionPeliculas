package dam.code.models;

import dam.code.dto.PeliculaDTO;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Pelicula {
    private StringProperty id;
    private StringProperty nombre;
    private StringProperty director;
    private IntegerProperty duracion;
    private ObjectProperty<LocalDate> fechaPublicacion;

    public Pelicula(String id, String nombre, String director, int duracion, LocalDate fechaPublicacion) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.director = new SimpleStringProperty(director);
        this.duracion = new SimpleIntegerProperty(duracion);
        this.fechaPublicacion = new SimpleObjectProperty<>(fechaPublicacion);
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
    public Integer getDuracion() {
        return this.duracion.get();
    }
    public LocalDate getFechaPublicacion() {
        return fechaPublicacion.get();
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
    public IntegerProperty duracionProperty() {
        return this.duracion;
    }
    public ObjectProperty<LocalDate> fechaPublicacionProperty() {
        return this.fechaPublicacion;
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
    public void setFechaPublicacion(LocalDate fechaPublicacion) {
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
