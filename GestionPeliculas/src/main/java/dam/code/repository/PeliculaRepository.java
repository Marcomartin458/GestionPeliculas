package dam.code.repository;

import dam.code.dto.PeliculaDTO;
import dam.code.models.Pelicula;

import java.util.List;
import java.util.Map;

public interface PeliculaRepository {

    Map<PeliculaDTO,Integer> cargar();
    void guardar(Map<PeliculaDTO,Integer> peliculas);
}
