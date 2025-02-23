/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.excepciones;

/**
 *
 * @author USER
 */
public class ElementoVacioException extends Exception {
    public ElementoVacioException(String mensaje) {
        super("Error: " + mensaje);
    }
}
