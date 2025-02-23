package modelos.entidades;

public class Libro extends Producto {
    private String autor;
    private GeneroLibro genero;
    private int cantHojas;
    
    public Libro() {}
    
    public Libro(int id) {
        super(id);
    }
    
    public Libro(int id, String nombre, double precio, String marca) {
        super(id, nombre, precio, marca);
    }
    
    public Libro(int id, String nombre, double precio, String marca, String autor, GeneroLibro genero, int cantHojas) {
        this(id, nombre, precio, marca);
        this.autor = autor;
        this.genero = genero;
        this.cantHojas = cantHojas;
    }
    
    public String getAutor() {
        return autor;
    }

    public GeneroLibro getGenero() {
        return genero;
    }
    
    public int getCantHojas() {
        return cantHojas;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(GeneroLibro genero) {
        this.genero = genero;
    }
    
    public void setCantHojas(int cantHojas) {
        this.cantHojas = cantHojas;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("Autor: ").append(autor).append("\n");
        sb.append("Genero: ").append(genero).append("\n");
        sb.append("Cantidad de hojas: ").append(cantHojas).append("\n");
        
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object obj) {
        boolean esIgual = super.equals(obj);
        
        Libro that = (Libro) obj;
        return esIgual &&
                this.autor.equals(that.autor) &&
                this.genero.equals(that.genero) &&
                this.cantHojas == that.cantHojas;
    }
}
