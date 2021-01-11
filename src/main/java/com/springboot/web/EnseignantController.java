package com.springboot.web;

import java.util.List;

import javax.validation.Valid;

import com.springboot.dao.ElementRepository;
import com.springboot.dao.EtablissementRepository;
import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.FiliereRepository;
import com.springboot.dao.InscriptionAdministrativeRepository;
import com.springboot.dao.InscriptionEnligneRepository;
import com.springboot.dao.InscriptionPedagogiqueRepository;
import com.springboot.dao.ModuleRepository;
import com.springboot.dao.ProfesseurRepository;
import com.springboot.entities.Element;
import com.springboot.entities.Etablissement;
import com.springboot.entities.Etape;
import com.springboot.entities.Etudiant;
import com.springboot.entities.Filiere;
import com.springboot.entities.InscriptionAdministrative;
import com.springboot.entities.InscriptionEnligne;
import com.springboot.entities.InscriptionPedagogique;
import com.springboot.entities.Module;
import com.springboot.entities.Professeur;
import com.springboot.entities.Semestre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@org.springframework.stereotype.Controller
public class EnseignantController {
	@Autowired
	InscriptionEnligneRepository inscriptionEnligneRepository;
	@Autowired
	InscriptionAdministrativeRepository inscriptionAdministrativeRepository;
	@Autowired
	EtudiantRepository etudiantRepository;
	@Autowired
	FiliereRepository filiereRepository;

	@Autowired
	EtablissementRepository etablissementRepository;
	
	@Autowired
	ElementRepository elementRepository;
	
	@Autowired
	ProfesseurRepository professeurRepository;
	
	
	@Autowired
	com.springboot.dao.ModuleRepository moduleRepository;
	@Autowired
	com.springboot.dao.EtapeRepository etapeRepository;
	@Autowired
	com.springboot.dao.SemestreRepository semestreRepository;

	@Autowired
	com.springboot.dao.InscriptionPedagogiqueRepository inscriptionPedagogiqueRepository;


	//1.Structure d'enseignants
	

	//Supprission filiere,etape , semestre ,module,element
	
	@GetMapping(path="/deleteFiliere")
	public String deleteFiliere(Long id , int page , int size) {
		filiereRepository.deleteById(id);
		return "redirect:/filieres?page="+page+"&size="+size;
	}
	
	@GetMapping(path="/deleteEtape")
	public String deleteEtape(Long id , int page , int size) {
		etapeRepository.deleteById(id);
		return "redirect:/etapes?page="+page+"&size="+size;
	}
	@GetMapping(path="/deleteSemestre")
	public String deleteSemestre(Long id , int page , int size) {
		semestreRepository.deleteById(id);
		return "redirect:/semestres?page="+page+"&size="+size;
	}
	@GetMapping(path="/deleteModule")
	public String deleteModule(Long id , int page , int size) {
		moduleRepository.deleteById(id);
		return "redirect:/modules?page="+page+"&size="+size;
	}
	@GetMapping(path="/deleteElement")
	public String deleteElement(Long id , int page , int size) {
		elementRepository.deleteById(id);
		return "redirect:/elements?page="+page+"&size="+size;
	}

	// 4.Inscription Pedagogique ,affecter filiere a l'etape

	@GetMapping(path="/formEtape")
	public String formEtape(Model model) {
		model.addAttribute("etape", new Etape());
		return "formEtape";
	}
	@GetMapping(path="/formFiliere")
	public String formFiliere(Model model) {
		model.addAttribute("filiere", new Filiere());
		return "formFiliere";
	}
	@GetMapping(path="/formSemestre")
	public String formSemestre(Model model) {
		model.addAttribute("semestre", new Semestre());
		return "formSemestre";
	}
	@GetMapping(path="/formModule")
	public String formModule(Model model) {
		model.addAttribute("module", new Module());
		return "formModule";
	}
	@GetMapping(path="/formElement")
	public String formElement(Model model) {
		model.addAttribute("element", new Element());
		return "formElement";
	}
	
	@RequestMapping(path="/saveFiliere" , method = RequestMethod.POST)
	public String saveFiliere(Model model,
			@Valid Filiere  filiere,
			@RequestParam("etablissement")Long etablissement,
			BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formFiliere";

		Etablissement f = etablissementRepository.findById(etablissement).get();
		f.setId(etablissement);
		filiere.setEtablissement(f);

		filiereRepository.save(filiere);
		model.addAttribute("filiere", filiere);
		model.addAttribute("etablissement", etablissement);

		return "redirect:/filieres";
	}
	
	
	@RequestMapping(path="/saveEtape" , method = RequestMethod.POST)
	public String saveEtape(Model model,
			@Valid Etape  etape,
			@RequestParam("filiere")Long filiere,
			BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formEtape";
		
		Filiere f = filiereRepository.findById(filiere).get();
		f.setId(filiere);
		etape.setFiliere(f);

		etapeRepository.save(etape);
		model.addAttribute("filiere",filiere);
		model.addAttribute("etape", etape);

		return "redirect:/etapes";
	}
	
	@RequestMapping(path="/saveSemestre" , method = RequestMethod.POST)
	public String saveSemstre(Model model,
			@Valid Semestre  semestre,
			@RequestParam("etape")Long etape,
			BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formSemestre";
		
		Etape f = etapeRepository.findById(etape).get();
		f.setId(etape);
		semestre.setEtape(f);

		semestreRepository.save(semestre);
		model.addAttribute("semestre", semestre);
		model.addAttribute("etape", etape);

		return "redirect:/semestres";
	}

	@RequestMapping(path="/saveModule" , method = RequestMethod.POST)
	public String saveModule(Model model,
			@Valid Module  module,
			@RequestParam("semestre")Long semestre,
			
			BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formSemestre";
		
		Semestre f = semestreRepository.findById(semestre).get();
		f.setId(semestre);
		module.setSemestre(f);

		moduleRepository.save(module);
		model.addAttribute("semestre", semestre);
		model.addAttribute("module", module);

		return "redirect:/modules";
	}

	@RequestMapping(path="/saveElement" , method = RequestMethod.POST)
	public String saveElement(Model model,
			@Valid Element  element,
			@RequestParam("module")Long module,
			@RequestParam("professeur")Long professeur,
			
			BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formModule";
		
		Module f = moduleRepository.findById(module).get();
		f.setId(module);
		element.setModule(f);
		
		Professeur p = professeurRepository.findById(professeur).get();
		p.setId(professeur);
		element.setProfesseur(p);
		
		
		elementRepository.save(element);
		model.addAttribute("element", element);
		model.addAttribute("professeur", professeur);
		model.addAttribute("module", module);

		return "redirect:/elements";
	}

	
	@GetMapping(path = "/filieres")
	public String filieres(Model model,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size){
		
		Page<Filiere> pageEtudiants = filiereRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("filieres", pageEtudiants.getContent());
		model.addAttribute("pages", new int[pageEtudiants.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		return "formFiliere";
	}
	@GetMapping(path = "/etapes")
	public String etapes(Model model,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size){
		
		Page<Etape> pageEtudiants = etapeRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("etapes", pageEtudiants.getContent());
		model.addAttribute("pages", new int[pageEtudiants.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		return "formEtape";
	}
	
	@GetMapping(path = "/semestres")
	public String semestres(Model model,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size){
		
		Page<Semestre> pageEtudiants = semestreRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("semestres", pageEtudiants.getContent());
		model.addAttribute("pages", new int[pageEtudiants.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		return "formSemestre";
	}
	
	@GetMapping(path = "/modules")
	public String modules(Model model,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size){
		
		Page<Module> pageEtudiants = moduleRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("modules", pageEtudiants.getContent());
		model.addAttribute("pages", new int[pageEtudiants.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		return "formModule";
	}
	
	@GetMapping(path = "/elements")
	public String elements(Model model,
			@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size){
		
		Page<Element> pageEtudiants = elementRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("elements", pageEtudiants.getContent());
		model.addAttribute("pages", new int[pageEtudiants.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		return "formElement";
	}
	
	/*
	 * @RequestMapping(path="/saveSemestre" , method = RequestMethod.POST) public
	 * String saveEtape(Model model,@Valid Semestre
	 * semestre,@RequestParam("id_etape")String id ,BindingResult bindingResult){
	 * if(bindingResult.hasErrors()) return "formEtape";
	 * 
	 * Long id_etape = etapeRepository.findId_etapeByLibelle_etape(id); Etape etape
	 * = etapeRepository.findById(id_etape).get(); etape.setId_etape(id_etape);
	 * semestre.setEtape(etape);
	 * 
	 * semestreRepository.save(semestre); model.addAttribute("semestre", semestre);
	 * 
	 * model.addAttribute("id_etape", id);
	 * 
	 * return "redirect:/etapes"; }
	 * 
	 * @RequestMapping(path="/saveModule" , method = RequestMethod.POST) public
	 * String saveEtape(Model model,@Valid Module module
	 * ,@RequestParam("id_semestre")String id ,BindingResult bindingResult){
	 * if(bindingResult.hasErrors()) return "formEtape";
	 * 
	 * Long id_semetre = semestreRepository.findId_moduleByLibelle_semestre(id);
	 * Semestre semestre = semestreRepository.findById(id_semetre).get();
	 * semestre.setId_semestre(id_semetre); module.setSemestre(semestre);
	 * 
	 * moduleRepository.save(module); model.addAttribute("module", module);
	 * 
	 * model.addAttribute("id_semestre", id);
	 * 
	 * return "redirect:/etapes"; }
	 * 
	 * @GetMapping(path="/etapes") public String listEtapes(Model model ) {
	 * List<Etape> etapes = etapeRepository.findAll(); model.addAttribute("etapes",
	 * etapes);
	 * 
	 * List<Semestre> semestres = semestreRepository.findAll();
	 * model.addAttribute("semestres", semestres);
	 * 
	 * List<Module> modules = moduleRepository.findAll();
	 * model.addAttribute("modules", modules);
	 * 
	 * return "redirect:/formEtape"; }
	 */



	//select Filiere,etape,ajout semestre

	/*
	 * @GetMapping(path="/selectFiliere") public String selectFiliere(Model model ,
	 * 
	 * @RequestParam(name="page",defaultValue = "0")int page ,
	 * 
	 * @RequestParam(name="size",defaultValue = "5")int size ,
	 * 
	 * @RequestParam(name="filiere",defaultValue = "")String filiere
	 * //@RequestParam(name="annee",defaultValue = "")String annee
	 * 
	 * ) { Long id_filiere =
	 * filiereRepository.findId_filiereByNome_filiere(filiere); Page<Etudiant>
	 * pageEtudiant = etudiantRepository.findById_filiere(id_filiere,
	 * PageRequest.of(page, size)); //Page<Etudiant> pageEtudiant =
	 * etudiantRepository.findById_filiereAndAnnee_academique(id_filiere,annee,
	 * PageRequest.of(page, size)); //Page<Etudiant> pageEtudiant =
	 * etudiantRepository.findByAnnee_academique(annee, PageRequest.of(page, size));
	 * List<Filiere> f = filiereRepository.findAll();
	 * model.addAttribute("selectFiliere",pageEtudiant.getContent());
	 * model.addAttribute("pages",new int[pageEtudiant.getTotalPages()]);
	 * model.addAttribute("currentPage",page); model.addAttribute("filiere",f);
	 * //model.addAttribute("annee",annee);
	 * 
	 * model.addAttribute("size",size); return "listeSemestre"; }
	 */

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

	@Autowired
	InscriptionPedagogiqueRepository pedagogiqueRepository;

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
	/*
	 * @RequestMapping(path="/saveAjoutSemestre" , method = RequestMethod.POST)
	 * public String saveAjoutSemestre(Model model ,
	 * 
	 * @RequestParam(name="cne")String cne,
	 * 
	 * @RequestParam(name="filiere")Long filiere,
	 * 
	 * @RequestParam(name="semestre")String semestre,
	 * 
	 * @Valid InscriptionPedagogique admin, BindingResult bindingResult){
	 * if(bindingResult.hasErrors()) return "ConfirmationSemestre";
	 * 
	 * Etudiant etudiant = etudiantRepository.getOne(cne);
	 * model.addAttribute("etudiant", cne); etudiant.setCne(cne);
	 * admin.setEtudiant(etudiant);
	 * 
	 * Filiere f = filiereRepository.findById(filiere).get();
	 * f.setId_filiere(filiere); admin.setFiliere(f);
	 * 
	 * 
	 * Long id = semestreRepository.findId_moduleByLibelle_semestre(semestre);
	 * Semestre s = semestreRepository.findById(id).get(); s.setId_semestre(id);
	 * admin.setSemestre(s);
	 * 
	 * 
	 * inscriptionPedagogiqueRepository.save(admin); model.addAttribute("admin",
	 * admin); model.addAttribute("cne", cne); model.addAttribute("filiere", f);
	 * model.addAttribute("semestre", s);
	 * 
	 * return "listeSemestre"; }
	 */

	
}

