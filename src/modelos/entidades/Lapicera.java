/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos.entidades;

/**
 *
 * @author USER
 */
public class Lapicera extends Producto {
    private Color color;
    private TipoLapicera tipo;
    
    /**
     * Comparador por defecto, establece todos los valores en null
     */
    public Lapicera() {}
    
    /**
     * Constructor auxiliar
     * @param id 
     */
    public Lapicera(int id) {
        super(id);
    }
    
    /**
     * Constructor que llama al const. de Producto
     * @param id
     * @param nombre
     * @param precio
     * @param marca 
     */
    public Lapicera(int id, String nombre, double precio, String marca) {
        super(id, nombre, precio, marca);
    }
    
    /**
     * Inicializa los atributos de Lapicera y le manda a otro constructor los
     * valores para los atributos de Producto
     * @param id
     * @param nombre
     * @param precio
     * @param marca
     * @param color
     * @param tipo 
     */
    public Lapicera(int id, String nombre, double precio, String marca, Color color, TipoLapicera tipo) {
        this(id, nombre, precio, marca);
        this.color = color;
        this.tipo = tipo;
    }
    
    
    public Color getColor() {
        return color;
    }

    public TipoLapicera getTipo() {
        return tipo;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setTipo(TipoLapicera tipo) {
        this.tipo = tipo;
    }
    
    @Override
    /**
     * Retorna una cadena con formato con los valores de sus atributos
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()); // Utiliza el toString de Producto
        sb.append("Color: ").append(color).append("\n");
        sb.append("Tipo: ").append(tipo).append("\n");
        
        return sb.toString();
    }
    
    @Override
    /**
     * Verifica que las lapiceras sean iguales.
     */
    public boolean equals(Object obj) {
        boolean esIgualProd = super.equals(obj); // Compara usando el equals de Producto
        
        Lapicera that = (Lapicera) obj;
        return esIgualProd &&
                this.color.equals(that.color) &&
                this.tipo.equals(that.tipo);
    }
    
    /**
     * Compara los colores de las lapiceras usando comparacion natural
     * @param l1
     * @param l2
     * @return 0 -> son iguales
     */
    public int compareByColor(Lapicera l1, Lapicera l2) {
        return l1.color.compareTo(l2.color);
    }
    
    /**
     * Compara los tipos de las lapiceras usando comparacion natural
     * @param l1
     * @param l2
     * @return 0 -> son iguales
     */
    public int compareByTipo(Lapicera l1, Lapicera l2) {
        return l1.tipo.compareTo(l2.tipo);
    }
}
