// package com.springboot.web;

// //import com.springboot.dao.NoteRepository;
// //import com.springboot.entities.Note;

// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
// import java.net.MalformedURLException;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Random;

// import com.itextpdf.io.image.ImageData;
// import com.itextpdf.io.image.ImageDataFactory;
// import com.itextpdf.kernel.pdf.PdfDocument;
// import com.itextpdf.kernel.pdf.PdfWriter;
// import com.itextpdf.layout.Document;
// import com.itextpdf.layout.element.Cell;
// import com.itextpdf.layout.element.IBlockElement;
// import com.itextpdf.layout.element.Image;
// import com.itextpdf.layout.element.Paragraph;
// import com.itextpdf.layout.element.Table;
// import com.springboot.dao.InscriptionAdministrativeRepository;
// import com.springboot.dao.InscriptionPedagogiqueRepository;
// import com.springboot.dao.ModuleRepository;
// import com.springboot.dao.NoteRepository;
// import com.springboot.dao.SemestreRepository;
// import com.springboot.entities.DelibirationSemestre;
// import com.springboot.entities.InscriptionPedagogique;
// import com.springboot.entities.Note;
// import com.springboot.entities.Semestre;
// import com.springboot.entities.Module;
// import com.springboot.entities.DelibirationSemestre;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// @org.springframework.stereotype.Controller
// public class DelibirationController {
//    public static final String REPOSITORY="C:/Users/hamza/delibiration";
//    @Autowired
//    NoteRepository noteRepository;
//    @Autowired
//    ModuleRepository moduleRepository;
//    @Autowired
//    InscriptionPedagogiqueRepository inscriptionPedagogiqueRepository;
//    @Autowired
//    SemestreRepository semestreRepository;
//    @GetMapping("/exportNotes")
//    public String exportNotes(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "5") int size,
//            @RequestParam(name = "module", defaultValue = "") String module) {
//     //    Page<Note> notes = noteRepository.findAll(PageRequest.of(page, size));
//     //    model.addAttribute("notes", notes);
//     //    System.out.println("size=" + notes.getContent().size());
//     //    File f = new File(REPOSITORY);
//     //    createPdf(f,notes.getContent());
//        return "/index-0";
//    }
//    @GetMapping("/test")
//    public String test(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "5") int size,
//            @RequestParam(name = "filiere", defaultValue = "5") int filiere,
//            @RequestParam(name = "semestre", defaultValue = "5") int semestre,
//            @RequestParam(name = "module", defaultValue = "bgi") String module) {
              
//        return "/NotesParModul";
//    }
//    @GetMapping("/resultatByetudiant")
//    public String resultatBySemestre(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "5") int size,
//            @RequestParam(name = "cne", defaultValue = "5") String cne) {
//             Page<Note> notes =noteRepository.findByCne(cne, PageRequest.of(page, size));
//             model.addAttribute("notes",notes);
//             model.addAttribute("title","list des note pour "+cne);
//        return "NotesParModul";
//    }
//    @GetMapping("/delibirationSemestre")
//    public String delibirationSemestre(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
//         @RequestParam(name = "size", defaultValue = "5") int size,
//         @RequestParam(name="year",defaultValue="2020/2021") String year,
//         @RequestParam(name="semestre",defaultValue="1") String semestre,
//         @RequestParam(name = "filiere", defaultValue = "1") int filiere,
//         @RequestParam(name="download" ,defaultValue = "false") boolean download) throws MalformedURLException {
//         // ArrayList<DelibirationSemestre> delibirations=new ArrayList<DelibirationSemestre>();
//         // Long id_filiere=Long.parseLong(filiere+"");
//         // Long id_semestre=Long.parseLong(semestre+"");
        
//         // System.out.println("filiere="+id_filiere+"  semestre=="+id_semestre);
//         // //ArrayList<Note> notes=new ArrayList<Note>();
//         // ArrayList<String>cnes=new ArrayList<String>();
//         // Semestre s=semestreRepository.findById_semestre(id_semestre);
//         // Page<InscriptionPedagogique> incs=inscriptionPedagogiqueRepository.
//         // findByid_moduleAndid_semestre(s, PageRequest.of(page, size));
//         // System.out.println("size2=="+incs.getNumberOfElements());
//         // for(InscriptionPedagogique i:incs){
//         //     System.out.println("f===="+i.getId_filiere());
//         //     if(i.getId_filiere()==id_filiere){
//         //         cnes.add(i.getCne());//list des cne des etudiant /etudie en semestre et filliere
//         //     }
//         // }
//         //     /////////////////
//         //     int i=0;
//         //     System.out.println("cnes size==== "+cnes.size());
//         //     for(String cne:cnes){
//         //         ///////////////todo get list modules li
//         //     List<Note>notes=noteRepository.findByCneAndYear(cne,2020, PageRequest.of(page, size)).toList();
//         //     System.out.println("size for cne="+cne+"--> "+notes.size());
//         //     boolean v=isValid(notes);
//         //     delibirations.add(new DelibirationSemestre(cne, s.getLibelle_semestre(),v));
//         //         ////////
//         //     if(download){
//         //         File file =createPdf(delibirations,delibirations.get(0).getSemestre());
//         //         model.addAttribute("path",file.getAbsolutePath());
//         //         System.out.println("path======="+file.getAbsolutePath());
//         //     }
//         //     }
//         //     /////////
//         //     model.addAttribute("delibirations",delibirations);
//         //     System.out.println("=======delibirationSize======"+delibirations.size());
//         //     for(DelibirationSemestre d: delibirations){
//         //         System.out.println(d.toString());
//         //     }
            
//         //what modules study any one of the selected students!!!!!!!!!!!!!!!!!!!!!!!!!!
//     return "delibirationSemestre";
//    }
   
//    //TODO complete "/resultatByetudiant" and try again to fix String moduleN
//    //Todo for next time show Notes by module,cne,year,semestre and semestre delibiration 
//    @GetMapping("/getNotesBySemestre")
//    public String getNotesBySemestre(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "5") int size,
//            @RequestParam(name = "filiere", defaultValue = "5") int filiere,
//            @RequestParam(name = "semestre", defaultValue = "5") int semestre,
//            @RequestParam(name = "module", defaultValue = "java") String module) {
//             //    Long id_filiere=Long.parseLong(filiere+"");
//             //    Long id_semestre=Long.parseLong(semestre+"");
//             //    System.out.println("filiere="+id_filiere+"  semestre=="+id_semestre);
//             //     ArrayList<Note> notes=new ArrayList<Note>();
//             //    ArrayList<String>cnes=new ArrayList<String>();
//             //    Semestre s=semestreRepository.findById_semestre(id_semestre);
//             //     Page<InscriptionPedagogique> incs=inscriptionPedagogiqueRepository.
//             //     findByid_moduleAndid_semestre(s, PageRequest.of(page, size));
//             //     System.out.println("size2=="+incs.getNumberOfElements());
//             //     for(InscriptionPedagogique i:incs){
//             //         System.out.println("f===="+i.getId_filiere());
//             //         if(i.getId_filiere()==id_filiere){
//             //             cnes.add(i.getCne());
//             //         }
//             //     }
//             //     for(String cne:cnes){
//             //         System.out.println("cne="+cne);
//             //         System.out.println("idmod===="+moduleRepository.getByLibellemodule(module).getId_module());
//             //         Page<Note> notest =noteRepository.findByCneAndId_module(cne, 
//             //         moduleRepository.getByLibellemodule(module).getId_module(), PageRequest.of(page, size));
//             //         for(Note n:notest){
//             //             System.out.println("last loop====="+n.getNote()+"   "+n.getCne());
//             //             notes.add(n);
//             //         }
//             //     }
//             //     model.addAttribute("notes", notes);
//             //     model.addAttribute("module", module);
//             //    System.out.println("size========="+notes.size());
//                // done!!!!!!!!!!!!!!!
//        return "NotesParSemestre";
//    }
//    @GetMapping("/getNotes")
//    public String getNotes(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "5") int size,
//            @RequestParam(name = "filiere", defaultValue = "5") int filiere,
//            @RequestParam(name = "semestre", defaultValue = "5") int semestre,
//            @RequestParam(name = "module", defaultValue = "1") String module) {
//                Long id_module=Long.parseLong(module);
//                //String moduleN=moduleRepository.findByid(id_module).getLibellemodule();
//                 Page<Note> notes = noteRepository.findByIdmodule(id_module,PageRequest.of(page, size));
//                 model.addAttribute("notes", notes);
//                 //model.addAttribute("title","liste des notes pour le module "+moduleN);
//                 System.out.println("size=" + notes.getContent().size());
//        return "NotesParModul";
//    }
//    public static File createPdf(List<DelibirationSemestre> notes,String semestre) throws MalformedURLException {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
//        LocalDateTime now = LocalDateTime.now();  
//        File file=null;
//        //TODO set horizontal logos and signatures 
//        try {
//            file =new File(REPOSITORY+"/delibiration_"+semestre.toUpperCase()+".pdf");
//            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(file));
//            Document doc = new Document(pdfDoc);
//            Table table = new Table(3);
//            table.setWidth(500);
//            table.setMargin(23);
//            doc.add(new Image(ImageDataFactory.create("https://www.linkpicture.com/q/external-content.duckduckgo.png")));
//            doc.add(new Paragraph("Le:"+dtf.format(now)+""));
//            //doc.add(new Image(ImageDataFactory.create("C:/Users/hamza/OneDrive/Bureau/logo.png")));
//            table.addCell(new Cell().add(new Paragraph("CNE")));
//            table.addCell(new Cell().add(new Paragraph("Semestre")));
//            table.addCell(new Cell().add(new Paragraph("Resultat")));
//            for (DelibirationSemestre note :notes) {
//                boolean n = note.isValid();
//                //table.addCell(new Cell().add(new Paragraph(note.getCne() +"")));
//                table.addCell(new Cell().add(new Paragraph(note.getCne())));
//                table.addCell(new Cell().add(new Paragraph(note.getSemestre())));
//                table.addCell(new Cell().add(new Paragraph(n ? "V" : "NV")));
//            }
//            doc.add(table);
//            doc.add(new Paragraph("Responsable"));
//            doc.add(new Paragraph("Professeur:"));
//            doc.close();
//            System.out.println("Table created successfully..");
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } 
//            return file;
    
//    }
//    public boolean isValid(List<Note>notes){
//     for(Note note:notes){
//         if(note.getNote()<10){
//             return false;
//         }
//     }
//     return true;
//    }
// }
