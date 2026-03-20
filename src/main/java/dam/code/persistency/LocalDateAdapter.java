package dam.code.persistency;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Adaptador personalizado para Gson que permite serializar y deserializar objetos LocalDate.
 *
 * @author Alumno- Marco Martin
 * @version 1.0
 */
public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    /**
     * Escribe un objeto LocalDate en formato String dentro del JSON.
     * Si la fecha es nula, guarda un valor nulo para evitar errores.
     *
     * @param jsonWriter el escritor JSON de Gson
     * @param localDate la fecha a serializar
     * @throws IOException si ocurre un error durante la escritura
     */
    @Override
    public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
        if(localDate == null){
            jsonWriter.nullValue();
        } else {
            jsonWriter.value(localDate.toString());
        }
    }

    /**
     * Lee una fecha en formato String desde un JSON y la convierte a LocalDate.
     *
     * @param jsonReader el lector JSON de Gson
     * @return el objeto LocalDate instanciado
     * @throws IOException si ocurre un error durante la lectura
     */
    @Override
    public LocalDate read(JsonReader jsonReader) throws IOException {
        String localDate = jsonReader.nextString();
        return LocalDate.parse(localDate);
    }
}