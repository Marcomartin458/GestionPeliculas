package dam.code.service;

import dam.code.dto.PeliculaDTO;
import dam.code.exceptions.PeliculaExcepcion;
import dam.code.models.Pelicula;
import dam.code.repository.PeliculaRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PeliculaService {
    private final ObservableList<Pelicula> peliculas;
    private final PeliculaRepository repository;
    private final Map<PeliculaDTO, Integer> visualizaciones;

    public PeliculaService(PeliculaRepository repository) {
        this.repository = repository;
        visualizaciones = repository.cargar();
        peliculas = FXCollections.observableArrayList(cargar());
    }

    public ObservableList<Pelicula> getPeliculas() {
        return peliculas;
    }
    private List<Pelicula> cargar() {
        List<Pelicula> listaPeliculas=new ArrayList<>();

        if(!visualizaciones.isEmpty()){
            for(Map.Entry<PeliculaDTO,Integer> pelicula:visualizaciones.entrySet()){
                listaPeliculas.add(Pelicula.fromDTO(pelicula.getKey()));
            }
        }
        return listaPeliculas;
    }
    private void guardar(){
        repository.guardar(visualizaciones);
    }

    public void agregarPelicula(Pelicula pelicula) throws PeliculaExcepcion {
        validarPelicula(pelicula);
        peliculas.add(pelicula);
        visualizaciones.put(pelicula.toDTO(), 0);
        guardar();
    }

    private void validarPelicula(Pelicula pelicula) throws PeliculaExcepcion {
        String regex="^[a-zA-Z]{3}[0-9]{2}$";
        if(!pelicula.getId().matches(regex) || pelicula.getId().isEmpty()){
            throw new PeliculaExcepcion("El id de la pelicula no es valido, debe tener 3 letras y 2 numeros. Ejemplo válido: ABC12");
        }
        if (pelicula.getNombre()== null || pelicula.getNombre().isEmpty()){
            throw new PeliculaExcepcion("El nombre es obligatorio");
        }
        if (pelicula.getDirector()== null || pelicula.getDirector().isEmpty()){
            throw new PeliculaExcepcion("El director es obligatorio");
        }
        if (pelicula.getDuracion()<=0 || pelicula.getDuracion()==null){
            throw new PeliculaExcepcion("Debes introducir obligatoriamente la duración y debe ser superior a 0.");
        }
        if (pelicula.getFechaPublicacion()==null || pelicula.getFechaPublicacion().isAfter(LocalDate.now())|| pelicula.getFechaPublicacion().isBefore(LocalDate.of(1800, 1,1))){
            throw new PeliculaExcepcion("La fecha de publicación es obligatoria y debe ser anterior a la actual");
        }
        boolean existe= peliculas.stream()
                .anyMatch(p->p.getId().equalsIgnoreCase(pelicula.getId()));
        if (existe){
            throw new PeliculaExcepcion("La película introducida ya existe en el sistema, puedes visualizarla sin tener que añadirla!");
        }

    }
    public void registrarVisualizacion(Pelicula pelicula) {
        if (pelicula != null) {
            PeliculaDTO dto = pelicula.toDTO();
            int visualizacionesActuales = 0;

            if (visualizaciones.containsKey(dto)) {
                visualizacionesActuales = visualizaciones.get(dto);
            }
            visualizaciones.put(dto, visualizacionesActuales + 1);
            guardar();
        }
    }
    public void eliminarPelicula(Pelicula pelicula) throws PeliculaExcepcion {
        if (pelicula == null){
            throw new PeliculaExcepcion("Debes seleccionar una película");
        }
        peliculas.remove(pelicula);
        visualizaciones.remove(pelicula.toDTO());
        guardar();
    }

    public void actualizarNombre (Pelicula pelicula, String nuevoNombre) throws PeliculaExcepcion {
        if (nuevoNombre==null || nuevoNombre.isEmpty()){
            throw new PeliculaExcepcion("El nombre es obligatorio");
        }
        PeliculaDTO viejoDto=pelicula.toDTO();
        int visualizacion =visualizaciones.get(viejoDto);
        visualizaciones.remove(viejoDto);
        pelicula.setNombre(nuevoNombre);
        visualizaciones.put(pelicula.toDTO(), visualizacion);
        guardar();
    }

    public void actualizarFechaPublicacion(Pelicula pelicula, LocalDate nuevoFechaPublicacion) throws PeliculaExcepcion {
        if (nuevoFechaPublicacion.isAfter(LocalDate.now())) {
            throw new PeliculaExcepcion("La fecha publicacion no puede ser superior a la de hoy");
        }
        PeliculaDTO viejoDto=pelicula.toDTO();
        int visualizacion =visualizaciones.get(viejoDto);
        visualizaciones.remove(viejoDto);
        pelicula.setFechaPublicacion(nuevoFechaPublicacion);
        visualizaciones.put(pelicula.toDTO(), visualizacion);
        guardar();
    }

}
