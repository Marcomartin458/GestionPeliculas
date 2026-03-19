package dam.code.service;

import dam.code.dto.PeliculaDTO;
import dam.code.exceptions.PersonaExcepcion;
import dam.code.models.Persona;
import dam.code.persistency.UsuariosDAO;
import java.util.*;

public class PersonaService {

    private static Map<Persona, String> registros;
    private final UsuariosDAO usuariosDAO;


    public PersonaService(UsuariosDAO usuariosDAO) {
        this.usuariosDAO=usuariosDAO;
        registros = usuariosDAO.cargar();
    }

    public Map<Persona, String> getRegistros() {
        return Map.copyOf(registros);
    }


    public void addUsuario(Persona persona, String password) throws PersonaExcepcion {
        registroValido(persona.getDni());
        registros.put(persona, password);
        usuariosDAO.guardar(registros);
    }


    public Persona getUsuario(String dni) {
        for (Persona persona : registros.keySet()) {
            if (persona.getDni().equals(dni)) {
                return persona;
            }
        }
        return null;
    }

    public void registroValido(String dni) throws PersonaExcepcion {
        String regex = "^[0-9]{8}[A-Z]{1}$";
        if (!dni.matches(regex)) {
            throw new PersonaExcepcion("DNI no válido. Debe tener 8 números y una letra. Ejemplo válido: 12345678A");
        }
        if (registros != null) {
            for (Persona personaExistente : registros.keySet()) {
                if (personaExistente.getDni().equals(dni)) {
                    throw new PersonaExcepcion("El DNI introducido ya existe en el sistema.");
                }
            }
        }
    }
}
