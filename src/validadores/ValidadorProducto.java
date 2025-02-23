package validadores;

public interface ValidadorProducto {
    /**
     * El String es valido si no es null, ni esta vacio.
     * @param cadena
     * @return 
     */
    default boolean isStringValido(String cadena) {
        return cadena != null && !cadena.isEmpty() && !cadena.isBlank();
    }
    
    /**
     * La cadena es valida si unicamente contiene un valor numerico
     * @param numero
     * @return 
     */
    default boolean isStringNumerico(String numero) {
        boolean retorno = false;
        try{
            double n = Double.parseDouble(numero);
            retorno = true;
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        return retorno;
    }
    
    /**
     * La cadena es valida si es realmente un numero y es mayor a 0.
     * @param precio
     * @return 
     */
    default boolean isNumeroPositivo(String numero) {
        boolean retorno = false;

        if(isStringNumerico(numero) && Double.parseDouble(numero) >= 0) {
            retorno = true;
        }

        return retorno;
    }
    
    default boolean isBooleanValido(String bool) {
        return bool.contentEquals("true") || bool.contentEquals("false");
    }
}