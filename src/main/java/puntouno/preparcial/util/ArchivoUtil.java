package puntouno.preparcial.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ArchivoUtil {

    static String fechaSistema = "";

    public static ArrayList<String> leerArchivo(String ruta) throws IOException {

        ArrayList<String>  contenido = new ArrayList<String>();
        FileReader fr=new FileReader(ruta);
        BufferedReader bfr=new BufferedReader(fr);
        String linea="";
        while((linea = bfr.readLine())!=null)
        {
            contenido.add(linea);
        }
        bfr.close();
        fr.close();
        return contenido;
    }

    private static void cargarFechaSistema() {

        String diaN = "";
        String mesN = "";

        Calendar cal1 = Calendar.getInstance();


        int dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH) + 1;
        int año = cal1.get(Calendar.YEAR);


        if (dia < 10) {
            diaN += "0" + dia;
        } else {
            diaN += "" + dia;
        }
        if (mes < 10) {
            mesN += "0" + mes;
        } else {
            mesN += "" + mes;
        }

        fechaSistema = año + "-" + mesN + "-" + diaN;

    }


    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) throws IOException {
        String log = "";
        Logger LOGGER = Logger.getLogger(accion);
        FileHandler fileHandler =  null;
        fileHandler = new FileHandler(rutaArchivo,true);
        cargarFechaSistema();
        try {

            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            switch (nivel) {
                case 1:
                    LOGGER.log(Level.INFO,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                case 2:
                    LOGGER.log(Level.WARNING,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                case 3:
                    LOGGER.log(Level.SEVERE,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                default:
                    break;
            }

        } catch (SecurityException e) {

            LOGGER.log(Level.SEVERE,e.getMessage());
            e.printStackTrace();
        } finally {

            fileHandler.close();
        }
    }
}
