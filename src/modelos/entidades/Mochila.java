/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.entidades;

/**
 *
 * @author USER
 */
public class Mochila extends Producto {
    private String disenio;
    private double capacidad;
    
    /**
     * Comparador por defecto, establece todos los valores en null
     */
    public Mochila() {}
    
    public Mochila(int id) {
        super(id);
    }
    
    /**
     * Constructor que llama al const. de Producto
     * @param id
     * @param nombre
     * @param precio
     * @param marca 
     */
    public Mochila(int id, String nombre, double precio, String marca) {
        super(id, nombre, precio, marca);
    }
    
    /**
     * Inicializa los atributos de Mochila y le manda a otro constructor los
     * valores para los atributos de Producto
     * @param id
     * @param nombre
     * @param precio
     * @param marca
     * @param disenio
     * @param capacidad 
     */
    public Mochila(int id, String nombre, double precio, String marca, String disenio, double capacidad) {
        this(id, nombre, precio, marca);
        this.disenio = disenio;
        this.capacidad = capacidad;
    }

    public String getDisenio() {
        return disenio;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public void setDisenio(String disenio) {
        this.disenio = disenio;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }
    
    @Override
    /**
     * Retorna una cadena con formato con los valores de sus atributos
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()); // Utiliza el toString de Producto
        sb.append("Diseño: ").append(disenio).append("\n");
        sb.append("Capacidad: ").append(capacidad).append("\n");
        
        return sb.toString();
    }
    
    @Override
    /**
     * Verifica que las mochilas sean iguales.
     */
    public boolean equals(Object obj) {
        boolean esIgualProd = super.equals(obj); // Compara usando el equals de Producto
        
        Mochila that = (Mochila) obj;
        return esIgualProd && 
                this.disenio.equals(that.disenio) &&
                this.capacidad == that.capacidad;
    }
    
    /**
     * Usa Comparable de String para comparar los nombres
     * @param m1
     * @param m2
     * @return 0 -> son iguales
     */
    public int compareByDisenio(Mochila m1, Mochila m2) {
        return m1.disenio.compareToIgnoreCase(m2.disenio);
    }
    
    /**
     * Usa Comparator de la clase Double para comparar 2 capacidades
     * @param m1
     * @param m2
     * @return 0 -> son iguales
     */
    public int compareByCapacidad(Mochila m1, Mochila m2) {
        return Double.compare(m1.capacidad, m2.capacidad);
    }
}
