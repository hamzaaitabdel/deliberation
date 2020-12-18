package com.springboot.web;

//import com.springboot.dao.NoteRepository;
//import com.springboot.entities.Note;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class DelibirationController {
//    public final String REPOSITORY="C:/Users/hamza/OneDrive/Bureau/testb.pdf";
//    @Autowired
//    NoteRepository noteRepository;
//
//    @GetMapping("/exportNotes")
//    public String exportNotes(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "5") int size,
//            @RequestParam(name = "module", defaultValue = "") String module) {
//        Page<Note> notes = noteRepository.findAll(PageRequest.of(page, size));
//        model.addAttribute("notes", notes);
//        System.out.println("size=" + notes.getContent().size());
//        File f = new File(REPOSITORY);
//        createPdf(f,notes.getContent());
//        return "/index-0";
//    }
//
//    public static void createPdf(File file,List<Note> notes) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
//        LocalDateTime now = LocalDateTime.now();  
//        //TODO set horizontal logos and signatures 
//        try {
//            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
//            Document doc = new Document(pdfDoc);
//            Table table = new Table(3);
//            table.setWidth(500);
//            table.setMargin(23);
//            doc.add(new Image(ImageDataFactory.create("C:/Users/hamza/OneDrive/Bureau/logo.png")));
//            doc.add(new Paragraph("Le:"+dtf.format(now)+""));
//            //doc.add(new Image(ImageDataFactory.create("C:/Users/hamza/OneDrive/Bureau/logo.png")));
//            table.addCell(new Cell().add(new Paragraph("CNE")));
//            table.addCell(new Cell().add(new Paragraph("Note")));
//            table.addCell(new Cell().add(new Paragraph("Resultat")));
//            for (Note note :notes) {
//                double n = note.getNote();
//                //table.addCell(new Cell().add(new Paragraph(note.getCne() +"")));
//                table.addCell(new Cell().add(new Paragraph(note.getEtudiant().getCne()+ "")));
//                table.addCell(new Cell().add(new Paragraph(n + "")));
//                table.addCell(new Cell().add(new Paragraph(n >= 10 ? "V" : "NV")));
//            }
//            doc.add(table);
//            doc.add(new Paragraph("Responsable"));
//            doc.add(new Paragraph("Professeur:"));
//            doc.close();
//            System.out.println("Table created successfully..");
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (MalformedURLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
