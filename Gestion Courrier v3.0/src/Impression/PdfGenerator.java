package Impression;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfWriter;

public class PdfGenerator {
	
    public static void impress() throws Exception {
        // Créez un document PDF
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("exemple.pdf"));
        document.open();

        // Ajoutez du contenu au document
        document.add(new Paragraph("Yvanooooooooooooooooo"
        		+ "ooooooooooooooooooooooooooo"
        		+ "ooooooooooooooooooooooooooo"
        		+ "ooooooooooooooooooooooooooooooooooooo"
        		+ "ooooooooooooooooooooooooooooooooooooo"
        		+ "oooooooooooooooooooooooooooooooooooooooo"
        		+ "oooooooooooooooooooooooooooooooooooooooooooooooooooo"
        		+ "ooooooooooooooooooooooooooooooooooooooo"));
        
        System.out.println("document ok");

        // Fermez le document
        document.close();
    }
}
