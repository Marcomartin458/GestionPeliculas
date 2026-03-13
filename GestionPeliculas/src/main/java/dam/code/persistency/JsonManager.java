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

public class JsonManager implements PeliculaRepository {

    private final File archivo;
    private Gson gson;

    public JsonManager(String path) {
        File directorio = new File("data/visualizaciones");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        this.archivo = new File(path + ".json");
        this.gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

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

    @Override
    public void guardar(Map<PeliculaDTO, Integer> visualizaciones) {
        try(Writer writer=new FileWriter(archivo)){
            gson.toJson(visualizaciones, writer);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
