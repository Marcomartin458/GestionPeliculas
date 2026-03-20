package dam.code.controller;

import dam.code.exceptions.PeliculaExcepcion;
import dam.code.models.Pelicula;
import dam.code.service.PeliculaService;
import dam.code.service.PersonaService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;

/**
 * Controlador para la vista principal de la cartelera de películas.
 * Gestiona la tabla, la inserción, edición y eliminación de películas.
 *
 * @author Alumno- Marco Martin
 * @version 1.0
 */
public class PeliculaController {
    @FXML private TableView<Pelicula> tablaPeliculas;

    @FXML private TableColumn<Pelicula, String> colID;
    @FXML private TableColumn<Pelicula, String> colTitulo;
    @FXML private TableColumn<Pelicula, String> colDirector;
    @FXML private TableColumn<Pelicula, Integer> colDuracion;
    @FXML private TableColumn<Pelicula, LocalDate> colFecha;

    @FXML private TextField txtId;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtDirector;
    @FXML private TextField txtDuracion;
    @FXML private TextField txtFecha;

    @FXML private Button btnEliminar;
    @FXML private Button btnCerrar;
    @FXML private Label lblMensaje;

    private PeliculaService peliculaService;

    /**
     * Metodo que establece el mensaje de bienvenida con el nombre del usuario.
     *
     * @param nombreUsuario el nombre del usuario logueado
     */
    public void inicioUsuario(String nombreUsuario) {
        lblMensaje.setText("Bienvenido a la cartelera, " + nombreUsuario );
    }

    /**
     * Metodo que introduce el servicio de películas y carga los datos en la tabla.
     *
     * @param peliculaService el servicio de gestión de películas
     */
    public void setPeliculaService(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
        tablaPeliculas.setItems(peliculaService.getPeliculas());
    }

    /**
     * Metodo que cierra la sesión actual previa confirmación del usuario y vuelve a la pantalla de inicio.
     */
    @FXML
    private void cerrarSesion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText("¿Seguro que quieres salir?");
        alert.setContentText("Se cerrará la sesión actual.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/personas_view.fxml"));

                    Scene scene = new Scene(loader.load());

                    Stage stage = (Stage) btnCerrar.getScene().getWindow();
                    stage.setScene(scene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Metodo que inicializa los componentes de la vista.
     * Configura el tamaño de las columnas mediante el siguiente metodo, la edición de celdas y los eventos de clic.
     */
    @FXML
    private void initialize() {
        prefWidthColumns();

        colID.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        colTitulo.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colDirector.setCellValueFactory(cellData -> cellData.getValue().directorProperty());
        colDuracion.setCellValueFactory(cellData -> cellData.getValue().duracionProperty().asObject());
        colFecha.setCellValueFactory(cellData -> cellData.getValue().fechaPublicacionProperty());

        tablaPeliculas.setEditable(true);

        // Edición del nombre
        colTitulo.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter()));
        colTitulo.setOnEditCommit(event -> {
            Pelicula pelicula = event.getRowValue();
            String nuevoTitulo = event.getNewValue();
            try {
                peliculaService.actualizarNombre(pelicula, nuevoTitulo);
            } catch (PeliculaExcepcion e) {
                mostrarError(e.getMessage());
            }
        });

        // Edición de la fecha
        colFecha.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        colFecha.setOnEditCommit(event -> {
            Pelicula pelicula = event.getRowValue();
            LocalDate nuevaFecha = event.getNewValue();
            try {
                peliculaService.actualizarFechaPublicacion(pelicula, nuevaFecha);
            } catch (PeliculaExcepcion e) {
                mostrarError(e.getMessage());
            }
        });

        // Registro visualización al hacer doble clic
        tablaPeliculas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();

                if (seleccionada != null) {
                    mostrarAlertVisualizacion(seleccionada);
                }
            }
        });
    }

    /**
     * Metodo que ajusta el ancho de las columnas de forma proporcional al tamaño de la tabla.
     */
    private void prefWidthColumns() {
        tablaPeliculas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colID.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.05));
        colTitulo.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.45));
        colDirector.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.30));
        colDuracion.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.15));
        colFecha.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.10));
    }

    /**
     * Metodo que recoge los datos de los campos de texto y añade una nueva película.
     */
    @FXML
    private void addPelicula() {
        try {
            Pelicula pelicula = new Pelicula(
                    txtId.getText(),
                    txtTitulo.getText(),
                    txtDirector.getText(),
                    Integer.parseInt(txtDuracion.getText()),
                    LocalDate.parse(txtFecha.getText())
            );

            peliculaService.agregarPelicula(pelicula);

            limpiarCampos();

        } catch (NumberFormatException e) {
            mostrarError("La duración debe ser un número válido");
        } catch (PeliculaExcepcion e) {
            mostrarError("Error al registrar la pelicula");
        }
    }

    /**
     * Metodo que muestra una ventana de alerta de tipo error.
     *
     * @param mensaje el texto a mostrar en la alerta
     */
    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Datos incorrectos");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    /**
     * Metodo que vacía todos los campos de texto del formulario.
     */
    private void limpiarCampos() {
        txtId.clear();
        txtTitulo.clear();
        txtDirector.clear();
        txtDuracion.clear();
        txtFecha.clear();
    }

    /**
     * Metodo que elimina la película seleccionada en la tabla.
     */
    @FXML
    private void eliminarPelicula() {
        Pelicula seleccionado = tablaPeliculas.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                peliculaService.eliminarPelicula(seleccionado);
            } catch (PeliculaExcepcion e) {
                mostrarError("Error al eliminar la pelicula");
            }
        }
    }

    /**
     * Metodo que muestra una alerta para confirmar la visualización de una película y la registra.
     *
     * @param pelicula la película a visualizar
     */
    private void mostrarAlertVisualizacion(Pelicula pelicula) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Desea visualizar la película?");
        alert.setContentText("Si pulsa sí, se visualizará");

        ButtonType btnSi = new ButtonType("Sí");
        ButtonType btnNo = new ButtonType("No");

        alert.getButtonTypes().setAll(btnSi, btnNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == btnSi) {
                peliculaService.registrarVisualizacion(pelicula);

                Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
                confirmacion.setHeaderText("Confirmación de visualización");
                confirmacion.setContentText("Visualización registrada con éxito");
                confirmacion.showAndWait();
            }
        });
    }
}