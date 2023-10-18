package puntouno.preparcial.util;

import puntouno.preparcial.model.Estudiante;

import java.util.function.Predicate;

public class EstudianteUtil {

    public static Predicate<Estudiante> buscarPorCodigo(String codigo){
        return estudiante -> estudiante.getCodigo().contains(codigo);
    }

    public static Predicate<Estudiante> buscarPorNombre(String nombre){
        return estudiante -> estudiante.getNombre().contains(nombre);
    }

    public static Predicate<Estudiante> buscarPorNota(String nota){
        return estudiante -> estudiante.getNota().contains(nota);
    }

    public static Predicate<Estudiante> buscarPorTodo(String codigo, String nombre, String nota) {

        Predicate<Estudiante> predicado = estudiante -> true;

        if( codigo != null && !codigo.isEmpty() ){
            predicado = predicado.and(buscarPorCodigo(codigo));
        }
        if( nombre != null && !nombre.isEmpty() ){
            predicado = predicado.and(buscarPorNombre(nombre));
        }
        if( nota != null && !nota.isEmpty() ){
            predicado = predicado.and(buscarPorNota(nota));
        }

        return predicado;
    }
}
