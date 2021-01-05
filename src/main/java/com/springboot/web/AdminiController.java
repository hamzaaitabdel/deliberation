package com.springboot.web;

import java.util.List;

import javax.validation.Valid;

import com.springboot.dao.AnneeUniversitaireRepository;
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

	
	@GetMapping(path="/ConfermationAdmin")
	public String ConfermationAdmin(Model model , 
			@RequestParam(name = "id_enligne" , defaultValue = "")Long id_enligne, 
			@RequestParam(name = "id_filiere" , defaultValue = "")String id_filiere,
			@RequestParam(name = "annee" , defaultValue = "")String annee,
			@RequestParam(name = "etablissement" , defaultValue = "")String etablissement
			) {
		InscriptionEnligne enligne = inscriptionEnligneRepository.getOne(id_enligne);
		model.addAttribute("enligne", enligne);
		InscriptionAdministrative admin = new InscriptionAdministrative();
		model.addAttribute("admin", admin);
		id_enligne = inscriptionEnligneRepository.findById(id_enligne).get().getId();
		List<Filiere> f = filiereRepository.findAll();
		List<AnneeUniversitaire> u = anneeUniversitaireRepository.findAll();
		
		model.addAttribute("filiere", f);
		model.addAttribute("enligne", id_enligne);
		model.addAttribute("annees", u);
		model.addAttribute("etablissement", etablissement);
		

		model.addAttribute("mode", "new");

		return "ConfirmationAdmin";
	}
//
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
	@GetMapping(path="/administrativeAll") 
	public String listAdministrativeAll(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword){
		Page<InscriptionEnligne> pageEnlignes = inscriptionEnligneRepository.findByValideEnligne(PageRequest.of(page, size));

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
		Page<InscriptionAdministrative> pageAdmins = inscriptionAdministrativeRepository.findByNomContains(keyword, PageRequest.of(page, size));
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

}

