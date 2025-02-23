package modelos.repositorios;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import modelos.entidades.Producto;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import servicios.Auxiliar;

public interface RepositorioGenerico <T extends Producto> extends Auxiliar<T> {
    // Instancia el objeto Gson para poder serializar convirtiendo a o desde JSON
    final Gson gson = new Gson();

    /**
     * Guarda el un archivo binario la lista pasada por parametro
     * @param archivo
     * @param objetos 
     */
    default void guardarEnBinario(String archivo, List<T> objetos) {
        /* Crea un objeto ObjectOutputStream mediante otro FileOutputStream al que se le pasa el 
        nombre del archivo a escribir */
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(objetos); // guarda la lista en el archivo
        }
        catch (IOException e) {
            System.err.println("Error al guardar en binario: " + e.getMessage());
        }
    }

    /**
     * Carga una lista de objetos de un archivo binario, de ser posible, sino retorna una lista vacia
     * @param archivo
     * @param clase
     * @return Lista de objetos de-serializados
     */
    @SuppressWarnings("unchecked")
    default List<T> cargarDesdeBinario(String archivo, Class<T> clase) {
        // Crea un objeto ObjectInputStream mediante otro FileInputStream al que se le pasa el nombre del archivo a leer
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            Object obj = ois.readObject();             // Lo guarda en una instancia de objeto
            if (obj instanceof List) {                 // Comprueba si el objeto es una instancia de Lista
                return new ArrayList<>((List<T>) obj); // Lo convierte a ArrayList para que sea mutable
            }
        } catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado, devolviendo una lista vacia.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar desde binario: " + e.getMessage());
        }
        return new ArrayList<>(); // Devuelve una lista vacia mutable vacia
    }

    /**
     * Guarda en un archivo JSON la lista pasada por parametro
     * @param archivo
     * @param objetos 
     */
    default void guardarEnJSON(String archivo, List<T> objetos) {
        try (Writer writer = new FileWriter(archivo)) { // Crea un objeto Writer para poder serializar a JSON
            gson.toJson(objetos, writer);               // lo serializa
        } catch (IOException e) {
            System.err.println("Error al guardar en JSON: " + e.getMessage());
        }
    }

    /**
     * Carga una lista de objetos de un archivo JSON, de ser posible, sino retorna una lista vacia
     * @param archivo
     * @param clase
     * @return 
     */
    default List<T> cargarDesdeJSON(String archivo, Class<T> clase) {
        /* Se crea un array vacio de tipo T para asi pasarle al metodo fromJson,
        y asi poder obtener los objetos dentro del archivo dentro de un array,
        equivalente a T[].class -> no es posible de esta manera
        */
        Class<T[]> arrayClase = (Class<T[]>) Array.newInstance(clase, 0).getClass();
        
        try (Reader reader = new FileReader(archivo)) {     // Crea un objeto Reader para poder de-serializar a JSON
            T[] array = gson.fromJson(reader, arrayClase);       // Crea un array con los objetos requeridos del archivo
            return new ArrayList<>(Arrays.asList(array));   // Lo retorna casteandolo a ArrayList
        } catch (IOException e) {
            System.err.println("Error al cargar desde JSON: " + e.getMessage() + ", devolviendo una lista vacia.");
            return new ArrayList<>(); // Devuelve una lista vacia
        }
    }
    
    default void guardarEnCSV(String archivo, List<T> objetos) {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            
            Optional<T> aux = objetos.stream().findFirst();
            T primerObj = aux.orElseThrow();
            
            String[] header = obtenerNombresDeCampos(primerObj.getClass());
            
            
            // Obtiene los nombres de los atributos para el header
            writer.write(String.join(",", header));
            writer.newLine();
            
           
            for (T obj : objetos) {
                writer.write(obtenerValoresDeCampos(obj));
                writer.newLine();
            }
        } catch(IOException | IllegalAccessException | NoSuchElementException  e) {
            System.out.println(e.getMessage());
        }
    }
    
    default List<T> cargarDesdeCSV(String archivo, Class<T> clase) {
        // Instancia un arraylist vacio
        List<T> lista = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            // Obtiene los nombres de los atributos del encabezado
            String[] camposArchivo = reader.readLine().split(",");
            
            // Obtiene los campos de la clase recibida por parametro
            Field[] campos = obtenerTodosLosCampos(clase);

            // Declara variable linea que es usada para obtener cada linea del archivo
            String linea;
            
            // Recorre el reader hasta que no haya mas lineas
            while ((linea = reader.readLine()) != null) {
                // Asigna los valores de entre cada coma a un array de Strings
                String[] valores = linea.split(",");

                // Obtiene el constructor de la clase
                Constructor<T> constructor = clase.getDeclaredConstructor();
                
                // Crea una instancia del objeto
                T objeto = constructor.newInstance();

                // Itera por la longitud del array con los nombres de los campos anteriormente creado
                for (int i = 0; i < camposArchivo.length; i++) {
                    // Recorre la cantidad de campos por la clase pasada
                    for (Field campo : campos) {
                        // Si el nombre del campo equivale al nombre del campo obtenido del csv
                        if (campo.getName().equals(camposArchivo[i])) {
                            campo.setAccessible(true); // Settea el acceso para poder asignarle el valor
                            
                            // Convierte el valor del objeto a su tipo original
                            Object valorConvertido = convertirValor(campo.getType(), valores[i]);
                            
                            // Se lo asigna al objeto en su respectivo atributo
                            campo.set(objeto, valorConvertido);
                        }
                    }
                }
                // Se agrega a la lista
                lista.add(objeto);
            }
            return lista; // Retorna la lista cargada con los datos
        } catch (Exception e) {
            System.out.println("No se pudieron cargar los datos del archivo.");
            return lista; // Retorna una lista vacia
        }
    }
    
    static void importarATxt(String archivo, String contenido) {
        try(FileWriter writer = new FileWriter(archivo)) {
            writer.write(contenido);
            writer.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
