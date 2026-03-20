package dam.code.persistency;

import dam.code.models.Persona;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import dam.code.recursos.Utilidades;
import dam.code.repository.PersonaRepository;

/**
 * Gestor de persistencia de usuarios mediante archivos .dat.
 * Se encarga de guardar y recuperar los registros de inicio de sesión.
 *
 * @author Alumno- Marco Martin
 * @version 1.0
 */
public class UsuariosDAO implements PersonaRepository {

    private static final String RUTA = System.getProperty("user.home") + "/Desktop/DAM/Simulacros/ProyectoGestionPeliculas/";
    private static final File FICHERO = new File(RUTA , "Registros.dat");

    /**
     * Guarda el mapa completo de usuarios y contraseñas en el archivo.
     * Si el directorio no existe, intenta crearlo antes de guardar.
     *
     * @param usuarios el mapa actualizado a guardar
     */
    public void guardar(Map<Persona, String> usuarios) {
        if (comprobarDirectorio()) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO))) {

                oos.writeObject(usuarios);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Carga el registro de usuarios desde el archivo.
     *
     * @return un mapa con los usuarios registrados, o un mapa vacío si falla la lectura o no existe
     */
    @SuppressWarnings("unchecked")
    public Map<Persona, String> cargar() {
        Map<Persona, String> usuarios = new LinkedHashMap<>();
        if (FICHERO.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FICHERO))) {
                usuarios = (Map<Persona, String>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        return usuarios;
    }

    /**
     * Verifica que el directorio de almacenamiento existe.
     * Si no existe, utiliza la clase Utilidades para generarlo.
     *
     * @return true si el directorio existe o se ha creado con éxito, false en caso contrario
     */
    public static boolean comprobarDirectorio() {
        if (Utilidades.existDirectory(RUTA)) {
            return true;
        } else {
            return Utilidades.crearDirectorio(RUTA);
        }
    }
}