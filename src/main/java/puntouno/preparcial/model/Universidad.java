package puntouno.preparcial.model;

import puntouno.preparcial.util.Persistencia;

import java.io.IOException;
import java.util.ArrayList;

public class Universidad {


    private ArrayList<Estudiante> listaEstudiantes = new ArrayList<>();

    private static class SingletonHolder {
        private final static ModelFactoryController eINSTANCE = new ModelFactoryController();
    }

    // Método para obtener la instancia de nuestra clase
    public static ModelFactoryController getInstance() {
        return SingletonHolder.eINSTANCE;
    }

    public Universidad(){
        cargarDatosDesdeArchivos();
    }

    private void cargarDatosDesdeArchivos() {
         Universidad universidad = new Universidad();
        try {
            Persistencia.cargarDatosArchivos(universidad);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Estudiante> getListaEstudiantes(){
        return listaEstudiantes;
    }

    public boolean agregarEstudiante(Estudiante estudiante) {
        try{
            if(!verificarEstudianteExistente(estudiante.getCodigo())) {
                crearEstudiante(estudiante);
                guardarEstudiante();
            }
            return true;
        }catch (Exception e){
            e.getMessage();
            return false;
        }
    }

    private void guardarEstudiante() {
        try {
            Persistencia.guardarEstudiante(getListaEstudiantes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void crearEstudiante(Estudiante estudiante) throws Exception{
        getListaEstudiantes().add(estudiante);
    }

    public boolean verificarEstudianteExistente(String codigo) throws Exception {
        if(estudianteExiste(codigo)){
            throw new Exception("El estudiante con código: "+codigo+" ya existe");
        }else{
            return false;
        }
    }

    public boolean estudianteExiste(String codigo) {
        boolean estudianteEncontrado = false;
        for (Estudiante estudiante : getListaEstudiantes()) {
            if(estudiante.getCodigo().equalsIgnoreCase(codigo)){
                estudianteEncontrado = true;
                break;
            }
        }
        return estudianteEncontrado;
    }



}
