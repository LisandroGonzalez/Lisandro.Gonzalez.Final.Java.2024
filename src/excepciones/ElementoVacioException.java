package excepciones;

public class ElementoVacioException extends Exception {
    public ElementoVacioException(String mensaje) {
        super("Error: " + mensaje);
    }
}
