package servicios;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface IdentificableFunc<T extends Identificable> {
    /**
     * Busca un elemento por ID y retorna true de ser encontrado en la listay false de no serlo.
     * @param lista
     * @param idBuscado
     * @return boolean
     */
    default boolean existeIdEnLista(List<T> lista, int idBuscado) {
        return lista.stream()
                .anyMatch(e -> e.getId() == idBuscado);
    }
    
    /**
     * De ser posible obtiene el elemento que coincida con el id pasado.
     * @param idBuscado
     * @return <T>
     */
    default Optional<T> obtenerPorID(List<T> lista, int idBuscado) {
        return lista.stream()
                .filter(e -> e.getId() == idBuscado)
                .findFirst();
    }
    
    /**
     * Encuentra el indice de un elemento dentro de una lista a partir del id del mismo
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
            .map(e -> e.getId() + 1)  // Extrae el ID y suma 1
            .orElse(1); // En caso de que no haya elementos, devuelve 1
    }
}
