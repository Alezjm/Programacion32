import java.time.LocalDateTime;
import java.util.ArrayList;

public class Pedido {
    private int idPedido;
    private ArrayList<Producto> productosSolicitados;
    private String estado;
    private LocalDateTime fechaHora;

    public Pedido(int idPedido) {
        this.idPedido = idPedido;
        this.productosSolicitados = new ArrayList<>();
        this.estado = "Pendiente";
        this.fechaHora = LocalDateTime.now();
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public ArrayList<Producto> getProductosSolicitados() {
        return productosSolicitados;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        producto.actualizarStock(cantidad);
        productosSolicitados.add(new Producto(producto.getId(), producto.getNombre(), producto.toString(), producto.getPrecio(), cantidad));
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ID Pedido: " + idPedido + ", Estado: " + estado + ", Fecha y Hora: " + fechaHora + ", Productos: " + productosSolicitados.size();
    }
}

