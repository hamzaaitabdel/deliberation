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

	
	@GetMapping(path="/ConfirmationAdmin")
	public String ConfirmationAdmin(Model model , Long id_enligne,
			String cne 

			) {
		InscriptionEnligne enligne = inscriptionEnligneRepository.getOne(id_enligne);
		model.addAttribute("enligne", enligne);
		InscriptionAdministrative admin = new InscriptionAdministrative();
		model.addAttribute("admin", admin);
		id_enligne = inscriptionEnligneRepository.findById(	id_enligne).get().getId();
		
		model.addAttribute("id", id_enligne);
		cne = inscriptionEnligneRepository.findById(id_enligne).get().getCne();
		model.addAttribute("cne", cne);
		

		model.addAttribute("mode", "new");

		return "ConfirmationAdmin";
	}
	
	@Autowired
	EtablissementRepository etablissementRepository;
	
	@PostMapping(path="/saveAdmin")
	public String saveAdmin(Model model ,
			@RequestParam("cne")String cne,
			@RequestParam("filiere")String filiere,
			@RequestParam("id_enligne")Long id_enligne,
			@RequestParam("annee")String annee,
			@RequestParam("etablissement")String etabli,
			
			@Valid InscriptionAdministrative admin,
			BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "ConfirmationAdmin";

		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id_enligne).get();
		
		enligne.setId(id_enligne);
		admin.setInscriptionEnligne(enligne);
		model.addAttribute("id_enligne", id_enligne);
		
		cne = inscriptionEnligneRepository.findById(id_enligne).get().getCne();
		admin.setCne(cne);
		model.addAttribute("cne", cne);
		
		Long id_filiere = filiereRepository.findIdByName(filiere);
		
		Filiere f = filiereRepository.findById(id_filiere).get();
		f.setId(id_filiere);
		admin.setFiliere(f);
		model.addAttribute("filiere", f);

		Long id_annee = anneeUniversitaireRepository.findByAnnee(annee);
		AnneeUniversitaire a = anneeUniversitaireRepository.findById(id_annee).get();
		a.setId(id_annee);
		admin.setAnneeUniversitaire(a);
		model.addAttribute("annee", a);
		
		Long id_etabli = etablissementRepository.findIdByName(etabli);
		Etablissement e = etablissementRepository.findById(id_etabli).get();
		e.setId(id_etabli);
		admin.setEtablissement(e);
		model.addAttribute("etablissement", e);
		
		

		model.addAttribute("admin", admin);
		
		inscriptionAdministrativeRepository.save(admin);



		return "redirect:/adminsAll";
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

