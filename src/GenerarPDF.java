import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;

import java.io.File;
import java.io.IOException;

import static org.apache.pdfbox.pdmodel.font.PDTrueTypeFont.load;

public class GenerarPDF {
    public static void main(String[] args) {
        try {
            PDDocument documento = new PDDocument();
            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            String rutaFuenteBold = "resources/fonts/Arial-Bold.ttf";
            String rutaFuenteRegular = "resources/fonts/Arial.ttf";

            File fuenteBold = new File(rutaFuenteBold);
            File fuenteRegular = new File(rutaFuenteRegular);

            if (!fuenteBold.exists() || !fuenteRegular.exists()) {
                System.err.println("¡Error! El archivo de la fuente no se encuentra en la ruta especificada.");
                return;
            }

            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                PDTrueTypeFont fuenteBoldType = load(documento, fuenteBold);
                PDTrueTypeFont fuenteRegularType = load(documento, fuenteRegular);

                contenido.beginText();
                contenido.setFont(fuenteBoldType, 18);
                contenido.setLeading(20);
                contenido.newLineAtOffset(50, 750);
                contenido.showText("Factura de Compra");
                contenido.newLine();
                contenido.setFont(fuenteRegularType, 12);
                contenido.showText("ID Pedido: " + "12345");
                contenido.newLine();
                contenido.showText("Fecha y Hora: " + "2024-11-22 12:00");
                contenido.newLine();
                contenido.newLine();
                contenido.showText("Productos:");
                contenido.newLine();

                contenido.showText("- Producto 1 (Cantidad: 2) - $20");
                contenido.newLine();
                contenido.showText("- Producto 2 (Cantidad: 1) - $50");
                contenido.newLine();

                contenido.newLine();
                contenido.setFont(fuenteBoldType, 12);
                contenido.showText("Total: $90");
                contenido.endText();
            }

            documento.save("salida.pdf");
            System.out.println("PDF generado correctamente.");
            documento.close();
        } catch (IOException e) {
            System.err.println("Ha ocurrido un error al generar el PDF.");
            e.printStackTrace();
        }
    }

    private static PDTrueTypeFont load(PDDocument documento, File fuenteRegular) {
        return null;
    }
}
