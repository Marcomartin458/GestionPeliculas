package dam.code.repository;

import dam.code.dto.PeliculaDTO;
import java.util.Map;

/**
 * Interfaz que define las operaciones de persistencia para las películas y sus visualizaciones.
 *
 * @author Alumno- Marco Martin
 * @version 1.0
 */
public interface PeliculaRepository {

    /**
     * Carga el historial de películas y sus visualizaciones.
     *
     * @return un mapa con los datos cargados, o un mapa vacío si no hay datos
     */
    Map<PeliculaDTO, Integer> cargar();

    /**
     * Guarda el historial actual de películas y sus visualizaciones.
     *
     * @param visualizaciones el mapa con los datos a guardar
     */
    void guardar(Map<PeliculaDTO, Integer> visualizaciones);
}