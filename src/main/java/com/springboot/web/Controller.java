package com.springboot.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.FiliereRepository;
import com.springboot.dao.InscriptionAdministrativeRepository;
import com.springboot.dao.InscriptionEnligneRepository;
import com.springboot.entities.Filiere;
import com.springboot.entities.InscriptionAdministrative;
import com.springboot.entities.InscriptionEnligne;


@org.springframework.stereotype.Controller
public class Controller {

	@Autowired
	InscriptionEnligneRepository inscriptionEnligneRepository;
	@Autowired
	InscriptionAdministrativeRepository inscriptionAdministrativeRepository;
	@Autowired
	FiliereRepository filiereRepository;
	//1.Inscription Enligne

	//Affichage avec pagination : chercher
	@GetMapping(path="/enlignes") 
	public String listEnligne(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		Page<InscriptionEnligne> pageEnlignes = inscriptionEnligneRepository.findByNom_frContains(keyword,PageRequest.of(page, size));
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

	//Insertion
	@RequestMapping(path="/enligne")
	public String inscriptionEnligne(Model model) {
		model.addAttribute("enligne", new InscriptionEnligne());
		model.addAttribute("mode", "new");
		return "formEnligne";
	}
	//Supprission
	@GetMapping(path="/deleteEnligne")
	public String deleteEnligne(String id ) {
		inscriptionEnligneRepository.deleteByCne(id);
		return "redirect:/enlignesAll";
	}
	//Validation 
	@RequestMapping(path="/saveEnligne" , method = RequestMethod.POST)
	public String saveEnligne(Model model,@Valid InscriptionEnligne inscriptionEnligne,BindingResult bindingResult){
		if(bindingResult.hasErrors()) return "formEnligne";
		inscriptionEnligneRepository.save(inscriptionEnligne);
		model.addAttribute("enligne", inscriptionEnligne);
		return "redirect:/enlignesAll";
	}	 

	//Valider InscriptionEnligne : mis valide_enligne=true
	@GetMapping(path="/validerEnligne")
	public String validerEnligne(Model model,String id ,
			@RequestParam(value="bar", required = true , defaultValue = "true")
	boolean bar) {
		InscriptionEnligne enligne = inscriptionEnligneRepository.findByCne(id);
		enligne.setValide_enligne(bar);
		inscriptionEnligneRepository.save(enligne);
		model.addAttribute("bar", bar);
		model.addAttribute("enligne",enligne);
		return "redirect:/enlignesAll?bar="+bar;
	}
	//Les enregistrements
	@GetMapping(path="/ConfermationEnligne")
	public String ConfermationEnligne(Model model,String id) {
		InscriptionEnligne enligne = inscriptionEnligneRepository.findByCne(id);
		model.addAttribute("enligne", enligne);
		return "ConfirmationEnligne";
	}
	
		//2.Inscription Administrative
	
	//Les enregistrements avec Insertion Admin
		@GetMapping(path="/ConfermationAdmin")
		public String ConfermationAdmin(Model model , String id) {
			InscriptionEnligne enligne = inscriptionEnligneRepository.getOne(id);
			model.addAttribute("enligne", enligne);
			model.addAttribute("admin", new InscriptionAdministrative());
			id = inscriptionEnligneRepository.findByCne(id).getCne();
			model.addAttribute("cne", id);
			
			
			model.addAttribute("mode", "new");
			
			return "ConfirmationAdmin";
		}
		
		//Validation 
		@RequestMapping(path="/saveAdmin" , method = RequestMethod.POST)
		public String saveAdmin(Model model,@RequestParam("cne")String id,
				
				@Valid InscriptionAdministrative admin,BindingResult bindingResult){
			if(bindingResult.hasErrors()) return "formAdmin";
			
			InscriptionEnligne enligne = inscriptionEnligneRepository.getOne(id);
			id = inscriptionEnligneRepository.findByCne(id).getCne();
			
			
			enligne.setCne(id);
			admin.setInscriptionEnligne(enligne);
			inscriptionAdministrativeRepository.save(admin);
			model.addAttribute("admin", admin);
			model.addAttribute("enligne", enligne);
			model.addAttribute("cne", id);
			
			return "redirect:/adminsAll";
		}

		
		//Edition
		@RequestMapping(path="/editAdmin")
		public String editAdmin(Model model , Long id 
				) {
			InscriptionAdministrative administrative = inscriptionAdministrativeRepository.findById(id).get();
			
			model.addAttribute("admin", administrative);
			
			model.addAttribute("mode", "edit");
			return "ConfirmationAdmin";//Encore des problemes ici , pas encore fini, il fait pas l'edition , il fait l'ajout
		}


		
		
	//afichage des inscriptionr Enligne valide
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


	//Supprission
	@GetMapping(path="/deleteAdmin")
	public String deleteAdmin(Long id ) {
		inscriptionAdministrativeRepository.deleteById(id);
		return "redirect:/adminsAll";
	}



}
