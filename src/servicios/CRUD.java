package servicios;

import java.util.List;
import modelos.entidades.Producto;
import excepciones.ElementoNoEncontradoException;

public interface CRUD <T extends Producto> {
    void agregar(T e);
    
    void eliminar(T e);
    
    void modificar(T e);
    
    List<T> getLista();
}
