package dam.code.models;

import dam.code.dto.PeliculaDTO;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Clase Pelicula
 *
 * @author Alumno- Marco Martin
 * @version 1.0
 */

public class Pelicula {
    private StringProperty id;
    private StringProperty nombre;
    private StringProperty director;
    private IntegerProperty duracion;
    private ObjectProperty<LocalDate> fechaPublicacion;
    /**
     * Constructor principal de la clase Pelicula
     *
     * @param id
     * @param nombre
     * @param director
     * @param duracion
     * @param fechaPublicacion
     */

    public Pelicula(String id, String nombre, String director, int duracion, LocalDate fechaPublicacion) {
        this.id = new SimpleStringProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.director = new SimpleStringProperty(director);
        this.duracion = new SimpleIntegerProperty(duracion);
        this.fechaPublicacion = new SimpleObjectProperty<>(fechaPublicacion);
    }
    //Getters Normales :)

    /**
     * Getter del id de la película
     *
     * @return el id de la película
     */
    public String getId() {
        return this.id.get();
    }
    /**
     * Getter del nombre de la película
     *
     * @return el nombre de la película
     */
    public String getNombre() {
        return this.nombre.get();
    }
    /**
     * Getter del director de la película
     *
     * @return el director de la película
     */
    public String getDirector() {
        return this.director.get();
    }
    /**
     * Getter de la duracion de la película
     *
     * @return el duracion de la película
     */
    public Integer getDuracion() {
        return this.duracion.get();
    }
    /**
     * Getter de la fecha de la película
     *
     * @return la fecha de la película
     */
    public LocalDate getFechaPublicacion() {
        return fechaPublicacion.get();
    }

    //Getters Properties :)

    /**
     * Getter de la propiedad observable del id de la película
     *
     * @return StringProperty del id de la película
     */
    public StringProperty idProperty() {
        return this.id;
    }
    /**
     * Getter de la propiedad observable del nombre de la película
     *
     * @return StringProperty del nombre de la película
     */
    public StringProperty nombreProperty() {
        return this.nombre;
    }
    /**
     * Getter de la propiedad observable del director de la película
     *
     * @return StringProperty del director de la película
     */
    public StringProperty directorProperty() {
        return this.director;
    }
    /**
     * Getter de la propiedad observable de la duracion de la película
     *
     * @return StringProperty de la duracion de la película
     */
    public IntegerProperty duracionProperty() {
        return this.duracion;
    }
    /**
     * Getter de la propiedad observable de la duracion de la película
     *
     * @return ObjectProperty que envuelve la fecha de publicación.
     */
    public ObjectProperty<LocalDate> fechaPublicacionProperty() {
        return this.fechaPublicacion;
    }

    /**
     * Setter de la fecha de la película
     *
     * @param nombre nuevo titulo de la peli
     */
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }
    /**
     * Setter de la fecha de la película
     *
     * @param fechaPublicacion nueva fecha de la peli
     */
    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion.set(fechaPublicacion);
    }

// --- DTO ---

    /**
     * Convierte la  Pelicula actual en un data transfer object.
     *
     * @return Un nuevo objeto PeliculaDTO con los datos actuales.
     */
    public PeliculaDTO toDTO() {
        return new PeliculaDTO(
                getId(),
                getNombre(),
                getDirector(),
                getDuracion(),
                getFechaPublicacion()
        );
    }
    /**
     * Construye una nueva Pelicula a partir de un dto.
     *
     * @param dto La PeliculaDTO que contiene los datos en el formato del dto.
     * @return Una nueva Pelicula con sus Properties inicializadas.
     */

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
