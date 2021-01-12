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
	
	@Autowired
	EtablissementRepository etablissementRepository;
	//2.Inscription Administrative


	//@RequestParam(name = "id_filiere" , defaultValue = "1")Long id_filiere
	@GetMapping(path="/ConfirmationAdmin")
	public String ConfirmationAdmin(Model model , Long id_enligne,String cne ) {

		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id_enligne).get();
		model.addAttribute("enligne", enligne);
		
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
	
	
	
	//Supprission
	@GetMapping(path="/deleteAdmin")
	public String deleteAdmin(Long id ) {
		inscriptionAdministrativeRepository.deleteById(id);
		return "redirect:/adminsAll";
	}

}
