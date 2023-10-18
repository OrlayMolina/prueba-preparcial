package puntouno.preparcial.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import puntouno.preparcial.model.Estudiante;
import puntouno.preparcial.model.Universidad;
import puntouno.preparcial.util.EstudianteUtil;
import puntouno.preparcial.util.Persistencia;

import java.io.IOException;
import java.util.function.Predicate;


public class EstudiantesViewController {

    Universidad univerdidad;
    ModelFactoryController modelFactoryController;
    Persistencia persistencia;
    ObservableList<Estudiante> listaEstudiantes = FXCollections.observableArrayList();
    Estudiante estudianteSeleccionado;

    @FXML
    private TableColumn<Estudiante, String> colCodigo;

    @FXML
    private TableColumn<Estudiante, String> colNombre;

    @FXML
    private TableColumn<Estudiante, String> colNota;

    @FXML
    private TableView<Estudiante> tableEstudiantes;

    @FXML
    private TextField txfCodigo;

    @FXML
    private TextField txfNombre;

    @FXML
    private TextField txfNota;

    @FXML
    void agregarEstudiante(ActionEvent event) throws Exception {
        crearEstudiante();
    }

    @FXML
    void buscarEstudiante(ActionEvent event) throws IOException {
        String codigo = txfCodigo.getText();
        String nombre = txfNombre.getText();
        String nota = txfNota.getText();

        buscarEstudiantes(codigo, nombre, nota);
    }

    @FXML
    void cancelarFiltro(ActionEvent event) {
        cancelarBusqueda();
    }

    @FXML
    void initialize() {
        univerdidad = new Universidad();
        persistencia = new Persistencia();
        initView();
    }

    private void initView() {
        initDataBinding(); // table
        obtenerEstudiantes(); // lista estudiantes
        tableEstudiantes.getItems().clear();
        tableEstudiantes.setItems(listaEstudiantes);
        listenerSelection();
    }

    private void initDataBinding() {
        colCodigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCodigo()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colNota.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNota()));
    }

    private void listenerSelection() {
        tableEstudiantes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            estudianteSeleccionado = newSelection;
            mostrarInformacionEstudiante(estudianteSeleccionado);
        });
    }

    private void mostrarInformacionEstudiante(Estudiante estudianteSeleccionado) {
        if(estudianteSeleccionado != null){
            txfCodigo.setText(estudianteSeleccionado.getCodigo());
            txfNombre.setText(estudianteSeleccionado.getNombre());
            txfNota.setText(estudianteSeleccionado.getNota());

        }
    }

    private void obtenerEstudiantes() {
        listaEstudiantes.addAll(univerdidad.getListaEstudiantes());
    }

    private void registrarAcciones(String mensaje, int nivel, String accion) throws IOException {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }

    private void crearEstudiante() throws Exception {

        Estudiante estudiante = construirEstudiante();

        if(datosValidos(estudiante)){
            if(univerdidad.agregarEstudiante(estudiante)){
                listaEstudiantes.add(estudiante);
                mostrarMensaje("Notificación estudiante", "Estudiante creado", "El estudiante se ha creado con éxito", Alert.AlertType.INFORMATION);
                limpiarCamposEstudiante();
                registrarAcciones("Estudiante agregado",1, "Agregar estudiante");

            }else{
                mostrarMensaje("Notificación estudiante", "Estudiante no creado", "El estudiante no se ha creado", Alert.AlertType.ERROR);
            }
        }else{
            mostrarMensaje("Notificación estudiante", "Estudiante no creado", "Los datos ingresados son invalidos", Alert.AlertType.ERROR);
        }

    }

    private void buscarEstudiantes(String codigo, String nombre, String notas) throws IOException {

        Predicate<Estudiante> predicate = EstudianteUtil.buscarPorTodo(codigo, nombre, notas);
        ObservableList<Estudiante> estudiantesFiltrados = listaEstudiantes.filtered(predicate);
        tableEstudiantes.setItems(estudiantesFiltrados);
        registrarAcciones("Estudiante filtrado",1, "Filtro de un estudiante");
    }

    private Estudiante construirEstudiante() {
        return new Estudiante(
                txfCodigo.getText(),
                txfNombre.getText(),
                txfNota.getText()

        );
    }

    private void cancelarBusqueda(){
        limpiarCamposEstudiante();
        tableEstudiantes.getSelectionModel().clearSelection();
        tableEstudiantes.setItems(listaEstudiantes);
        listenerSelection();
    }

    private void limpiarCamposEstudiante() {
        txfCodigo.setText("");
        txfNombre.setText("");
        txfNota.setText("");

    }

    private boolean datosValidos(Estudiante estudiante) {
        String mensaje = "";
        if(estudiante.getCodigo() == null || estudiante.getCodigo().equals(""))
            mensaje += "El código del estudiante es invalido \n" ;
        if(estudiante.getNombre() == null || estudiante.getNombre() .equals(""))
            mensaje += "El nombre del estudiante es invalido \n" ;
        if(estudiante.getNota() == null || estudiante.getNota() .equals(""))
            mensaje += "las notas del estudiante es invalido \n" ;
        if(mensaje.equals("")){
            return true;
        }else{
            mostrarMensaje("Notificación estudiante", "Estudiante no creado", mensaje, Alert.AlertType.ERROR);
            return false;
        }
    }

    private void mostrarMensaje(String titulo, String header, String contenido, Alert.AlertType alertType) {
        Alert aler = new Alert(alertType);
        aler.setTitle(titulo);
        aler.setHeaderText(header);
        aler.setContentText(contenido);
        aler.showAndWait();
    }


}
