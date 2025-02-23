package servicios;

import java.util.List;
import modelos.entidades.Producto;
import modelos.excepciones.ElementoNoEncontradoException;

public interface CRUD <T extends Producto> {
    void agregar(T e);
    
    void eliminar(T e);
    
    void modificar(T e) throws ElementoNoEncontradoException;
    
    List<T> getLista();
}
