package modelos.gestores;

import modelos.entidades.Producto;
import java.util.List;
import modelos.repositorios.RepositorioGenerico;
import servicios.CRUD;
import servicios.IdentificableFunc;

public class GestorGenerico<T extends Producto> implements CRUD<T>, RepositorioGenerico, IdentificableFunc<T> {
    protected String archivo;
    protected List<T> lista;
    
    public GestorGenerico(String archivo, Class<T> clase) {
        this.archivo = archivo;
        
        lista = cargarDesdeCSV(archivo, clase);
    }
    
    /**
     * Agrega un elemento a la lista.
     * @param archivo
     * @param e 
     */
    @Override
    public void agregar(T e) {
        lista.add(e);
    }
    
    /**
     * Elimina un elemento de la lista.
     * @param archivo
     * @param e 
     */
    @Override
    public void eliminar(T e) {
        lista.remove(e);
    }
    
    /**
     * Modifica un elemento de la lista y lo guarda.
     * @param elemento 
     */
    @Override
    public void modificar(T elemento){
        // Obtiene el indice en el que se encuentra dentro de la lista
        int index = obtenerIndiceEnLista(lista, elemento);

        // De haber sido encontrado lo modifica y lo guarda
        if (index != -1) {
            lista.set(index, elemento);
        }
    }
    
    /**
     * Retorna la lista.
     * @return lista
     */
    @Override
    public List<T> getLista() {
        return lista;
    }
    
    /**
     * Guarda los cambios efectuados a la lista en el archivo
     */
    public void guardarCambios() {
        guardarEnCSV(archivo, lista);
    }
}
