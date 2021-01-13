package com.springboot.web;

//import com.springboot.dao.NoteRepository;
//import com.springboot.entities.Note;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.itextpdf.io.IOException;
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
import com.springboot.dao.AnneUniversitaireRepository;
import com.springboot.dao.ElementRepository;
import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.NoteRepository;
import com.springboot.dao.ProfesseurRepository;
import com.springboot.entities.AnneeUniversitaire;
import com.springboot.entities.Element;
import com.springboot.entities.Etudiant;
import com.springboot.entities.Filiere;
import com.springboot.entities.InscriptionPedagogique;
import com.springboot.entities.Note;
import com.springboot.entities.Note.Examen;
import com.springboot.entities.Professeur;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import aj.org.objectweb.asm.Type;

@org.springframework.stereotype.Controller
public class DelibirationController2 {
    @Autowired
    ProfesseurRepository Prof_rep;
    @Autowired
    EtudiantRepository Etud_rep;
    @Autowired
    ElementRepository Elem_rep;

    @Autowired
    AnneUniversitaireRepository anneUniversitaireRep;
    @Autowired
    NoteRepository noteRep;

    @RequestMapping(path = "/import", method = RequestMethod.POST)
    public String mapReapExcelDatatoDB(@RequestParam("file") MultipartFile reapExcelDataFile,@RequestParam("session")String session ,@RequestParam("coef")String coefe ,@RequestParam("Element")String eleme , Model model,
         @RequestParam("Type")String Typen,   HttpServletRequest request) throws IOException {
        
         Long elem = Long.parseLong(eleme);// 
            
      
       AnneeUniversitaire ann=new AnneeUniversitaire();
        List <AnneeUniversitaire> Annes = anneUniversitaireRep.findAll();
            for (AnneeUniversitaire a : Annes) {
                if(a.getAnnee().equals(getAnne())){
                    ann= anneUniversitaireRep.getOne(a.getId());
                }
            }
        
        System.out.println("haaaaaayloooookiiiiiiiii:"+session+Typen);
        double coef = Double.parseDouble(coefe); 
        Element el= Elem_rep.getOne(elem);
        
       
        ArrayList<Note> noteList = new ArrayList<Note>();
        XSSFWorkbook workbook = new XSSFWorkbook();
        
        
        try {
            workbook = new XSSFWorkbook(reapExcelDataFile.getInputStream());
        } catch (java.io.IOException e) {
            // TODO Auto-generated catch block
            System.out.println("here gibt es problemmmmme .......  file incorectos");
            e.printStackTrace();
        }

        XSSFSheet worksheet = workbook.getSheetAt(0);
        com.springboot.entities.Module m;

        double X = el.getNoteValidation();
        double Z = el.getNoteEliminatoire();
       
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            Note n = new Note();
            n.setCoefficient(coef);
            n.setAnneeUniversitaire(ann);
            XSSFRow row = worksheet.getRow(i);
            try{
            if (row.getCell(2).getStringCellValue().equals("abs")){
                n.setNote((double)0);
            }
        }
        catch(IllegalStateException e){
     n.setNote((Double) row.getCell(2).getNumericCellValue());
        }

            

            n.setEtudiant(Etud_rep.getOne(row.getCell(1).getStringCellValue())); 
            n.setExamen(Examen.valueOf(Typen));
            Double nt = n.getNote();
            
            n.setElement(el);
//  cas d'exame :  tester si il s'agit d'un abcent
            if (Typen.equals(Examen.EXAM.toString()) ) {
                n.setSession(session);
                try{
                    if (row.getCell(2).getStringCellValue().equals("abs")){
                        n.setEtats("nonvalidé");
                    }
                }
                catch(IllegalStateException e){
             n.setNote((Double) row.getCell(2).getNumericCellValue());
                
                
               


          System.out.println("here is 1");
            List<Note>notes =noteRep.findByCneAndElement(n.getEtudiant(), el);

           
            ArrayList<Double> exams=new ArrayList<Double>();
            System.out.println("here is 3");
            exams.clear();
            System.out.println("here is 4");
            double coeff=0;
            double s=0;
            System.out.println("here is 5");
            double coeffs=0;
            
            
            System.out.println("heeeere is still ");
            for(Note note :notes){
               
                String temp=note.getExamen().toString();
               
                if(temp.equals("EXAM")){
                    System.out.println("\t (if true)------.>"+note.getExamen().toString());
                    coeff=note.getCoefficient();
                    exams.add(n.getNote());
                    exams.add(note.getNote());
                }
                else{
                    s+=note.getNote()*note.getCoefficient();
                    coeffs+=note.getCoefficient();
                    System.out.println("\t(if true)------s==="+s+" coef==="+note.getCoefficient());
                }
                
                
            }

            double max=0;
            try{
                System.out.println("_-----------arraydyali==="+exams.toString());
                max=Collections.max(exams);
                System.out.println("le max est : "+max);
                System.out.println("coef"+ coeff);
            }catch(Exception ex){
                System.err.println("[error]:==>database");
                max=n.getNote();
                coeff=n.getCoefficient();
                System.out.println("max : "+ max);
                System.out.println("coeff ? "+coeff);
               
            }
            double moyenneElem=(s+(max*coeff))/(coeffs+coeff);
            
            System.out.println("moyenne element est : "+ moyenneElem);

            if(moyenneElem<Z){
                System.out.println("nonvalidé ? "+ Z);
            n.setEtats("nonvalidé");
            }
       else if(moyenneElem<X && moyenneElem>=Z){
                if(session.equals("Ordinaire"))
                n.setEtats("Ratrapage");
                else {
                n.setEtats("nonvalidé");
                }
            }
            else{
                n.setEtats("validé"); 
            }
        }
    }

        else{
            n.setSession("...");
            n.setEtats("pas");
        }
            noteList.add(n); 
            noteRep.save(n);
        }
       
        List list_note= noteRep.getNoteByElement(el);

        model.addAttribute("Lnots", noteList);
        
        System.out.println("seccesss");
       
             return "selectionerEtapes"; 
        }


    //////select element
    @RequestMapping(path="/select_element",method = RequestMethod.GET)
    public String importation1(Model model, HttpServletRequest request){
    Professeur P=new Professeur();
    Long a = (long) 1;
    P=Prof_rep.getOne(a);
    List <Element> elements = P.getElements();

    System.out.println("haaaaaaaniiiiiiii");
    
    
    model.addAttribute("Elements", elements);
        return "selectmodyle";
    }
    
    @RequestMapping(path="/modifier",method = RequestMethod.GET)
    public String select(@RequestParam("id") String idS,Model model, HttpServletRequest request){
        long id = Long.parseLong(idS);
        Note n = noteRep.getOne(id);
        Professeur P=new Professeur();
        double note = n.getNote();
        model.addAttribute("Note", n);
    
        return "selectionerfil";
    }



    @RequestMapping(path="/confirmer",method = RequestMethod.POST)
    public String modifier(@RequestParam("id") String ids,@RequestParam("note") String noteS,Model model, HttpServletRequest request){
        long id = Long.parseLong(ids);
        Note n = noteRep.getOne(id);
        double noteD = Double.parseDouble(noteS);
        n.setNote(noteD);

        double X = n.getElement().getNoteValidation();
        double Z = n.getElement().getNoteEliminatoire();

        if (n.getExamen().EXAM.toString().equals(Examen.EXAM.toString()) ) {
            //etas not so fast : 
           System.out.println("here is 1");
             List<Note>notes =noteRep.findByCneAndElement(n.getEtudiant(), n.getElement());
 
            
             ArrayList<Double> exams=new ArrayList<Double>();
             System.out.println("here is 3");
             exams.clear();
             System.out.println("here is 4");
             double coeff=0;
             double s=0;
             System.out.println("here is 5");
             double coeffs=0;
             
             
             System.out.println("heeeere is still ");
             for(Note note :notes){
                
                 String temp=note.getExamen().toString();
                
                 if(temp.equals("EXAM")){
                     System.out.println("\t (if true)------.>"+note.getExamen().toString());
                     coeff=note.getCoefficient();
                     exams.add(n.getNote());
                     exams.add(note.getNote());
                 }
                 else{
                     s+=note.getNote()*note.getCoefficient();
                     coeffs+=note.getCoefficient();
                     System.out.println("\t(if true)------s==="+s+" coef==="+note.getCoefficient());
                 }
                 
                 
             }
 
             double max=0;
             try{
                 System.out.println("_-----------arraydyali==="+exams.toString());
                 max=Collections.max(exams);
                 System.out.println("le max est : "+max);
                 System.out.println("coef"+ coeff);
             }catch(Exception e){
                 System.err.println("[error]:==>database");
                 max=n.getNote();
                 coeff=n.getCoefficient();
                 System.out.println("max : "+ max);
                 System.out.println("coeff ? "+coeff);
                
             }
             double moyenneElem=(s+(max*coeff))/(coeffs+coeff);
             
             System.out.println("moyenne element est : "+ moyenneElem);
 
             if(moyenneElem<Z){
                 System.out.println("nonvalidé ? "+ Z);
             n.setEtats("nonvalidé");
             }
        else if(moyenneElem<X && moyenneElem>=Z){
                 if(n.getSession().equals("Ordinaire"))
                 n.setEtats("Ratrapage");
                 else {
                 n.setEtats("nonvalidé");
                 }
             }
             else{
                 n.setEtats("validé"); 
             }
         }
 
        
           
         
        noteRep.save(n);

        return "";//home
    }




    @RequestMapping(path="/modifiercomplet",method = RequestMethod.GET)
    public String modify(@RequestParam("id") String idS,Model model, HttpServletRequest request){
        
        long id = Long.parseLong(idS);
        Note n = noteRep.getOne(id);
        Professeur P=new Professeur();
        P=Prof_rep.getOne((long)(1));
        List <Element> elements = P.getElements();

        double note = n.getNote();
        model.addAttribute("Note", n);
        return "selectionerfil";

    }
    @GetMapping("/noteModules")
    public String noteModule(Model model,
    @RequestParam(name = "idElement",defaultValue = "1")Long id){
        Long idProf=1l;
        Professeur p=Prof_rep.getOne(idProf);
        List<Element>list=p.getElements();
        List<Note> notes=noteRep.getNoteByElement(Elem_rep.getOne(id));
        System.out.println("size="+notes.size());
        model.addAttribute("notes",notes);
        model.addAttribute("list", list);
        return "ListModules";
    }



    public String getAnne(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int mounth = Calendar.getInstance().get(Calendar.MONTH);
        String y = Integer.toString(year);
        if (mounth > 7){
            int yearp = year+1; 
            
            String yp = Integer.toString(yearp);
            y= y+"/"+yp;            
        }
        else{
            int yearp = year-1; 
           
            String yn = Integer.toString(yearp);
            y= yn+"/"+y; 
        }
   
    
        return y;
    }






}