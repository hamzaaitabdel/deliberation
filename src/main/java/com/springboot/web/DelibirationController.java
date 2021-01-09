package com.springboot.web;

//import com.springboot.dao.NoteRepository;
//import com.springboot.entities.Note;

// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
import java.net.MalformedURLException;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
// import java.util.Random;
import java.util.Map;

import com.springboot.dao.DelibirationModuleRepository;
import com.springboot.dao.ElementRepository;
import com.springboot.dao.EtudiantRepository;
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
// import com.springboot.dao.EtudiantRepository;
// import com.springboot.dao.InscriptionAdministrativeRepository;
import com.springboot.dao.InscriptionPedagogiqueRepository;
import com.springboot.dao.ModuleRepository;
import com.springboot.dao.NoteRepository;
import com.springboot.dao.SemestreRepository;
import com.springboot.entities.DelibirationElement;
import com.springboot.entities.DelibirationModule;
import com.springboot.entities.DelibirationSemestre;
import com.springboot.entities.Element;
import com.springboot.entities.Etudiant;
import com.springboot.entities.InscriptionPedagogique;
import com.springboot.entities.Note;
import com.springboot.entities.Semestre;
import com.springboot.entities.Module;
//import com.springboot.entities.DelibirationSemestre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
public class DelibirationController {
   public static final String REPOSITORY="C:/Users/hamza/delibiration";
   @Autowired
   NoteRepository noteRepository;
   @Autowired
   ModuleRepository moduleRepository;
   @Autowired
   InscriptionPedagogiqueRepository inscriptionPedagogiqueRepository;
   @Autowired
   SemestreRepository semestreRepository;
   @Autowired
   ElementRepository elementRepository;
   @Autowired
   DelibirationModuleRepository delibirationModuleRepository;
   @Autowired
   EtudiantRepository etudiantRepository;

   @GetMapping("/moduleDetails")
   public String moduleDetails(Model model ,
   @RequestParam(name= "id_module", defaultValue="1") Long id_module,
   @RequestParam(name="cne",defaultValue="010101") String cne){
       Module module=moduleRepository.getOne(id_module);
       Etudiant etudiant =etudiantRepository.getOne(cne);
       ArrayList<Note>notes=new ArrayList<Note>();
       List<Element> elements=elementRepository.elements(module);
        for(Element e: elements){
            List<Note> note=noteRepository.findByCneAndElement(etudiant,e);
            notes.addAll(note);
        }
        model.addAttribute("notes", notes);
       return "moduleDetails";
   }
   @GetMapping("/delibirationSemestre")
   public String delibirationSemestre(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "5") int size,
        @RequestParam(name="year",defaultValue="2020/2021") String year,
        @RequestParam(name="semestre",defaultValue="1") Long sem,
        @RequestParam(name = "filiere", defaultValue = "1") int filiere,
        @RequestParam(name="download" ,defaultValue = "false") boolean download) throws MalformedURLException {

            try{
                //List<List<Element>> elements =new ArrayList<List<Element>>();
                List<DelibirationModule>delibirationModule=new ArrayList<DelibirationModule>();
                ArrayList<DelibirationElement>delibirationElement=new ArrayList<DelibirationElement>();
                Map<DelibirationModule,ArrayList<DelibirationElement>> delib= new HashMap<DelibirationModule,ArrayList<DelibirationElement>>();
                //get l9raya informations
                double s=0,coeffs=0,s1=0,coeffs1=0,s2=0,coeffs2=0;
                ArrayList<String> cnes=new ArrayList<String>();
                Semestre semestre =semestreRepository.findById_semestre(sem);
                List<Module>modules=semestre.getModules();
                //here i have modules for requested Semestre
                for(Module module :modules){
                    List<Element>elements=elementRepository.elements(module);
                    for(Element elem:elements){
                        List<Note>notes =noteRepository.getNoteByElement(elem);
                        for(Note note:notes){
                            if(!cnes.contains(note.getEtudiant().getCne())){
                                cnes.add(note.getEtudiant().getCne());
                            }
                        }
                    }
                    for(String cne:cnes){
                        s2=0;
                        coeffs1=0;
                        //System.out.println("for cne=>"+cne +":");
                        DelibirationElement de = new DelibirationElement();
                        for(Element elem:elements){
                            List<Note>notes =noteRepository.findByCneAndElement(etudiantRepository.getOne(cne), elem);
                            ArrayList<Double> exams=new ArrayList<Double>();
                            exams.clear();
                            double coeff=0;
                            s=0;
                            coeffs=0;
                            for(Note note :notes){
                                String temp=note.getExamen().toString();
                                //System.out.println("\tfor note>type="+note.getExamen()+" value=>"+note.getNote());
                                if(temp.equals("EXAM")){
                                    //System.out.println("\t (if true)------.>"+note.getExamen().toString());
                                    coeff=note.getCoefficient();
                                    exams.add(note.getNote());
                                }
                                else{
                                    s+=note.getNote()*note.getCoefficient();
                                    coeffs+=note.getCoefficient();
                                    //System.out.println("\t(if true)------s==="+s+" coef==="+note.getCoefficient());
                                }
                                
                                
                            }

                            double max=0;
                            try{
                                //System.out.println("_-----------arraydyali==="+exams.toString());
                                max=Collections.max(exams);
                            }catch(Exception e){
                                System.err.println("[error]:"+e.getMessage());
                            }
                            
                            
                            double moyenneElem=(s+(max*coeff))/(coeffs+coeff);
                            moyenneElem=Double.parseDouble(String.format("%.2f", moyenneElem));
                            de = new DelibirationElement(3l,elem,etudiantRepository.getOne(cne),moyenneElem);
                            delibirationElement.add(de);
                            //System.out.println("affElem=>"+elem.getNom()+" coef> "+elem.getCoefficient()+" note> "+moyenneElem);
                            s2+=moyenneElem*elem.getCoefficient();
                            //System.out.println("s2==="+s2);
                            coeffs1+=elem.getCoefficient();
                        }
                        double moyenneModule=s2/coeffs1;
                        moyenneModule=Double.parseDouble(String.format("%.2f", moyenneModule));
                        DelibirationModule dm = new DelibirationModule(module,moyenneModule,etudiantRepository.getOne(cne));
                        delib.put(dm, delibirationElement);
                        delibirationModuleRepository.save(dm);
                        delibirationModule.add(dm);
                }
                
                }
                List<HashMap<Integer,Double>> details =new ArrayList<HashMap<Integer,Double>>();
                // for(DelibirationModule d:delibirationModule){
                //     Map<Integer,Double> map =new HashMap<Integer,Double>();
                //     //System.out.println(d.toString());
                // }
                model.addAttribute("modules", delibirationModule);
                model.addAttribute("modules1", delib);
                
            }catch(Exception e){
                model.addAttribute("error",e.getMessage());
                e.printStackTrace();
                return "errors-500";
            }
    return "delibiration-table";
   }
   
   
}
