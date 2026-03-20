package dam.code.repository;

import dam.code.exceptions.PersonaExcepcion;
import dam.code.models.Persona;

import java.util.Map;

/**
 * Interfaz que define las operaciones de persistencia para las personas y sus contraseñas.
 *
 * @author Alumno- Marco Martin
 * @version 1.0
 */
public interface PersonaRepository {
    /**
     * Guarda el historial actual de personas y sus contraseñas.
     *
     * @param map el mapa con los datos a guardar
     */
    void guardar(Map<Persona, String> map);
    /**
     * Carga el historial de personas y sus contraseñas.
     *
     * @return un mapa con los datos cargados, o un mapa vacío si no hay datos
     */
    Map<Persona, String> cargar();




}
