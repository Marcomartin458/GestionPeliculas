package dam.code.repository;

import dam.code.exceptions.PersonaExcepcion;
import dam.code.models.Persona;

import java.util.Map;

public interface PersonaRepository {
    void guardar(Map<Persona, String> map);
    Map<Persona, String> cargar();




}
