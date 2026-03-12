package dam.code.persistency;

import dam.code.models.Persona;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import dam.code.recursos.Utilidades;


public class UsuariosDAO {
    private static final String RUTA = System.getProperty("user.home") + "/Desktop/DAM/Simulacros/ProyectoGestionPeliculas/";
    private static final File FICHERO = new File(RUTA + "Registros.dat");

    public void guardar(Map<Persona, String> usuarios) {
        if (comprobarDirectorio(RUTA)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FICHERO))) {

                oos.writeObject(usuarios);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

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


    public static boolean comprobarDirectorio(String ruta) {
        if (Utilidades.existDirectory(ruta)) {
            return true;
        } else {
            return Utilidades.crearDirectorio(ruta);
        }
    }
}


