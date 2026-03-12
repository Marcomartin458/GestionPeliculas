package dam.code.service;

import dam.code.dto.PeliculaDTO;
import dam.code.models.Pelicula;
import dam.code.repository.PeliculaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Map;

public class PeliculaService {
    private final ObservableList<Pelicula> peliculas;
    private final PeliculaRepository repository;

    public PeliculaService(PeliculaRepository repository) {
        this.repository = repository;
        peliculas = FXCollections.observableArrayList(repository.cargar());
    }

    public ObservableList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void agregarPelicula(Pelicula pelicula) {

    }


    public static void guardar(Map<PeliculaDTO, Integer> peliculas) {
        ObservableList<Pelicula> dtoList;
        for (Map.Entry<PeliculaDTO, Integer> pelicula : peliculas.entrySet()) {
            PeliculaDTO peliDto=pelicula.getKey();
        }
        dtoList.add(peliculas.toDTO);
    }
}
