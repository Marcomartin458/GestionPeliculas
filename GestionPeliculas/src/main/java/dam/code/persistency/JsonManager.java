package dam.code.persistency;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dam.code.dto.PeliculaDTO;
import dam.code.models.Pelicula;
import dam.code.repository.PeliculaRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonManager implements PeliculaRepository {

    private static final String FILE_PATH= "peliculas.json";
    private static final Gson GSON = new Gson();


    @Override
    public Map<PeliculaDTO, Integer> cargar() {
        return Map.of();
    }

    @Override
    public void guardar(Map<PeliculaDTO, Integer> peliculas) {

    }
}
