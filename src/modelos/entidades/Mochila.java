package modelos.entidades;

public class Mochila extends Producto {
    private String disenio;
    private double capacidad;
    
    /**
     * Constructor por defecto, establece todos los valores en null
     */
    public Mochila() {}
    
    /**
     * Constructor auxiliar
     * @param id 
     */
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()); // Utiliza el toString de Producto
        sb.append("Diseño: ").append(disenio).append("\n");
        sb.append("Capacidad: ").append(capacidad).append("\n");
        
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        // Compara usando el equals de Producto
        boolean esIgualProd = super.equals(obj);
        
        // Casteo de Object a Mochila
        Mochila that = (Mochila) obj;
        
        // Retorna la comparacion como producto y luego los valores de los atributos de Mochila
        return esIgualProd && 
                this.disenio.equals(that.disenio) &&
                this.capacidad == that.capacidad;
    }
}
