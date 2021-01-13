package com.springboot.web;

import java.io.File;
import java.io.FileNotFoundException;

//import com.springboot.dao.NoteRepository;
//import com.springboot.entities.Note;

// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.time.format.DateTimeFormatter;
// import java.time.LocalDateTime;
// import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
// import java.util.Random;
import java.util.Map;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.springboot.dao.AnneResultRepository;
import com.springboot.dao.DelibirationModuleRepository;
import com.springboot.dao.ElementRepository;
import com.springboot.dao.EtablissementRepository;
import com.springboot.dao.EtapeRepository;
import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.FiliereRepository;

import java.time.LocalDateTime;
import com.springboot.dao.InscriptionPedagogiqueRepository;
import com.springboot.dao.ModuleRepository;
import com.springboot.dao.NoteRepository;
import com.springboot.dao.SemestreRepository;
import com.springboot.dao.SemestreResultRepository;
import com.springboot.entities.AnneeResult;
import com.springboot.entities.DelibirationElement;
import com.springboot.entities.DelibirationModule;
import com.springboot.entities.Element;
import com.springboot.entities.Etablissement;
import com.springboot.entities.Etape;
import com.springboot.entities.Etudiant;
import com.springboot.entities.Filiere;
import com.springboot.entities.InscriptionPedagogique;
import com.springboot.entities.Note;
import com.springboot.entities.Semestre;
import com.springboot.entities.SemestreResult;
import com.springboot.service.PdfGenerators;
import com.springboot.entities.Module;
//import com.springboot.entities.DelibirationSemestre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
   SemestreResultRepository semestreResultRepository;
   @Autowired
   EtudiantRepository etudiantRepository;
   @Autowired
   EtablissementRepository etablissementRepository;
   @Autowired
   FiliereRepository filiereRepository;
   @Autowired
   EtapeRepository etapeRepository;
   @Autowired
   AnneResultRepository anneResultRepository;

   @GetMapping("/selectEtablissement")
   public String selectEtab(Model model){
       List<Etablissement> list=etablissementRepository.findAll();
       model.addAttribute("etabs", list);
       return "choicesMenu/selectEtablissement";
   }
   @PostMapping("/getFiliere")
   public String getFiliere(Model model,
   @RequestParam(name = "etablissement")Long idEtab){
       List<Filiere> list=filiereRepository.findByEtablissement(etablissementRepository.getOne(idEtab));
       model.addAttribute("list", list);
       return "choicesMenu/selectFiliere";
   }
   @PostMapping("/getEtaps")
   public String getEtaps(Model model,
   @RequestParam(name = "filiere")Long idfiliere){
       Filiere f =filiereRepository.getOne(idfiliere);
       List<Etape>list=f.getEtapes(); 
       model.addAttribute("list", list);
       return "choicesMenu/selectEtapes";
   }
   @PostMapping("/getSemestres")
   public String getSemestre(Model model,
   @RequestParam(name = "etape")Long idEtaps){
       Etape etape =etapeRepository.getOne(idEtaps);
       List<Semestre>list=etape.getSemestres();
       model.addAttribute("list", list);
       return "choicesMenu/selectSemestre";
   }
   @PostMapping("/redirectDelibiration")
   public String redirectDelibiration(Model model,
   @RequestParam(name = "semestre")Long idSem){
       
       model.addAttribute("semestre", idSem);
       return "redirect:/delibirationSemestre";
   }
  
   @GetMapping("/delibirationModule")
   public String delibirationModule(Model model,
   @RequestParam(name="module",defaultValue = "1") Long idModule){

    try{
        //List<List<Element>> elements =new ArrayList<List<Element>>();
        List<DelibirationModule>delibirationModule=new ArrayList<DelibirationModule>();
        Map<DelibirationModule,ArrayList<DelibirationElement>> delib= new HashMap<DelibirationModule,ArrayList<DelibirationElement>>();
        //get l9raya informations
        double s=0,coeffs=0,s1=0,coeffs1=0,s2=0,coeffs2=0;
        ArrayList<String> cnes=new ArrayList<String>();
        List<Module>modules=new ArrayList<Module>();
        modules.add(moduleRepository.getOne(idModule));
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
                ArrayList<DelibirationElement>delibirationElement=new ArrayList<DelibirationElement>();
                s2=0;
                coeffs1=0;
                System.out.println("for cne=>"+cne +":");
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
                        System.out.println("\tfor note>type="+note.getExamen()+" value=>"+note.getNote());
                        if(temp.equals("EXAM")){
                            System.out.println("\t (if true)------.>"+note.getExamen().toString());
                            coeff=note.getCoefficient();
                            exams.add(note.getNote());
                        }
                        else{
                            s+=note.getNote()*note.getCoefficient();
                            coeffs+=note.getCoefficient();
                            System.out.println("\t(if false)------s==="+s+" coef==="+note.getCoefficient());
                        }
                        
                        
                    }

                    double max=0;
                    try{
                        System.out.println("_-----------arraydyali==="+exams.toString());
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
                System.out.println("pour>"+dm.getModule().getName()+",note="
                +dm.getNote()+"et elems");
                for(DelibirationElement d:delibirationElement){
                    System.out.println("pour cne="+cne+" : "+d.getElement().getName()+":"+d.getNote());
                }
                
        }
        }
        // for(DelibirationModule d:delib.){
            
        //     //System.out.println(d.toString());
        // }
        model.addAttribute("modules", delibirationModule);
        model.addAttribute("modules1", delib);
        model.addAttribute("title","delibiration module: "+modules.get(0).getName());
    }catch(Exception e){
        model.addAttribute("error",e.getMessage());
        e.printStackTrace();
        return "errors-500";
    }
    return "delibiration-table";
   }
   @GetMapping("/delibirationSemestre")
   public String delibirationSemestre(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "5") int size,
        @RequestParam(name="year",defaultValue="2020/2021") String year,
        @RequestParam(name="semestre",defaultValue="1") Long sem,
        @RequestParam(name="save",defaultValue="0") boolean save,
        @RequestParam(name="session",defaultValue="ordinaire") String session,
        @RequestParam(name = "filiere", defaultValue = "1") int filiere,
        @RequestParam(name="print" ,defaultValue = "false") boolean print) throws MalformedURLException {
            try{
                //List<List<Element>> elements =new ArrayList<List<Element>>();
                List<DelibirationModule>delibirationModule=new ArrayList<DelibirationModule>();
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
                        ArrayList<DelibirationElement>delibirationElement=new ArrayList<DelibirationElement>();
                        s2=0;
                        coeffs1=0;
                        //System.out.println("for cne=>"+cne +":");
                        DelibirationElement de = new DelibirationElement();
                        for(Element elem:elements){
                            
                            List<Note>notes =noteRepository.findByCneAndElementAndSession(etudiantRepository.getOne(cne), elem,session);
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
                        if(!Double.isNaN(dm.getNote())){
                            delib.put(dm, delibirationElement);
                        }
                        
                        System.out.println("notenull"+dm.getNote());
                        delibirationModule.add(dm);
                        if(save){
                            delibirationModuleRepository.save(dm);
                        }
                }
                
                }
                
                if(print){
                    File f=PdfGenerators.createPdf(delibirationModule ,semestre.getName());
                    System.out.println("[PDF]-> pdf generated and saved at:"+f.getAbsolutePath());
                }
                model.addAttribute("modules", delibirationModule);
                model.addAttribute("modules1", delib);
                model.addAttribute("title","delibiration semestre: "+semestre.getName());
            }catch(Exception e){
                model.addAttribute("error",e.getMessage());
                e.printStackTrace();
                return "errors-500";
            }

    return "delibiration-table";
   }
   
   @GetMapping("/delibirationDesSemestres")
   public String delibirationDesSemestres(Model model,
   @RequestParam(name="semestre",defaultValue = "1") Long id_semestre,
   @RequestParam(name="save",defaultValue = "0") boolean save,
   @RequestParam(name="print",defaultValue = "0") boolean print){

    Semestre semestre =semestreRepository.getOne(id_semestre);
    List<Module> modules =semestre.getModules();
    List<DelibirationModule>dm= new ArrayList<DelibirationModule>();
    List<SemestreResult>srList= new ArrayList<SemestreResult>();
    ArrayList<String>cnes =new ArrayList<String>();
    for(Module m:modules){
         List<DelibirationModule>tmp = delibirationModuleRepository.findByModule(m);
         System.out.println("tmpSize for module="+m.getName()+"="+tmp.size());
         dm.addAll(tmp);
    }
    System.out.println("modulesSize="+modules.size());
    System.out.println("delibSize="+dm.size());
    //just to get all cnes
    for(DelibirationModule d: dm){
        if(!cnes.contains(d.getEtudiant().getCne()))cnes.add(d.getEtudiant().getCne());
        System.out.println(d.getEtudiant().getCne()+"-----"+d.getModule().getName()+"---"+d.getNote());
    }
    //now to delebirate Semestres
    for(String cne :cnes){
        double s=0;
        double moyenneSemestre=0;
        double coef=0;
        for(DelibirationModule d:dm){
            if(d.getEtudiant()==etudiantRepository.getOne(cne)){
                s+=d.getNote()*d.getModule().getCoefficient();
                coef+=d.getModule().getCoefficient();
            }
        }
        moyenneSemestre=s/coef;
        moyenneSemestre=Double.parseDouble(String.format("%.2f", moyenneSemestre));
        SemestreResult sr =new SemestreResult(7l,semestre,etudiantRepository.getOne(cne),moyenneSemestre);
        srList.add(sr);
        if(save){
            semestreResultRepository.save(sr);
        }
        
        System.out.println("----->"+sr.getNote());
        //new entities d semestre
    }
    if(print){
        try {
            File f = PdfGenerators.createPdfSemestre(srList, semestre.getName());
            System.out.println("[PDF] file created and saved at: "+f.getAbsolutePath());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    model.addAttribute("list", srList);
    model.addAttribute("title","delibiration semestre: "+semestre.getName());
    return "delibiration-table-semestre";
   }
   @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public ModelAndView method(Model model,
    @RequestParam(name="module",defaultValue = "1")Long idModule) {
        Module module =moduleRepository.getOne(idModule);
       List<DelibirationModule>m=delibirationModuleRepository.findByModule(module);
       String projectUrl="hello";
       try {
           projectUrl=PdfGenerators.createPdf(m, module.getName()).getAbsolutePath();
       } catch (MalformedURLException e) {
           model.addAttribute("error", "file error->"+e.getMessage());
       }
        
        return new ModelAndView("redirect:" + projectUrl);
    }
   @GetMapping("/printModules")
   public String printModules(Model model,
   @RequestParam(name="module",defaultValue = "1")Long idModule){
       Module module =moduleRepository.getOne(idModule);
       List<DelibirationModule>m=delibirationModuleRepository.findByModule(module);
       try {
           File f=PdfGenerators.createPdf(m, module.getName());
           System.out.println(f.getAbsolutePath());
       } catch (MalformedURLException e) {
           model.addAttribute("error", "file error->"+e.getMessage());
       }
       return "errors-500";
   }
   
   @GetMapping("/delibirationAnnuelle")
   public String delibirationAnnuelle(Model model,
   @RequestParam(name = "idEtape",defaultValue = "1")Long idEtape,
   @RequestParam(name="print",defaultValue = "false")boolean print,
   @RequestParam(name="save",defaultValue = "false")boolean save) throws MalformedURLException {
    Etape etape =etapeRepository.getOne(idEtape);
    System.out.println(etape.getName());
    List<Semestre> semestres=semestreRepository.findByEtape(etape);
    for(Semestre s:semestres)System.out.println("semestres_>>"+s.getName());
    List<SemestreResult> delibirationSemestres=new ArrayList<SemestreResult>();
    List<AnneeResult> delibirationAnnee=new ArrayList<AnneeResult>();
    ArrayList<String>cnes= new ArrayList<String>();
    
    if(semestres.size()>2)System.err.println("[error]:there is more than 2 semestres for this \"Etaps\"");
    for(Semestre s:semestres){
        List<SemestreResult> sr=semestreResultRepository.findBySemestre(s);
        delibirationSemestres.addAll(sr);
       System.out.println("for1>>");
    }
    System.out.println("delibirationSize="+delibirationSemestres.size());
    for(SemestreResult sr:delibirationSemestres){
        if(!cnes.contains(sr.getEtudiant().getCne()))cnes.add(sr.getEtudiant().getCne());
    }
    //deleberate
    for(String cne:cnes){
        double moyenne=0;
        double sum=0;
        int i=1;
        Etudiant etudiant=etudiantRepository.getOne(cne);
        List<SemestreResult> temp=semestreResultRepository.findByEtudiant(etudiant);
        for(SemestreResult sr:temp){
            sum+=sr.getNote();
            System.out.println("pour le semestre >"+sr.getSemestre().getName()+",note est>"+sr.getNote());
            i++;
        } 
        if(i==2){
            moyenne=sum/i;
        }
        else if(i<2){
            System.out.println("[error]: donnees des semestre insufisantes!!!");
        }
        else{
            System.out.println("[error]: plus que 2 semestre on ete trouve pour cette etape!!!");
        }
        System.out.println("delin ann size-->"+delibirationSemestres.size());
        moyenne=sum/i;
        moyenne=Double.parseDouble(String.format("%.2f", moyenne));
        AnneeResult ar =new AnneeResult(1l,etape,etudiant,moyenne);
        delibirationAnnee.add(ar);
        if(save){
            anneResultRepository.save(ar);
        }
        if(print){
            File f= PdfGenerators.createPdfAnnee(delibirationAnnee,etape.getName());
            System.out.println("[PDF] file created and saved at: "+f.getAbsolutePath());
        }
        
    }
    model.addAttribute("list", delibirationAnnee);
    System.out.println(delibirationAnnee.size());
    return"delibiration-table-annee";
   }
}
