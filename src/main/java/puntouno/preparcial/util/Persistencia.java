package puntouno.preparcial.util;

import puntouno.preparcial.model.Estudiante;
import puntouno.preparcial.model.Universidad;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Persistencia {

    public static final String RUTA_ARCHIVO_ESTUDIANTES = "C:\\Users\\Orlay.molina\\Desktop\\Pruebas\\preparcial\\src\\main\\resources\\puntouno\\preparcial\\archivos\\estudiantes.txt";

    public static final String RUTA_ARCHIVO_LOG = "C:\\Users\\Orlay.molina\\Desktop\\Pruebas\\preparcial\\src\\main\\resources\\puntouno\\preparcial\\archivos\\UniversidadLog.txt";

    public static void cargarDatosArchivos(Universidad universidad) throws FileNotFoundException, IOException {

        ArrayList<Estudiante> estudiantesCargados = cargarEstudiantes();
        if (estudiantesCargados.size() > 0)
            universidad.getListaEstudiantes().addAll(estudiantesCargados);
    }

    public static ArrayList<Estudiante> cargarEstudiantes() throws FileNotFoundException, IOException {
        ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_ESTUDIANTES);
        String linea = "";
        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            Estudiante estudiante = new Estudiante();
            estudiante.setCodigo(linea.split("--")[0]);
            estudiante.setNombre(linea.split("--")[1]);
            estudiante.setNota(linea.split("--")[2]);

            estudiantes.add(estudiante);
        }
        return estudiantes;
    }

    public static void guardarEstudiante(ArrayList<Estudiante> estudiantes) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Orlay.molina\\Desktop\\Pruebas\\preparcial\\src\\main\\resources\\puntouno\\preparcial\\archivos\\estudiantes.txt"))) {
            for (Estudiante estudiante : estudiantes) {
                bw.write(estudiante.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) throws IOException {
        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }
}
