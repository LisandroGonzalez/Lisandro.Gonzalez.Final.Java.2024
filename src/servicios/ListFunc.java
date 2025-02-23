package servicios;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface ListFunc<T> {
    default List<T> filtrarLista(List<T> lista, Predicate<T> predicado) {
        return lista.stream()
                .filter(predicado) // lo filtra
                .collect(Collectors.toList()); // retorna la lista filtrada
    }
    
    default List<T> ordenarLista(List<T> lista, Comparator<T> comparador) {
        return lista.stream()
                .sorted(comparador)
                .collect(Collectors.toList());
    }
    
    default String obtenerListaEnString(List<T> lista, String titulo){
        if(lista.isEmpty()) {
            return "Lista vacia";
        }
        else {
            StringBuilder sb = new StringBuilder();
            if(titulo != null) sb.append(titulo).append("\n");
            sb.append("-------------------------------\n");
            lista.forEach(e -> sb.append(e.toString())
                    .append("-------------------------------\n"));
            return sb.toString();
        }
    }
}
