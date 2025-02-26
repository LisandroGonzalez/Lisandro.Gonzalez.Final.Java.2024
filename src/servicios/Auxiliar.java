package servicios;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import modelos.entidades.Color;
import modelos.entidades.GeneroLibro;
import modelos.entidades.Producto;
import modelos.entidades.TipoLapicera;

public interface Auxiliar <T extends Producto> {
    /**
     * Obtiene una lista con los valores de un Enumerado
     * @param <T>
     * @param enumerado
     * @return 
     */
    default <T extends Enum<T>> List<String> obtenerValoresEnum(Class<T> enumerado) {
        return Stream.of(enumerado.getEnumConstants()) // Genera un Stream a partir de las constantes dentro del Enum
                .map(Enum::name)                       // Mappea los nombres del enum
                .collect(Collectors.toList());         // Lo retorna como lista
    }
    
    /**
     * Obtiene todos los campos de una clase, incluyendo los de la clase padre.
     * @param clase
     * @return campos
     */
    default Field[] obtenerTodosLosCampos(Class<?> clase) {
        List<Field> campos = new ArrayList<>();

        // Recorre la jerarquia de clases hasta llegar a Object
        while (clase != null) {
            // Agrega todos los campos de la clase actual
            campos.addAll(0, Arrays.asList(clase.getDeclaredFields()));

            // Avanza a la superclase
            clase = clase.getSuperclass();
        }
        
        // En caso de que tenga el atributo serialVersionUID lo remueve de la lista
        campos.removeIf(campo -> campo.getName().equals("serialVersionUID"));

        // Convierte la lista en un array
        return campos.toArray(new Field[0]);
    }
    
    /**
     * Obtiene los nombres de los campos dentro del array pasado por parametro
     * y los retorna dentro de un array de String
     * @param campos
     * @return nombres
     */
    default String[] obtenerNombresDeCampos(Class<?> clase) {
        Field[] campos = obtenerTodosLosCampos(clase);
        
        return Arrays.stream(campos)  // Convierte el Array a Steam
                .map(Field::getName)         // Mappea por cada campo el nombre del mismo
                .toArray(String[]::new); // Retorna el resultado como un array de Strings
    }
    
    /**
     * Metodo auxiliar para la serializacion en formato csv,
     * obtiene los valores de los campos pasados por parametro 
     * y los retorna en una cadena separandolos con coma.
     * @param objeto
     * @return
     * @throws IllegalAccessException 
     */
    default String obtenerValoresDeCampos(T objeto) throws IllegalAccessException {
        Field[] campos = obtenerTodosLosCampos(objeto.getClass());
        
        return Arrays.stream(campos)
                .peek(campo -> campo.setAccessible(true))         // Permite acceder a atributos privados
                .map(campo -> {                                   // Por cada campo dentro de la lista
                    try {
                        return String.valueOf(campo.get(objeto)); // Obtiene el valor del campo y lo convierte a String
                    } catch (IllegalAccessException e) {
                        return "null";                            // En caso de error, retorna "null"
                    }
                })
                .collect(Collectors.joining(",")); // Une los valores con comas
    }
    
    /**
     * Convierte una el valor de una cadena a su tipo original.
     * @param tipo
     * @param valor
     * @return 
     */
    default Object convertirValor(Class<?> tipo, String valor) {
        if (tipo.equals(int.class) || tipo.equals(Integer.class)) {
            return Integer.parseInt(valor);
        } else if (tipo.equals(double.class) || tipo.equals(Double.class)) {
            return Double.parseDouble(valor);
        } else if (tipo.equals(boolean.class) || tipo.equals(Boolean.class)) {
            return Boolean.parseBoolean(valor);
        } else if (tipo.equals(Color.class)) {
            return Color.valueOf(valor);
        } else if (tipo.equals(GeneroLibro.class)) {
            return GeneroLibro.valueOf(valor);
        } else if (tipo.equals(TipoLapicera.class)) {
            return TipoLapicera.valueOf(valor);
        }
        
        return valor; // Si es String u otro tipo, lo deja igual
    }
}
