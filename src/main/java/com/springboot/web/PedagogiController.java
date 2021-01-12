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
//public class PedagogiController {
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
//	//3.Inscription Pedagogique
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
////	//inscrire pedagogiquement
////		@GetMapping(path="/inscrirePedagogiquement") 
////		public String inscrirePedagogiquement(Model model, String cne) {
////			Etudiant e = etudiantRepository.findById(cne).get();
////			/*multiple etapes can belong to a single filiere so
////			 * it would be preferable to fetch a list of etapes,
////			 * a list of semeters of these etapes,
////			 * and then lists of all modules
////			 * or fetch all the modules that belong to a specific filiere
////			 * i'm pretty sure it's way better
////			 * TriHard 7 */
////			Etape et= etapeRepository.findById_filiere(e.getFiliere().getId_filiere());
////			Semestre s = semestreRepository.findById_etape(et.getId_etape());
////			List<Module> lm=moduleRepository.findById_semestre(s.getId_semestre());
////
////			model.addAttribute("etudiant", e);
////			model.addAttribute("modules", lm);
////
////			return "formPedagogique";
////		}
////
////		
////		//save pedagogique
////		@PostMapping(path="/savePedagogique")
////		public String savePedagogique(@RequestParam(name="cne")String cne,
////				/*@RequestParam(name="module")String libelle_module,*/
////				@RequestParam(name="module")String mod) {
////
////			Etudiant e = etudiantRepository.findById(cne).get();
////			Filiere f = filiereRepository.findById_filiere(e.getFiliere().getId_filiere());
////			System.out.println("mod id: "+mod);
////			Module m = moduleRepository.findById(Long.parseLong(mod)).get();
////			InscriptionPedagogique ip = new InscriptionPedagogique();
////			ip.setFiliere(f);
////			ip.setEtudiant(e);
////			ip.setModule(m);
////
////			inscriptionPedagogiqueRepository.save(ip);
////
////			return "redirect:/listePedago";
////		}
////
////		// insc pedago
////		@GetMapping(path="/listePedago") 
////		public String listePedago(Model model ,
////				@RequestParam(name="page",defaultValue = "0")int page ,
////				@RequestParam(name="size",defaultValue = "5")int size , 
////				@RequestParam(name="keyword",defaultValue = "")String keyword) {
////			Page<InscriptionPedagogique> pageinscpedago = inscriptionPedagogiqueRepository.findAll(PageRequest.of(page, size));
////			model.addAttribute("pedagogiques",pageinscpedago.getContent());
////			model.addAttribute("pages",new int[pageinscpedago.getTotalPages()]);
////			model.addAttribute("currentPage",page);
////			model.addAttribute("keyword",keyword);
////			model.addAttribute("size",size);
////			return "listePedago";
////		}
//	
//}
//
