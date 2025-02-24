package servicios;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Funciones auxiliares para el manejo de listas
 * @author USER
 * @param <T> 
 */
public interface ListFunc<T> {
    /**
     * Genera una lista nueva a partir de la pasada por parametro segun el predicado.
     * @param lista
     * @param predicado
     * @return ListaFiltrada
     */
    default List<T> filtrarLista(List<T> lista, Predicate<T> predicado) {
        return lista.stream()
                .filter(predicado)                           // lo filtra
                .collect(Collectors.toList()); // retorna la lista filtrada
    }
    
    /**
     * Genera una lista nueva a partir de la pasada por parametro segun el orden indicado
     * por el Comparator
     * @param lista
     * @param comparador
     * @return ListaOrdenada
     */
    default List<T> ordenarLista(List<T> lista, Comparator<T> comparador) {
        return lista.stream()
                .sorted(comparador)                          // Ordena la lista
                .collect(Collectors.toList());  // Convierte el Stream en una lista nueva ordenada
    }
    
    /**
     * Obtiene todo el contenido de la lista en formato String aplicando el titulo al principio.
     * @param lista
     * @param titulo
     * @return String
     */
    default String obtenerListaEnString(List<T> lista, String titulo){
        if(lista.isEmpty()) {
            return "Lista vacia";
        }
        else {
            StringBuilder sb = new StringBuilder();
            // En caso de que se le haya paso un titulo
            sb.append(titulo).append("\n");
            sb.append("-------------------------------\n");
            lista.forEach(e -> sb.append(e.toString())
                    .append("-------------------------------\n"));
            return sb.toString();
        }
    }
    
    /**
     * Obtiene todo el contenido de la lista en formato String sin titulo.
     * @param lista
     * @return String
     */
    default String obtenerListaEnString(List<T> lista) {
        if(lista.isEmpty()) {
                return "Lista vacia";
        }
        else {
            StringBuilder sb = new StringBuilder();
            sb.append("-------------------------------\n");
            lista.forEach(e -> sb.append(e.toString())
                    .append("-------------------------------\n"));
            return sb.toString();
        }
    }
}