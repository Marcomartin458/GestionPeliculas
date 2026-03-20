package dam.code.persistency;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dam.code.dto.PeliculaDTO;
import dam.code.models.Pelicula;
import dam.code.repository.PeliculaRepository;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Gestor de peliculas en formato JSON.
 * Se encarga de guardar y cargar el historial de visualizaciones específico de cada usuario.
 *
 * @author Alumno- Marco Martin
 * @version 1.0
 */
public class JsonManager implements PeliculaRepository {

    private final File archivo;
    private Gson gson;

    /**
     * Constructor del gestor JSON.
     * Crea el directorio base si no existe y configura el adaptador Gson para leer fechas y claves complejas.
     *
     * @param path el nombre del archivo a crear o leer (el DNI del usuario)
     */
    public JsonManager(String path) {
        File directorio = new File("data/visualizaciones");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        this.archivo = new File(directorio,path + ".json");
        this.gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    /**
     * Lee el archivo JSON del usuario y recupera su historial de películas.
     *
     * @return un mapa con las películas (DTO) y su cantidad de visualizaciones. Si falla o no existe, devuelve un mapa vacío.
     */
    @Override
    public Map<PeliculaDTO, Integer> cargar() {
        try (FileReader reader = new FileReader(archivo)) {
            Type mapType = new TypeToken<Map<PeliculaDTO, Integer>>() {
            }.getType();

            Map<PeliculaDTO, Integer> map = gson.fromJson(reader, mapType);

            return map != null ? map : new LinkedHashMap<>();
        } catch (IOException e) {
            return new LinkedHashMap<>();
        }
    }

    /**
     * Escribe el mapa actual de visualizaciones en el archivo JSON del usuario.
     *
     * @param visualizaciones el mapa actualizado a guardar en disco
     */
    @Override
    public void guardar(Map<PeliculaDTO, Integer> visualizaciones) {
        try(Writer writer=new FileWriter(archivo)){
            gson.toJson(visualizaciones, writer);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}