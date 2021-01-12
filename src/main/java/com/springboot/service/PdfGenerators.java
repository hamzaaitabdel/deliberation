package com.springboot.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.itextpdf.io.image.ImageDataFactory;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.springboot.entities.*;

public class PdfGenerators {
    public static final String REPOSITORY="C:/Users/hamza/delibiration";
    public static File createPdf(List<DelibirationModule> notes,String semestre) throws MalformedURLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
        LocalDateTime now = LocalDateTime.now();  
        File file=null;
        //TODO set horizontal logos and signatures 
        try {
            file =new File(REPOSITORY+"/delibiration_"+semestre.toUpperCase()+".pdf");
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
            Document doc = new Document(pdfDoc);
            Table table = new Table(5);
            table.setWidth(500);
            table.setMargin(23);
            doc.add(new Image(ImageDataFactory.create("https://i.ibb.co/8gbMHmP/external-content-duckduckgo.png")));
            doc.add(new Paragraph("delibiration Semestre :"+semestre));
            doc.add(new Paragraph("Le:"+dtf.format(now)+""));
            //doc.add(new Image(ImageDataFactory.create("C:/Users/hamza/OneDrive/Bureau/logo.png")));
            table.addCell(new Cell().add(new Paragraph("CNE")));
            table.addCell(new Cell().add(new Paragraph("Nom")));
            table.addCell(new Cell().add(new Paragraph("Module")));
            table.addCell(new Cell().add(new Paragraph("Note")));
            table.addCell(new Cell().add(new Paragraph("Resultat")));
            for (DelibirationModule note :notes) {
                //table.addCell(new Cell().add(new Paragraph(note.getCne() +"")));
                table.addCell(new Cell().add(new Paragraph(note.getEtudiant().getCne())));
                table.addCell(new Cell().add(new Paragraph(note.getEtudiant().getNom()+" "+note.getEtudiant().getPrenom())));
                table.addCell(new Cell().add(new Paragraph(note.getModule().getName()+"")));
                table.addCell(new Cell().add(new Paragraph(note.getNote()+"")));
                table.addCell(new Cell().add(new Paragraph(note.getNote()>note.getModule().getNoteValidation()? "V" : "NV")));
            }
            doc.add(table);
            doc.add(new Paragraph("Responsable"));
            doc.add(new Paragraph("\n\n\n"));
            doc.add(new Paragraph("Professeur:"));
            doc.close();
            System.out.println("Table created successfully..");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
            return file;
     
    }
	public static File createPdfSemestre(List<SemestreResult> notes, String semestre) throws MalformedURLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
        LocalDateTime now = LocalDateTime.now();  
        File file=null;
        //TODO set horizontal logos and signatures 
        try {
            file =new File(REPOSITORY+"/delibiration_"+semestre.toUpperCase()+".pdf");
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
            Document doc = new Document(pdfDoc);
            Table table = new Table(4);
            table.setWidth(500);
            table.setMargin(23);
            doc.add(new Image(ImageDataFactory.create("https://i.ibb.co/8gbMHmP/external-content-duckduckgo.png")));
            doc.add(new Paragraph("delibiration Semestre :"+semestre));
            doc.add(new Paragraph("Le:"+dtf.format(now)+""));
            //doc.add(new Image(ImageDataFactory.create("C:/Users/hamza/OneDrive/Bureau/logo.png")));
            table.addCell(new Cell().add(new Paragraph("CNE")));
            table.addCell(new Cell().add(new Paragraph("Nom")));
            table.addCell(new Cell().add(new Paragraph("Note")));
            table.addCell(new Cell().add(new Paragraph("Resultat")));
            for (SemestreResult note :notes) {
                //table.addCell(new Cell().add(new Paragraph(note.getCne() +"")));
                table.addCell(new Cell().add(new Paragraph(note.getEtudiant().getCne())));
                table.addCell(new Cell().add(new Paragraph(note.getEtudiant().getNom()+" "+note.getEtudiant().getPrenom())));
                table.addCell(new Cell().add(new Paragraph(note.getNote()+"")));
                table.addCell(new Cell().add(new Paragraph(note.getNote()>10? "V" : "NV")));
            }
            doc.add(table);
            doc.add(new Paragraph("Responsable"));
            doc.add(new Paragraph("\n\n\n"));
            doc.add(new Paragraph("Professeur:"));
            doc.close();
            System.out.println("Table created successfully..");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
            return file;
	}
}
