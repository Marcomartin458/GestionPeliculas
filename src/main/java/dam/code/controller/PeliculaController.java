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

public class PeliculaController {
    @FXML private TableView<Pelicula> tablaPeliculas;

    @FXML private TableColumn<Pelicula, String> colID;
    @FXML private TableColumn<Pelicula, String> colTitulo;
    @FXML private TableColumn<Pelicula, String> colDirector;
    @FXML private TableColumn<Pelicula, Integer> colDuracion;
    @FXML private TableColumn<Pelicula, LocalDate> colFecha;

    @FXML
    private TextField txtId;
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtDirector;
    @FXML
    private TextField txtDuracion;
    @FXML
    private TextField txtFecha;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnCerrar;

    @FXML
    private Label lblMensaje;

    public void inicioUsuario(String nombreUsuario) {
        lblMensaje.setText("Bienvenido a la cartelera, " + nombreUsuario );
    }

    private PeliculaService peliculaService;


    public void setPeliculaService(PeliculaService peliculaService) {
        this.peliculaService=peliculaService;
        tablaPeliculas.setItems(peliculaService.getPeliculas());
    }


    @FXML
    private void cerrarSesion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesion");
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

    @FXML
    private void initialize() {
        prefWidthColumns();

        colID.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        colTitulo.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colDirector.setCellValueFactory(cellData -> cellData.getValue().directorProperty());
        colDuracion.setCellValueFactory(cellData -> cellData.getValue().duracionProperty().asObject());
        colFecha.setCellValueFactory(cellData -> cellData.getValue().fechaPublicacionProperty());

        tablaPeliculas.setEditable(true);

        //Nombre editable
        colTitulo.setCellFactory(TextFieldTableCell.forTableColumn(new DefaultStringConverter() {
        }));
        colTitulo.setOnEditCommit(event -> {
            Pelicula pelicula = event.getRowValue();
            String nuevoTitulo = event.getNewValue();
            try {
                peliculaService.actualizarNombre(pelicula, nuevoTitulo);
            } catch (PeliculaExcepcion e) {
                mostrarError(e.getMessage());
            }
        });

        //Fecha editable
        colFecha.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter() {}));
        colFecha.setOnEditCommit(event -> {
            Pelicula pelicula = event.getRowValue();
            LocalDate nuevaFecha = event.getNewValue();
            try {
                peliculaService.actualizarFechaPublicacion(pelicula, nuevaFecha);
            } catch (PeliculaExcepcion e) {
                mostrarError(e.getMessage());
            }
        });
        // Registro visualizacion
        tablaPeliculas.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Pelicula seleccionada = tablaPeliculas.getSelectionModel().getSelectedItem();

                if (seleccionada != null) {
                    mostrarAlertVisualizacion(seleccionada);
                }
            }
        });

    }

    private void prefWidthColumns() {
        tablaPeliculas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colID.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.05));
        colTitulo.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.45));
        colDirector.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.30));
        colDuracion.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.15));
        colFecha.prefWidthProperty().bind(tablaPeliculas.widthProperty().multiply(0.10));
    }

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
            mostrarError("La duración debe ser un numero valido");
        } catch (PeliculaExcepcion e) {
            mostrarError("Error al registrar la pelicula");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Datos incorrectos");
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void limpiarCampos() {
        txtId.clear();
        txtTitulo.clear();
        txtDirector.clear();
        txtDuracion.clear();
        txtFecha.clear();
    }

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
    private void mostrarAlertVisualizacion(Pelicula pelicula) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Desea visualizar la pelicula?");
        alert.setContentText("Si pulsa si, se visualizara");

        ButtonType btnSi = new ButtonType("Si");
        ButtonType btnNo = new ButtonType("No");

        alert.getButtonTypes().setAll(btnSi, btnNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == btnSi) {
                peliculaService.registrarVisualizacion(pelicula);

                Alert confirmacion = new Alert(Alert.AlertType.INFORMATION);
                confirmacion.setHeaderText("Confirmación de visualizacion");
                confirmacion.setContentText("Visualización registrada con éxito");
                confirmacion.showAndWait();
            }


        });
    }
}
