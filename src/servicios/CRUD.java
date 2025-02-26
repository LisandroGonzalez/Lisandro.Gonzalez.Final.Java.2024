package servicios;

import java.util.List;

public interface CRUD <T> {
    void agregar(T e);
    
    void eliminar(T e);
    
    void modificar(T e);
    
    List<T> getLista();
}
