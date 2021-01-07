package com.springboot.web;

import java.util.List;

import javax.validation.Valid;

import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.FiliereRepository;
import com.springboot.dao.InscriptionAdministrativeRepository;
import com.springboot.dao.InscriptionEnligneRepository;
import com.springboot.dao.InscriptionPedagogiqueRepository;
import com.springboot.dao.ModuleRepository;
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
public class EnligneController {
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


	//1.Inscription Enligne

	//Insertion
	@RequestMapping(path="/formEnligne")
	public String inscriptionEnligne(Model model) {
		model.addAttribute("enligne", new InscriptionEnligne());
		model.addAttribute("mode", "new");
		return "formEnligne";
	}

	//Affichage avec pagination : chercher
	@GetMapping(path="/enlignes") 
	public String listEnligne(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		Page<InscriptionEnligne> pageEnlignes = inscriptionEnligneRepository.findByNomContains(keyword,PageRequest.of(page, size));
		model.addAttribute("enlignes",pageEnlignes.getContent());
		model.addAttribute("pages",new int[pageEnlignes.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",keyword);
		model.addAttribute("size",size);

		return "listeEnligne";
	}

	//Affichage avec pagination :tous
	@GetMapping(path="/enlignesAll") 
	public String listEnligneAll(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		Page<InscriptionEnligne> pageEnlignes = inscriptionEnligneRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("enlignes",pageEnlignes.getContent());
		model.addAttribute("pages",new int[pageEnlignes.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",keyword);
		model.addAttribute("size",size);
		return "listeEnligne";
	}

	//Supprission
	@GetMapping(path="/deleteEnligne")
	public String deleteEnligne(Long id ) {
		inscriptionEnligneRepository.deleteById(id);
		return "redirect:/enlignesAll";
	}
	//Validation 
	@RequestMapping(path="/saveEnligne" , method = RequestMethod.POST)
	public String saveEnligne(Model model,@Valid InscriptionEnligne inscriptionEnligne,BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formEnligne";
		inscriptionEnligneRepository.save(inscriptionEnligne);
		model.addAttribute("enligne", inscriptionEnligne);
		return "EnregistrementEnligne";
	}	 

	//Valider InscriptionEnligne : mis valide_enligne=true
	@GetMapping(path="/validerEnligne")
	public String validerEnligne(Model model,Long id ,
			@RequestParam(value="bar", required = true , defaultValue = "true")
	boolean bar) {
		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id).get();
		enligne.setValideEnligne(bar);
		inscriptionEnligneRepository.save(enligne);
		model.addAttribute("bar", bar);
		model.addAttribute("enligne",enligne);
		return "redirect:/enlignesAll?bar="+bar;
	}
	//Les enregistrements
	@GetMapping(path="/ConfirmationEnligne")
	public String ConfirmationEnligne(Model model,Long id) {
		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id).get();
		model.addAttribute("enligne", enligne);
		return "ConfirmationEnligne";
	}

}
	/*
	@GetMapping(path="/ConfermationAdmin")
	public String ConfermationAdmin(Model model , String id 
			//@RequestParam(name = "id_filiere" , defaultValue = "1")Long id_filiere
			) {
		InscriptionEnligne enligne = inscriptionEnligneRepository.getOne(id);
		model.addAttribute("enligne", enligne);
		InscriptionAdministrative admin = new InscriptionAdministrative();
		model.addAttribute("admin", admin);
		id = inscriptionEnligneRepository.findByCne(id).getCne();
		//Filiere filiere = filiereRepository.getOne(id_filiere);
		//filiere.getNom_filiere();
		//model.addAttribute("filiere", filiere);
		model.addAttribute("cne", id);


		model.addAttribute("mode", "new");

		return "ConfirmationAdmin";
	}

	@RequestMapping(path="/saveAdmin" , method = RequestMethod.POST)
	public String saveAdmin(Model model ,@RequestParam("cne")String cne,@RequestParam("filiere")Long filiere,
			@Valid InscriptionAdministrative admin,
			BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formAdmin";

		InscriptionEnligne enligne = inscriptionEnligneRepository.findByCne(cne);
		enligne.setCne(cne);
		admin.setInscriptionEnligne(enligne);

		Filiere f = filiereRepository.findById(filiere).get();
		f.setId(filiere);
		admin.setFiliere(f);

		inscriptionAdministrativeRepository.save(admin);


		model.addAttribute("admin", admin);
		model.addAttribute("enligne", enligne);
		model.addAttribute("filiere", f);

		return "redirect:/adminsAll";
	}
	@GetMapping(path="/administrativeAll") 
	public String listAdministrativeAll(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword){
		Page<InscriptionEnligne> pageEnlignes = inscriptionEnligneRepository.findByValide_enligne(PageRequest.of(page, size));

		model.addAttribute("enligne",pageEnlignes.getContent());
		model.addAttribute("pages",new int[pageEnlignes.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",keyword);
		model.addAttribute("size",size);
		return "confirmAdmini";
	}



	@GetMapping(path="/admins") 
	public String listAdmin(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		Page<InscriptionAdministrative> pageAdmins = inscriptionAdministrativeRepository.findByAnnee_academiqueContains(keyword, PageRequest.of(page, size));
		model.addAttribute("admins",pageAdmins.getContent());
		model.addAttribute("pages",new int[pageAdmins.getTotalPages()]);
		model.addAttribute("currentPage",page);
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
		InscriptionEnligne enligne = inscriptionEnligneRepository.findByCne(admin.getInscriptionEnligne().getCne());
		Etudiant e = new Etudiant();  

		e.setCne(enligne.getCne());
		e.setNom(enligne.getNomFr());
		e.setPrenom(enligne.getPrenomFr());

		e.setFiliere(admin.getFiliere());
		e.setTelephone(admin.getTelephone());
		e.setEmail(admin.getEmailEtud());
		etudiantRepository.save(e);

		model.addAttribute("enligne",enligne);
		return "redirect:/etudiantsAll";
	}

	//Supprission
	@GetMapping(path="/deleteAdmin")
	public String deleteAdmin(Long id ) {
		inscriptionAdministrativeRepository.deleteById(id);
		return "redirect:/adminsAll";
	}

	//Supprission etape , semestre ,module
	@GetMapping(path="/deleteEtape")
	public String deleteEtape(Long id ) {
		etapeRepository.deleteById(id);
		return "redirect:/formEtape";
	}
	@GetMapping(path="/deleteSemestre")
	public String deleteSemstre(Long id ) {
		semestreRepository.deleteById(id);
		return "redirect:/formEtape";
	}
	@GetMapping(path="/deleteModule")
	public String deleteModule(Long id ) {
		moduleRepository.deleteById(id);
		return "redirect:/formEtape";
	}

	// 4.Inscription Pedagogique ,affecter filiere a l'etape

	@GetMapping(path="/formEtape")
	public String formEtape(Model model) {
		Etape etape = new Etape();
		model.addAttribute("etape", etape);
		List<Etape> etapes = etapeRepository.findAll();
		model.addAttribute("etapes", etapes);

		Semestre semestre = new Semestre();
		model.addAttribute("semestre", semestre);
		List<Semestre> semestres = semestreRepository.findAll();
		model.addAttribute("semestres", semestres);
		Module module = new Module();
		model.addAttribute("module", module);
		List<Module> modules = moduleRepository.findAll();
		model.addAttribute("modules", modules);

		return "formEtape";
	}
	//Validation etape V2
	@RequestMapping(path="/saveEtape" , method = RequestMethod.POST)
	public String saveEtape(Model model,@Valid Etape  etape, @RequestParam("filiere")Long filiere,BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formEtape";

		Filiere f = filiereRepository.findById(filiere).get();
		f.setId_filiere(filiere);
		etape.setFiliere(f);

		etapeRepository.save(etape);
		model.addAttribute("etape", etape);
		model.addAttribute("filiere", filiere);

		return "redirect:/etapes";
	}
	@RequestMapping(path="/saveSemestre" , method = RequestMethod.POST) public
	String saveEtape(Model model,@Valid Semestre
			semestre,@RequestParam("id_etape")String id ,BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formEtape";

		Long id_etape = etapeRepository.findId_etapeByLibelle_etape(id);
		Etape etape = etapeRepository.findById(id_etape).get(); 
		etape.setId_etape(id_etape);
		semestre.setEtape(etape);

		semestreRepository.save(semestre); 
		model.addAttribute("semestre", semestre);

		model.addAttribute("id_etape", id);

		return "redirect:/etapes";
	}
	@RequestMapping(path="/saveModule" , method = RequestMethod.POST) public
	String saveEtape(Model model,@Valid Module module
			,@RequestParam("id_semestre")String id ,BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formEtape";

		Long id_semetre = semestreRepository.findId_moduleByLibelle_semestre(id);
		Semestre semestre = semestreRepository.findById(id_semetre).get();
		semestre.setId_semestre(id_semetre);
		module.setSemestre(semestre);

		moduleRepository.save(module);
		model.addAttribute("module", module);

		model.addAttribute("id_semestre", id);

		return "redirect:/etapes";
	}
	@GetMapping(path="/etapes") 
	public String listEtapes(Model model
			) {
		List<Etape> etapes = etapeRepository.findAll();
		model.addAttribute("etapes", etapes);

		List<Semestre> semestres = semestreRepository.findAll();
		model.addAttribute("semestres", semestres);

		List<Module> modules = moduleRepository.findAll();
		model.addAttribute("modules", modules);

		return "redirect:/formEtape";
	}



	//select Filiere,etape,ajout semestre


	@GetMapping(path="/selectFiliere") 
	public String selectFiliere(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="filiere",defaultValue = "")String filiere
			//@RequestParam(name="annee",defaultValue = "")String annee

			) {
		Long id_filiere = filiereRepository.findId_filiereByNome_filiere(filiere);
		Page<Etudiant> pageEtudiant = etudiantRepository.findById_filiere(id_filiere, PageRequest.of(page, size));
		//Page<Etudiant> pageEtudiant = etudiantRepository.findById_filiereAndAnnee_academique(id_filiere,annee, PageRequest.of(page, size));
		//Page<Etudiant> pageEtudiant = etudiantRepository.findByAnnee_academique(annee, PageRequest.of(page, size));
		List<Filiere> f = filiereRepository.findAll();
		model.addAttribute("selectFiliere",pageEtudiant.getContent());
		model.addAttribute("pages",new int[pageEtudiant.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("filiere",f);
		//model.addAttribute("annee",annee);

		model.addAttribute("size",size);
		return "listeSemestre";
	}


	//Affichage avec pagination :tous
	@GetMapping(path = "/etudiantsParFiliere")
	public String etudiantsParFiliere(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "filiere", defaultValue = "") String filiere) {
		Page<Etudiant> pageEtudiants = etudiantRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("etudiants", pageEtudiants.getContent());
		model.addAttribute("pages", new int[pageEtudiants.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("filiere", filiere);
		model.addAttribute("size", size);
		return "listeSemestre";
	}

	@Autowired
	InscriptionPedagogiqueRepository pedagogiqueRepository;

	@GetMapping(path="/ajouterParSemestre")
	public String ajouterParSemestre(Model model 
			//@RequestParam(name = "cne" , defaultValue = "")String cne 
			//@RequestParam(name = "filiere" , defaultValue = "")String filiere
			) {

		InscriptionPedagogique admin = new InscriptionPedagogique();
		model.addAttribute("admin", admin);
//		Etudiant e = etudiantRepository.getOne(cne);
//		e.setCne(cne);
//		model.addAttribute("cne", cne);
		model.addAttribute("mode", "new");

		return "ConfirmationSemestre";
	}

	@RequestMapping(path="/saveAjoutSemestre" , method = RequestMethod.POST)
	public String saveAjoutSemestre(Model model ,
			@RequestParam(name="cne")String cne,
			@RequestParam(name="filiere")Long filiere,
			@RequestParam(name="semestre")String semestre,

			@Valid InscriptionPedagogique admin,
			BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "ConfirmationSemestre";

		Etudiant etudiant = etudiantRepository.getOne(cne);
		model.addAttribute("etudiant", cne);
		etudiant.setCne(cne);
		admin.setEtudiant(etudiant);

		Filiere f = filiereRepository.findById(filiere).get();
		f.setId_filiere(filiere);
		admin.setFiliere(f);


		Long id = semestreRepository.findId_moduleByLibelle_semestre(semestre);
		Semestre s = semestreRepository.findById(id).get();
		s.setId_semestre(id);
		admin.setSemestre(s);


		inscriptionPedagogiqueRepository.save(admin);
		model.addAttribute("admin", admin);
		model.addAttribute("cne", cne);
		model.addAttribute("filiere", f);
		model.addAttribute("semestre", s);

		return "listeSemestre";
	}

	//

//	//inscrire pedagogiquement
//		@GetMapping(path="/inscrirePedagogiquement") 
//		public String inscrirePedagogiquement(Model model, String cne) {
//			Etudiant e = etudiantRepository.findById(cne).get();
//			/*multiple etapes can belong to a single filiere so
//			 * it would be preferable to fetch a list of etapes,
//			 * a list of semeters of these etapes,
//			 * and then lists of all modules
//			 * or fetch all the modules that belong to a specific filiere
//			 * i'm pretty sure it's way better
//			 * TriHard 7 */
//			Etape et= etapeRepository.findById_filiere(e.getFiliere().getId_filiere());
//			Semestre s = semestreRepository.findById_etape(et.getId_etape());
//			List<Module> lm=moduleRepository.findById_semestre(s.getId_semestre());
//
//			model.addAttribute("etudiant", e);
//			model.addAttribute("modules", lm);
//
//			return "formPedagogique";
//		}
//
//		
//		//save pedagogique
//		@PostMapping(path="/savePedagogique")
//		public String savePedagogique(@RequestParam(name="cne")String cne,
//				/*@RequestParam(name="module")String libelle_module,*/
//				@RequestParam(name="module")String mod) {
//
//			Etudiant e = etudiantRepository.findById(cne).get();
//			Filiere f = filiereRepository.findById_filiere(e.getFiliere().getId_filiere());
//			System.out.println("mod id: "+mod);
//			Module m = moduleRepository.findById(Long.parseLong(mod)).get();
//			InscriptionPedagogique ip = new InscriptionPedagogique();
//			ip.setFiliere(f);
//			ip.setEtudiant(e);
//			ip.setModule(m);
//
//			inscriptionPedagogiqueRepository.save(ip);
//
//			return "redirect:/listePedago";
//		}
//
//		// insc pedago
//		@GetMapping(path="/listePedago") 
//		public String listePedago(Model model ,
//				@RequestParam(name="page",defaultValue = "0")int page ,
//				@RequestParam(name="size",defaultValue = "5")int size , 
//				@RequestParam(name="keyword",defaultValue = "")String keyword) {
//			Page<InscriptionPedagogique> pageinscpedago = inscriptionPedagogiqueRepository.findAll(PageRequest.of(page, size));
//			model.addAttribute("pedagogiques",pageinscpedago.getContent());
//			model.addAttribute("pages",new int[pageinscpedago.getTotalPages()]);
//			model.addAttribute("currentPage",page);
//			model.addAttribute("keyword",keyword);
//			model.addAttribute("size",size);
//			return "listePedago";
//		}
//}

