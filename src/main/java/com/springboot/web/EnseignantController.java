//package com.springboot.web;
//
//import java.util.List;
//
//import javax.validation.Valid;
//
//import com.springboot.dao.EtudiantRepository;
//import com.springboot.dao.FiliereRepository;
//import com.springboot.dao.InscriptionAdministrativeRepository;
//import com.springboot.dao.InscriptionEnligneRepository;
//import com.springboot.dao.InscriptionPedagogiqueRepository;
//import com.springboot.dao.ModuleRepository;
//import com.springboot.entities.Etape;
//import com.springboot.entities.Etudiant;
//import com.springboot.entities.Filiere;
//import com.springboot.entities.InscriptionAdministrative;
//import com.springboot.entities.InscriptionEnligne;
//import com.springboot.entities.InscriptionPedagogique;
//import com.springboot.entities.Module;
//import com.springboot.entities.Semestre;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//
//@org.springframework.stereotype.Controller
//public class EnseignantController {
//	@Autowired
//	InscriptionEnligneRepository inscriptionEnligneRepository;
//	@Autowired
//	InscriptionAdministrativeRepository inscriptionAdministrativeRepository;
//	@Autowired
//	EtudiantRepository etudiantRepository;
//	@Autowired
//	FiliereRepository filiereRepository;
//
//	@Autowired
//	com.springboot.dao.ModuleRepository moduleRepository;
//	@Autowired
//	com.springboot.dao.EtapeRepository etapeRepository;
//	@Autowired
//	com.springboot.dao.SemestreRepository semestreRepository;
//
//	@Autowired
//	com.springboot.dao.InscriptionPedagogiqueRepository inscriptionPedagogiqueRepository;
//
//
//	//1.Structure d'enseignants
//	
//
//	//Supprission etape , semestre ,module
//	@GetMapping(path="/deleteEtape")
//	public String deleteEtape(Long id ) {
//		etapeRepository.deleteById(id);
//		return "redirect:/formEtape";
//	}
//	@GetMapping(path="/deleteSemestre")
//	public String deleteSemstre(Long id ) {
//		semestreRepository.deleteById(id);
//		return "redirect:/formEtape";
//	}
//	@GetMapping(path="/deleteModule")
//	public String deleteModule(Long id ) {
//		moduleRepository.deleteById(id);
//		return "redirect:/formEtape";
//	}
//
//	// 4.Inscription Pedagogique ,affecter filiere a l'etape
//
//	@GetMapping(path="/formEtape")
//	public String formEtape(Model model) {
//		Etape etape = new Etape();
//		model.addAttribute("etape", etape);
//		List<Etape> etapes = etapeRepository.findAll();
//		model.addAttribute("etapes", etapes);
//
//		Semestre semestre = new Semestre();
//		model.addAttribute("semestre", semestre);
//		List<Semestre> semestres = semestreRepository.findAll();
//		model.addAttribute("semestres", semestres);
//		Module module = new Module();
//		model.addAttribute("module", module);
//		List<Module> modules = moduleRepository.findAll();
//		model.addAttribute("modules", modules);
//
//		return "formEtape";
//	}
//	//Validation etape V2
//	@RequestMapping(path="/saveEtape" , method = RequestMethod.POST)
//	public String saveEtape(Model model,@Valid Etape  etape, @RequestParam("filiere")Long filiere,BindingResult bindingResult){
//		if(bindingResult.hasErrors()) return "formEtape";
//
//		Filiere f = filiereRepository.findById(filiere).get();
//		f.setId_filiere(filiere);
//		etape.setFiliere(f);
//
//		etapeRepository.save(etape);
//		model.addAttribute("etape", etape);
//		model.addAttribute("filiere", filiere);
//
//		return "redirect:/etapes";
//	}
//	@RequestMapping(path="/saveSemestre" , method = RequestMethod.POST) public
//	String saveEtape(Model model,@Valid Semestre
//			semestre,@RequestParam("id_etape")String id ,BindingResult bindingResult){
//		if(bindingResult.hasErrors()) return "formEtape";
//
//		Long id_etape = etapeRepository.findId_etapeByLibelle_etape(id);
//		Etape etape = etapeRepository.findById(id_etape).get(); 
//		etape.setId_etape(id_etape);
//		semestre.setEtape(etape);
//
//		semestreRepository.save(semestre); 
//		model.addAttribute("semestre", semestre);
//
//		model.addAttribute("id_etape", id);
//
//		return "redirect:/etapes";
//	}
//	@RequestMapping(path="/saveModule" , method = RequestMethod.POST) public
//	String saveEtape(Model model,@Valid Module module
//			,@RequestParam("id_semestre")String id ,BindingResult bindingResult){
//		if(bindingResult.hasErrors()) return "formEtape";
//
//		Long id_semetre = semestreRepository.findId_moduleByLibelle_semestre(id);
//		Semestre semestre = semestreRepository.findById(id_semetre).get();
//		semestre.setId_semestre(id_semetre);
//		module.setSemestre(semestre);
//
//		moduleRepository.save(module);
//		model.addAttribute("module", module);
//
//		model.addAttribute("id_semestre", id);
//
//		return "redirect:/etapes";
//	}
//	@GetMapping(path="/etapes") 
//	public String listEtapes(Model model
//			) {
//		List<Etape> etapes = etapeRepository.findAll();
//		model.addAttribute("etapes", etapes);
//
//		List<Semestre> semestres = semestreRepository.findAll();
//		model.addAttribute("semestres", semestres);
//
//		List<Module> modules = moduleRepository.findAll();
//		model.addAttribute("modules", modules);
//
//		return "redirect:/formEtape";
//	}
//
//
//
//	//select Filiere,etape,ajout semestre
//
//
//	@GetMapping(path="/selectFiliere") 
//	public String selectFiliere(Model model ,
//			@RequestParam(name="page",defaultValue = "0")int page ,
//			@RequestParam(name="size",defaultValue = "5")int size , 
//			@RequestParam(name="filiere",defaultValue = "")String filiere
//			//@RequestParam(name="annee",defaultValue = "")String annee
//
//			) {
//		Long id_filiere = filiereRepository.findId_filiereByNome_filiere(filiere);
//		Page<Etudiant> pageEtudiant = etudiantRepository.findById_filiere(id_filiere, PageRequest.of(page, size));
//		//Page<Etudiant> pageEtudiant = etudiantRepository.findById_filiereAndAnnee_academique(id_filiere,annee, PageRequest.of(page, size));
//		//Page<Etudiant> pageEtudiant = etudiantRepository.findByAnnee_academique(annee, PageRequest.of(page, size));
//		List<Filiere> f = filiereRepository.findAll();
//		model.addAttribute("selectFiliere",pageEtudiant.getContent());
//		model.addAttribute("pages",new int[pageEtudiant.getTotalPages()]);
//		model.addAttribute("currentPage",page);
//		model.addAttribute("filiere",f);
//		//model.addAttribute("annee",annee);
//
//		model.addAttribute("size",size);
//		return "listeSemestre";
//	}
//
//
//	//Affichage avec pagination :tous
//	@GetMapping(path = "/etudiantsParFiliere")
//	public String etudiantsParFiliere(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
//			@RequestParam(name = "size", defaultValue = "5") int size,
//			@RequestParam(name = "filiere", defaultValue = "") String filiere) {
//		Page<Etudiant> pageEtudiants = etudiantRepository.findAll(PageRequest.of(page, size));
//		model.addAttribute("etudiants", pageEtudiants.getContent());
//		model.addAttribute("pages", new int[pageEtudiants.getTotalPages()]);
//		model.addAttribute("currentPage", page);
//		model.addAttribute("filiere", filiere);
//		model.addAttribute("size", size);
//		return "listeSemestre";
//	}
//
//	@Autowired
//	InscriptionPedagogiqueRepository pedagogiqueRepository;
//
//	@GetMapping(path="/ajouterParSemestre")
//	public String ajouterParSemestre(Model model 
//			//@RequestParam(name = "cne" , defaultValue = "")String cne 
//			//@RequestParam(name = "filiere" , defaultValue = "")String filiere
//			) {
//
//		InscriptionPedagogique admin = new InscriptionPedagogique();
//		model.addAttribute("admin", admin);
////		Etudiant e = etudiantRepository.getOne(cne);
////		e.setCne(cne);
////		model.addAttribute("cne", cne);
//		model.addAttribute("mode", "new");
//
//		return "ConfirmationSemestre";
//	}
//
//	@RequestMapping(path="/saveAjoutSemestre" , method = RequestMethod.POST)
//	public String saveAjoutSemestre(Model model ,
//			@RequestParam(name="cne")String cne,
//			@RequestParam(name="filiere")Long filiere,
//			@RequestParam(name="semestre")String semestre,
//
//			@Valid InscriptionPedagogique admin,
//			BindingResult bindingResult){
//		if(bindingResult.hasErrors()) return "ConfirmationSemestre";
//
//		Etudiant etudiant = etudiantRepository.getOne(cne);
//		model.addAttribute("etudiant", cne);
//		etudiant.setCne(cne);
//		admin.setEtudiant(etudiant);
//
//		Filiere f = filiereRepository.findById(filiere).get();
//		f.setId_filiere(filiere);
//		admin.setFiliere(f);
//
//
//		Long id = semestreRepository.findId_moduleByLibelle_semestre(semestre);
//		Semestre s = semestreRepository.findById(id).get();
//		s.setId_semestre(id);
//		admin.setSemestre(s);
//
//
//		inscriptionPedagogiqueRepository.save(admin);
//		model.addAttribute("admin", admin);
//		model.addAttribute("cne", cne);
//		model.addAttribute("filiere", f);
//		model.addAttribute("semestre", s);
//
//		return "listeSemestre";
//	}
//
//	
//}
//
