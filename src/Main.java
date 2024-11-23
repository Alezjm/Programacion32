import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static picocli.CommandLine.Help.Ansi.Style.bold;

public class Main {
    private static ArrayList<Producto> productos = new ArrayList<>();
    private static ArrayList<Usuario> usuarios = new ArrayList<>();
    private static ArrayList<Pedido> pedidos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int pedidoIdCounter = 1;
    private static Object PDTrueTypeFont;

    public static void main(String[] args) {
        inicializarDatos();

        boolean salir = false;
        while (!salir) {
            System.out.println("\n---- Sistema de Gestión de Inventario ----");
            System.out.println("1. Ver inventario");
            System.out.println("2. Crear pedido");
            System.out.println("3. Cambiar estado de pedido");
            System.out.println("4. Generar informes");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    verInventario();
                    break;
                case 2:
                    crearPedido();
                    break;
                case 3:
                    cambiarEstadoPedido();
                    break;
                case 4:
                    generarInformes();
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
    }

    private static void inicializarDatos() {
        productos.add(new Producto(1, "Laptop", "Dell Inspiron 15", 800.0, 10));
        productos.add(new Producto(2, "Monitor", "Samsung 24\"", 150.0, 15));
        productos.add(new Producto(3, "Teclado", "Logitech K120", 20.0, 30));

        usuarios.add(new Usuario("admin", "1234", "Administrador"));
        usuarios.add(new Usuario("almacenero", "5678", "Almacenero"));
        usuarios.add(new Usuario("contador", "abcd", "Contador"));
    }

    private static void verInventario() {
        System.out.println("\n---- Inventario ----");
        for (Producto producto : productos) {
            System.out.println(producto.toString());
        }
    }

    private static void crearPedido() {
        System.out.println("\n---- Crear Pedido ----");
        Pedido pedido = new Pedido(pedidoIdCounter++);

        boolean agregarProductos = true;
        while (agregarProductos) {
            System.out.println("Seleccione un producto por ID para agregar al pedido:");
            for (Producto producto : productos) {
                System.out.println(producto.toString());
            }
            System.out.print("ID del producto: ");
            int idProducto = scanner.nextInt();
            System.out.print("Cantidad: ");
            int cantidad = scanner.nextInt();
            scanner.nextLine();

            Producto productoSeleccionado = buscarProductoPorId(idProducto);
            if (productoSeleccionado != null) {
                pedido.agregarProducto(productoSeleccionado, cantidad);
            } else {
                System.out.println("Producto no encontrado.");
            }

            System.out.print("¿Deseas agregar otro producto? (s/n): ");
            String respuesta = scanner.nextLine();
            agregarProductos = respuesta.equalsIgnoreCase("s");
        }

        pedidos.add(pedido);
        System.out.println("Pedido creado con éxito:");
        System.out.println(pedido.toString());

        generarFacturaPDF(pedido);
    }

    private static Producto buscarProductoPorId(int idProducto) {
        for (Producto producto : productos) {
            if (producto.getId() == idProducto) {
                return producto;
            }
        }
        return null;
    }

    private static void cambiarEstadoPedido() {
        System.out.println("\n---- Cambiar Estado de Pedido ----");
        System.out.println("Pedidos actuales:");
        for (Pedido pedido : pedidos) {
            System.out.println(pedido.toString());
        }
        System.out.print("ID del pedido: ");
        int idPedido = scanner.nextInt();
        scanner.nextLine();

        Pedido pedidoSeleccionado = buscarPedidoPorId(idPedido);
        if (pedidoSeleccionado != null) {
            System.out.println("Estado actual: " + pedidoSeleccionado.getEstado());
            System.out.print("Nuevo estado (Pendiente/Enviado/Entregado): ");
            String nuevoEstado = scanner.nextLine();
            pedidoSeleccionado.setEstado(nuevoEstado);
            System.out.println("Estado del pedido actualizado.");
        } else {
            System.out.println("Pedido no encontrado.");
        }
    }

    private static Pedido buscarPedidoPorId(int idPedido) {
        for (Pedido pedido : pedidos) {
            if (pedido.getIdPedido() == idPedido) {
                return pedido;
            }
        }
        return null;
    }

    private static void generarInformes() {
        System.out.println("\n---- Generar Informes ----");
        System.out.println("1. Informe de Inventario");
        System.out.println("2. Informe de Pedidos");
        System.out.print("Selecciona una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                Informe.generarInformeInventario(productos);
                break;
            case 2:
                Informe.generarInformePedidos(pedidos);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void generarFacturaPDF(Pedido pedido) {
        String directorioFacturas = "facturas/";
        File directorio = new File(directorioFacturas);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        String nombreArchivo = directorioFacturas + "Factura_" + pedido.getIdPedido() + ".pdf";

        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                contenido.beginText();
                contenido.setFont(PDTrueTypeFont.ARIAL_BOLD, 18);
                contenido.setLeading(20);
                contenido.newLineAtOffset(50, 750);
                contenido.showText("Factura de Compra");
                contenido.newLine();
                contenido.setFont(PDTrueTypeFont.ARIAL_BOLD, 12);
                contenido.showText("ID Pedido: " + pedido.getIdPedido());
                contenido.newLine();
                contenido.showText("Fecha y Hora: " + pedido.getFechaHora());
                contenido.newLine();
                contenido.newLine();
                contenido.showText("Productos:");
                contenido.newLine();

                for (Producto producto : pedido.getProductosSolicitados()) {
                    contenido.showText("- " + producto.getNombre() + " (Cantidad: " + producto.getCantidadEnStock() + ") - $" + producto.getPrecio());
                    contenido.newLine();
                }

                contenido.newLine();
                contenido.setFont(PDTrueTypeFont.ARIAL_BOLD, 12);
                contenido.showText("Total: $" + calcularTotalPedido(pedido));
                contenido.endText();
            }

            documento.save(nombreArchivo);
            System.out.println("Factura generada: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al generar la factura: " + e.getMessage());
        }
    }

    private static double calcularTotalPedido(Pedido pedido) {
        double total = 0.0;
        for (Producto producto : pedido.getProductosSolicitados()) {
            total += producto.getPrecio() * producto.getCantidadEnStock();
        }
        return total;
    }

    public static void setPDTrueTypeFont(Object PDTrueTypeFont) {
        Main.PDTrueTypeFont = PDTrueTypeFont;
    }
}
