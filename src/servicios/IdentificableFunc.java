package servicios;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Posibles funciones si la Clase que lo implementa tiene id.
 * @author USER
 * @param <T> 
 */
public interface IdentificableFunc<T extends Identificable> {
    /**
     * Busca un elemento por ID dentro de una lista y retorna true de ser encontrado, false de no serlo.
     * @param lista
     * @param idBuscado
     * @return boolean
     */
    default boolean existeIdEnLista(List<T> lista, int idBuscado) {
        return lista.stream()
                .anyMatch(e -> e.getId() == idBuscado);
    }
    
    /**
     * De ser posible retorna un Optional con el elemento de la lista que coincida con el id pasado.
     * @param idBuscado
     * @return Optional<T>
     */
    default Optional<T> obtenerPorID(List<T> lista, int idBuscado) {
        return lista.stream()
                .filter(e -> e.getId() == idBuscado) // Busca en la lista el idBuscado
                .findFirst(); // Si es encontrado retorna un Optional con el objeto.
    }
    
    /**
     * Encuentra el indice dentro de la lista de un elemento a partir del id del mismo.
     * @param lista
     * @param elemento
     * @return indice
     */
    default int obtenerIndiceEnLista(List<T> lista, T elemento) {
        return lista.indexOf(obtenerPorID(lista, elemento.getId()));
    }
    
    /**
     * En caso de que no haya registros en la lista, retorna el id 1,
     * caso contrario, retorna el id mas grande + 1
     * @return id
     */
    default int obtenerNuevoID(List<T> lista) {
        return lista.stream()
            .max(Comparator.comparingInt(T::getId)) // Encuentra el elemento con mayor ID
            .map(e -> e.getId() + 1)                                   // Extrae el ID mas grande y suma 1
            .orElse(1);                                                         // En caso de que no haya elementos en la lista, retorna 1
    }
}
