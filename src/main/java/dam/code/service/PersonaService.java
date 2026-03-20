package dam.code.service;

import dam.code.dto.PeliculaDTO;
import dam.code.exceptions.PersonaExcepcion;
import dam.code.models.Persona;
import dam.code.persistency.UsuariosDAO;
import java.util.*;

/**
 * Servicio que gestiona el registro, validación y búsqueda de usuarios.
 *
 * @author Alumno- Marco Martin
 * @version 1.0
 */
public class PersonaService {

    private Map<Persona, String> registros;
    private final UsuariosDAO usuariosDAO;

    /**
     * Constructor del servicio.
     * Carga en memoria los usuarios registrados desde el archivo de datos.
     *
     * @param usuariosDAO el objeto encargado de la persistencia de usuarios
     */
    public PersonaService(UsuariosDAO usuariosDAO) {
        this.usuariosDAO = usuariosDAO;
        registros = usuariosDAO.cargar();
    }

    /**
     * Obtiene una copia del mapa de usuarios registrados.
     *
     * @return un mapa inmodificable con las personas y sus contraseñas
     */
    public Map<Persona, String> getRegistros() {
        return Map.copyOf(registros);
    }

    /**
     * Valida, añade un nuevo usuario al sistema y guarda los cambios.
     *
     * @param persona el objeto Persona a registrar
     * @param password la contraseña asociada al usuario
     * @throws PersonaExcepcion si el DNI no es válido o ya existe
     */
    public void addUsuario(Persona persona, String password) throws PersonaExcepcion {
        registroValido(persona.getDni());
        registros.put(persona, password);
        usuariosDAO.guardar(registros);
    }

    /**
     * Busca y devuelve un usuario basándose en su DNI.
     *
     * @param dni el DNI a buscar
     * @return el objeto Persona si lo encuentra, o null si no existe
     */
    public Persona getUsuario(String dni) {
        for (Persona persona : registros.keySet()) {
            if (persona.getDni().equals(dni)) {
                return persona;
            }
        }
        return null;
    }

    /**
     * Comprueba que el formato del DNI sea correcto y que no esté ya registrado.
     *
     * @param dni el DNI a validar
     * @throws PersonaExcepcion si el formato es incorrecto o el DNI está duplicado
     */
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