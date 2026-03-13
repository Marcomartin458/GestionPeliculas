package dam.code.service;

import dam.code.dto.PeliculaDTO;
import dam.code.exceptions.PeliculaExcepcion;
import dam.code.models.Pelicula;
import dam.code.repository.PeliculaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeliculaService {
    private final ObservableList<Pelicula> peliculas;
    private final PeliculaRepository repository;
    private final Map<PeliculaDTO, Integer> visualizaciones;

    public PeliculaService(PeliculaRepository repository) {
        this.repository = repository;
        visualizaciones = repository.cargar();
        peliculas = FXCollections.observableArrayList(cargar());
    }

    public ObservableList<Pelicula> getPeliculas() {
        return peliculas;
    }
    private List<Pelicula> cargar() {
        List<Pelicula> listaPeliculas=new ArrayList<>();

        if(!visualizaciones.isEmpty()){
            for(Map.Entry<PeliculaDTO,Integer> e:visualizaciones.entrySet()){
                listaPeliculas.add(Pelicula.fromDTO(e.getKey()));
            }
        }
        return listaPeliculas;
    }
    private void guardar(){
        repository.guardar(visualizaciones);
    }

    public void agregarPelicula(Pelicula pelicula) throws PeliculaExcepcion {

    }

}
