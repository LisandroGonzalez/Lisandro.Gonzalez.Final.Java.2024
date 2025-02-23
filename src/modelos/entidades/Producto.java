package modelos.entidades;

import java.io.Serializable;
import servicios.Identificable;

public abstract class Producto implements Comparable<Producto>, Serializable, Identificable {
    private int id;
    private String nombre;
    private double precio;
    private String marca;

    /**
     * Constructor por defecto -> todos sus valores seran null
     */
    public Producto() {}
    
    /**
     * Constructor auxiliar para realizar consultas por id
     * @param id 
     */
    public Producto(int id) {
        this.id = id;
    }

    /**
     * Constructor de Producto
     * @param id
     * @param nombre
     * @param precio
     * @param marca 
     */
    public Producto(int id, String nombre, double precio, String marca) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.marca = marca;
    }

    @Override
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Precio: $").append(precio).append("\n");
        sb.append("Marca: ").append(marca).append("\n");
        
        return sb.toString();
    }
    
    /**
     * Evalua si los valores de los atributos de ambos productos son iguales
     * @param p1
     * @param p2
     * @return resultado de la comparacion
     */
    private static boolean sonIguales(Producto p1, Producto p2) {
        return p1.id == p2.id &&
                p1.nombre.equalsIgnoreCase(p2.nombre) &&
                p1.precio == p2.precio &&
                p1.marca.equalsIgnoreCase(p2.marca);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { // Si los objetos son iguales
            return true;
        }
        if (obj == null) { // Si el objeto es null
            return false;
        }
        if (getClass() != obj.getClass()) { // Si las clases no coinciden
            return false;
        }
        
        // Casteo de Object a Producto
        Producto that = (Producto) obj;
        return sonIguales(this, that); // Resultado de la comparacion
    }
    
    @Override
    /**
     * Comparador natural de la clase,
     * Usa compare de la clase Integer para comparar 2 Id
     */
    public int compareTo(Producto that) {
        return Integer.compare(this.id, that.id);
    }
    
    /**
     * Usa compare de la clase Double para comparar 2 precios
     * @param p1
     * @param p2
     * @return 0 -> son iguales
     */
    public static int compareByPrecio(Producto p1, Producto p2) {
        return Double.compare(p1.precio, p2.precio);
    }
    
    /**
     * Compara ignorando las mayusculas
     * @param p1
     * @param p2
     * @return 0 -> son iguales
     */
    public static int compareByNombre(Producto p1, Producto p2) {
        return p1.nombre.compareToIgnoreCase(p2.nombre);
    }
    
    /**
     * Compara ignorando las mayusculas
     * @param p1
     * @param p2
     * @return 0 -> son iguales
     */
    public static int compareByMarca(Producto p1, Producto p2) {
        return p1.marca.compareToIgnoreCase(p2.marca);
    }
}
