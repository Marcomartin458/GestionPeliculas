package dam.code.controller;

import dam.code.models.Persona;
import dam.code.persistency.JsonManager;
import dam.code.persistency.UsuariosDAO;
import dam.code.repository.PeliculaRepository;
import dam.code.service.PeliculaService;
import dam.code.service.PersonaService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class InicioController {

    @FXML
    private TextField txtdni;

    @FXML
    private PasswordField txtContraseña;

    @FXML
    private Button btnInicio;

    @FXML
    private Button btnRegistro;

    private PersonaService personaService = new PersonaService(new UsuariosDAO());


    private Persona usuarioRegistrado;

    @FXML
    public void iniciarSesion() {
        String dni = txtdni.getText();
        String password = txtContraseña.getText();

        if (dni.isEmpty() || password.isEmpty()) {
            mostrarAlertErrorValidacion("Debes introducir tu DNI y contraseña.");
            return;
        }
        Persona persona = personaService.getUsuario(dni);

        if (persona == null) {
            mostrarAlertErrorValidacion("No existe ningún usuario registrado con ese DNI.");
            return;
        }

        String passwordGuardada = personaService.getRegistros().get(persona);

        if (passwordGuardada != null && passwordGuardada.equals(password)) {
            usuarioRegistrado = persona;
            mostrarAlertConfirm();
        } else {
            mostrarAlertErrorValidacion("La contraseña es incorrecta.");
        }
    }

    @FXML
    public void cambiarVentana() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/personas_view.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) btnRegistro.getScene().getWindow();
            stage.setScene(new Scene(root, 600, 650));

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlertErrorValidacion("Error al cargar la pantalla de registro.");
        }
    }

    private void mostrarAlertErrorValidacion(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de validación");
        alert.setHeaderText("No se ha podido iniciar sesión");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarAlertConfirm() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Inicio exitoso");

        alert.setHeaderText("Bienvenido/a, " + usuarioRegistrado.getNombre());
        alert.setContentText("Desea acceder a su cartelera de películas personal?");

        ButtonType btnSi = new ButtonType("Sí");
        ButtonType btnNo = new ButtonType("No");

        alert.getButtonTypes().setAll(btnSi, btnNo);

        alert.showAndWait().ifPresent(response -> {
            if (response == btnSi) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/pelicula_view.fxml"));
                    Parent root = fxmlLoader.load();

                    PeliculaController peliculaController = fxmlLoader.getController();

                    peliculaController.inicioUsuario(usuarioRegistrado.getNombre());


                    String dni = usuarioRegistrado.getDni();
                    PeliculaRepository repository = new JsonManager(dni);
                    PeliculaService service = new PeliculaService(repository);

                    peliculaController.setPeliculaService(service);

                    Stage stage = (Stage) btnInicio.getScene().getWindow();
                    stage.setScene(new Scene(root));

                } catch (Exception e) {
                    e.printStackTrace();
                    mostrarAlertErrorValidacion("Error al acceder al catálogo.");
                }
            }
        });
    }
}