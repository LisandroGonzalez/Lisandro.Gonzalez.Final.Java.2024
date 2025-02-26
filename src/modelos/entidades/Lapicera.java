package modelos.entidades;

public class Lapicera extends Producto {
    private Color color;
    private TipoLapicera tipo;
    
    /**
     * Constructor por defecto, establece todos los valores en null
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
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()); // Utiliza el toString de Producto
        sb.append("Color: ").append(color).append("\n");
        sb.append("Tipo: ").append(tipo).append("\n");
        
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        // Compara usando el equals de Producto
        boolean esIgualProd = super.equals(obj);
        
        // Casteo de Object a Lapicera
        Lapicera that = (Lapicera) obj;
        
        // Retorna la comparacion como producto y luego los valores de los atributos de Lapicera
        return esIgualProd &&
                this.color.equals(that.color) &&
                this.tipo.equals(that.tipo);
    }
}
