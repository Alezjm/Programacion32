public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadEnStock;

    public Producto(int id, String nombre, String descripcion, double precio, int cantidadEnStock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadEnStock = cantidadEnStock;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidadEnStock() {
        return cantidadEnStock;
    }

    public void actualizarStock(int cantidad) {
        this.cantidadEnStock -= cantidad;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Precio: $" + precio + ", Stock: " + cantidadEnStock;
    }
}

