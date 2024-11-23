import java.util.ArrayList;

public class Informe {
    public static void generarInformeInventario(ArrayList<Producto> productos) {
        System.out.println("\n---- Informe de Inventario ----");
        for (Producto producto : productos) {
            System.out.println(producto.toString());
        }
    }

    public static void generarInformePedidos(ArrayList<Pedido> pedidos) {
        System.out.println("\n---- Informe de Pedidos ----");
        for (Pedido pedido : pedidos) {
            System.out.println(pedido.toString());
        }
    }
}

