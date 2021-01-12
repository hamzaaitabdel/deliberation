package com.springboot.web;

import java.util.List;

import javax.validation.Valid;

import com.springboot.dao.AnneeUniversitaireRepository;
import com.springboot.dao.EtablissementRepository;
import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.FiliereRepository;
import com.springboot.dao.InscriptionAdministrativeRepository;
import com.springboot.dao.InscriptionEnligneRepository;
import com.springboot.dao.InscriptionPedagogiqueRepository;
import com.springboot.dao.ModuleRepository;
import com.springboot.entities.AnneeUniversitaire;
import com.springboot.entities.Etablissement;
import com.springboot.entities.Etape;
import com.springboot.entities.Etudiant;
import com.springboot.entities.Filiere;
import com.springboot.entities.InscriptionAdministrative;
import com.springboot.entities.InscriptionEnligne;
import com.springboot.entities.InscriptionPedagogique;
import com.springboot.entities.Module;
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
public class AdminiController {
	@Autowired
	InscriptionEnligneRepository inscriptionEnligneRepository;
	@Autowired
	InscriptionAdministrativeRepository inscriptionAdministrativeRepository;
	@Autowired
	EtudiantRepository etudiantRepository;
	@Autowired
	FiliereRepository filiereRepository;

	@Autowired
	com.springboot.dao.ModuleRepository moduleRepository;
	@Autowired
	com.springboot.dao.EtapeRepository etapeRepository;
	@Autowired
	com.springboot.dao.SemestreRepository semestreRepository;

	@Autowired
	com.springboot.dao.InscriptionPedagogiqueRepository inscriptionPedagogiqueRepository;
	@Autowired
	AnneeUniversitaireRepository anneeUniversitaireRepository;

	//2.Inscription Administrative


	//@RequestParam(name = "id_filiere" , defaultValue = "1")Long id_filiere
	@GetMapping(path="/ConfirmationAdmin")
	public String ConfirmationAdmin(Model model , Long id_enligne,String cne ) {

		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id_enligne).get();
		model.addAttribute("enligne", enligne);
		//		
		

		
		List<Filiere> f = filiereRepository.findAll();
		List<AnneeUniversitaire> u = anneeUniversitaireRepository.findAll();
	
		List<Etablissement> le = etablissementRepository.findAll();



		model.addAttribute("id", id_enligne);
		model.addAttribute("cne", cne);
				model.addAttribute("annee", u);
			model.addAttribute("filiere", f);
		model.addAttribute("etablissement", le);
		model.addAttribute("mode", "new");

		return "ConfirmationAdmin";
	}

	/*@RequestMapping(path="/saveAdmin" , method = RequestMethod.POST)
	public String saveAdmin(Model model ,@RequestParam("cne")String cne,@RequestParam("filiere")Long filiere,
			@Valid InscriptionAdministrative admin,
			BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formAdmin";

		InscriptionEnligne enligne = inscriptionEnligneRepository.findByCne(cne);
		enligne.setCne(cne);
		admin.setInscriptionEnligne(enligne);

		Filiere f = filiereRepository.findById(filiere).get();
		f.setId_filiere(filiere);
		admin.setFiliere(f);

		inscriptionAdministrativeRepository.save(admin);

		return "redirect:/";

	}*/

	//	@RequestMapping(path="/saveAdmin" , method = RequestMethod.POST)
	//	public String saveAdmin(Model model ,@RequestParam("cne")Long cne,@RequestParam("filiere")Long filiere,
	//			@Valid InscriptionAdministrative admin,
	//			BindingResult bindingResult){
	//		if(bindingResult.hasErrors()) return "formAdmin";
	//
	//		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(cne).get();
	//		enligne.setId(cne);
	//		admin.setInscriptionEnligne(enligne);
	//
	//		Filiere f = filiereRepository.findById(filiere).get();
	//		f.setId(filiere);
	//		admin.setFiliere(f);
	//
	//		inscriptionAdministrativeRepository.save(admin);
	//		//			
	//		//			Etudiant e = new Etudiant();  
	//		//			e.setNom_etud(enligne.getNom_fr());
	//		//			e.setPrenom_etud(enligne.getPrenom_fr());
	//		//			
	//		//			e.setAnnee_academique(admin.getAnnee_academique());
	//		//			
	//		//			e.setEmail_etud(enligne.getEmail());
	//		//			
	//		//			e.setFiliere(admin.getFiliere());
	//		//			
	//		//			//e.setResultat(enligne.getResultat());
	//		//			
	//		//			e.setTel_etud(admin.getTelephone());
	//		//			
	//		//			e.setCne(enligne.getCne());
	//		//			
	//		//			etudiantRepository.save(e);
	//
	//		model.addAttribute("admin", admin);
	//		model.addAttribute("enligne", enligne);
	//		model.addAttribute("filiere", f);
	//
	//		return "redirect:/adminsAll";
	//	}
	////
	//	@RequestMapping(path="/saveAdmin" , method = RequestMethod.POST)
	//	public String saveAdmin(Model model ,@RequestParam("cne")String cne,@RequestParam("filiere")Long filiere,
	//			@Valid InscriptionAdministrative admin,
	//			BindingResult bindingResult){
	//		if(bindingResult.hasErrors()) return "formAdmin";
	//
	//		InscriptionEnligne enligne = inscriptionEnligneRepository.findByCne(cne);
	//		enligne.setCne(cne);
	//		admin.setInscriptionEnligne(enligne);
	//
	//		Filiere f = filiereRepository.findById(filiere).get();
	//		f.setId(filiere);
	//		admin.setFiliere(f);
	//
	//		inscriptionAdministrativeRepository.save(admin);
	//
	//
	//		model.addAttribute("admin", admin);
	//		model.addAttribute("enligne", enligne);
	//		model.addAttribute("filiere", f);
	//
	//		return "redirect:/adminsAll";
	//	}
	//	@GetMapping(path="/administrativeAll") 
	//	public String listAdministrativeAll(Model model ,
	//			@RequestParam(name="page",defaultValue = "0")int page ,
	//			@RequestParam(name="size",defaultValue = "5")int size , 
	//			@RequestParam(name="keyword",defaultValue = "")String keyword){
	//		Page<InscriptionEnligne> pageEnlignes = inscriptionEnligneRepository.findByValideEnligne(PageRequest.of(page, size));
	//	
	@Autowired
	EtablissementRepository etablissementRepository;

	@PostMapping(path="/saveAdmin")
	public String saveAdmin(Model model ,
			@RequestParam("cne")String cne,
			@RequestParam("filiere")Long id_filiere,
			@RequestParam("id_enligne")Long id_enligne,
			@RequestParam("annee")Long annee,
			@RequestParam("etablissement")Long etabli,

			@Valid InscriptionAdministrative admin,
			BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "ConfirmationAdmin";

		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id_enligne).get();

		admin.setInscriptionEnligne(enligne);

		admin.setCne(cne);



		Filiere f = filiereRepository.findById(id_filiere).get();
		f.setId(id_filiere);
		admin.setFiliere(f);


		AnneeUniversitaire a = anneeUniversitaireRepository.findById(annee).get();
		admin.setAnneeUniversitaire(a);

		Etablissement e = etablissementRepository.findById(etabli).get();
		admin.setEtablissement(e);

		inscriptionAdministrativeRepository.save(admin);



		return "redirect:/adminsAll";
	}



	@GetMapping(path="/admins") 
	public String listAdmin(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		/*
		Page<InscriptionAdministrative> pageAdmins = inscriptionAdministrativeRepository.findByAnnee_academiqueContains(keyword, PageRequest.of(page, size));
=======
		Page<InscriptionAdministrative> pageAdmins = inscriptionAdministrativeRepository.findByNomContains(keyword, PageRequest.of(page, size));
>>>>>>> branch 'Binome2' of https://github.com/hamzaaitabdel/deliberation
		model.addAttribute("admins",pageAdmins.getContent());
		model.addAttribute("pages",new int[pageAdmins.getTotalPages()]);
		 */	model.addAttribute("currentPage",page);
		 model.addAttribute("keyword",keyword);
		 model.addAttribute("size",size);
		 return "listeAdmin";
	}


	//Affichage avec pagination :tous
	@GetMapping(path="/adminsAll") 
	public String listAdminAll(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		Page<InscriptionAdministrative> pageAdmins = inscriptionAdministrativeRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("admins",pageAdmins.getContent());
		model.addAttribute("pages",new int[pageAdmins.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",keyword);
		model.addAttribute("size",size);
		return "listeAdmin";
	}
	//validation Admini manuelle

	@GetMapping(path="/validAdmin")
	public String validAdmin(Model model,Long id ) {
		InscriptionAdministrative admin = inscriptionAdministrativeRepository.findById(id).get();
		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(admin.getInscriptionEnligne().getId()).get();
		Etudiant e = new Etudiant();  
		e.setNom(enligne.getNomFr());
		e.setPrenom(enligne.getPrenomFr());

		e.setFiliere(admin.getFiliere());

		e.setCne(enligne.getCne());
		e.setEmail(admin.getEmailEtud());
		e.setTelephone(admin.getTelephone());

		etudiantRepository.save(e);

		model.addAttribute("enligne",enligne);
		return "redirect:/etudiantsAll";
	}
	
	
	
	//	@GetMapping(path="/validAdmin")
	//	public String validAdmin(Model model,Long id ) {
	//		InscriptionAdministrative admin = inscriptionAdministrativeRepository.findById(id).get();
	//		InscriptionEnligne enligne = inscriptionEnligneRepository.findByCne(admin.getInscriptionEnligne().getCne());
	//		Etudiant e = new Etudiant();  
	//
	//		e.setCne(enligne.getCne());
	//		e.setNom(enligne.getNomFr());
	//		e.setPrenom(enligne.getPrenomFr());
	//
	//		e.setFiliere(admin.getFiliere());
	//		e.setTelephone(admin.getTelephone());
	//		e.setEmail(admin.getEmailEtud());
	//		etudiantRepository.save(e);
	//
	//		model.addAttribute("enligne",enligne);
	//		return "redirect:/etudiantsAll";
	//	}

	//Supprission
	@GetMapping(path="/deleteAdmin")
	public String deleteAdmin(Long id ) {
		inscriptionAdministrativeRepository.deleteById(id);
		return "redirect:/adminsAll";
	}

	//Supprission etape , semestre ,module
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

	// 4.Inscription Pedagogique ,affecter filiere a l'etape
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
	//Validation etape V2
	//	@RequestMapping(path="/saveEtape" , method = RequestMethod.POST)
	//	public String saveEtape(Model model,@Valid Etape  etape, @RequestParam("filiere")Long filiere,BindingResult bindingResult){
	//		if(bindingResult.hasErrors()) return "formEtape";
	//
	//		Filiere f = filiereRepository.findById(filiere).get();
	//		f.setId(filiere);
	//		etape.setFiliere(f);
	//
	//		etapeRepository.save(etape);
	//		model.addAttribute("etape", etape);
	//		model.addAttribute("filiere", filiere);
	//
	//		return "redirect:/etapes";
	//	}
	//	@RequestMapping(path="/saveSemestre" , method = RequestMethod.POST) public
	//	String saveSemestre(Model model,@Valid Semestre
	//			semestre,@RequestParam("id_etape")String id ,BindingResult bindingResult){
	//		if(bindingResult.hasErrors()) return "formEtape";
	//		
	//		//Long id_etape = etapeRepository.findId_etapeByLibelle_etape(id);
	//		//Etape etape = etapeRepository.findById(id_etape).get(); 
	//		//etape.setId(id_etape);
	//		//semestre.setEtape(etape);
	//
	//		semestreRepository.save(semestre); 
	//		model.addAttribute("semestre", semestre);
	//
	//		model.addAttribute("id_etape", id);
	//
	//		return "redirect:/etapes";
	//	}
	//	@RequestMapping(path="/saveModule" , method = RequestMethod.POST) public
	//	String saveModule(Model model,@Valid Module module
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
	////	@GetMapping(path="/etapes") 
	////	public String listEtapes(Model model
	////			) {
	////		List<Etape> etapes = etapeRepository.findAll();
	////		model.addAttribute("etapes", etapes);
	////
	////		List<Semestre> semestres = semestreRepository.findAll();
	////		model.addAttribute("semestres", semestres);
	////
	////		List<Module> modules = moduleRepository.findAll();
	////		model.addAttribute("modules", modules);
	////
	////		return "redirect:/formEtape";
	////	}
	//
	//	/*
	//	 * @GetMapping(path="/selectParFiliere") public String selectParFiliere(Model
	//	 * model ,@RequestParam(name="filiere")Long id_filiere) { //List<Etudiant>
	//	 * etudiants = etudiantRepository.findAllById_filiere(id_filiere); //Filiere
	//	 * filiere = filiereRepository.findById_filiere(id_filiere);
	//	 * 
	//	 * Filiere f = filiereRepository.findById(id_filiere).get();
	//	 * f.setId_filiere(id_filiere);
	//	 * 
	//	 * model.addAttribute("filiere", id_filiere);
	//	 * 
	//	 * model.addAttribute("etudiants",etudiants);
	//	 * 
	//	 * return "listeSemestre"; }
	//	 */
}
