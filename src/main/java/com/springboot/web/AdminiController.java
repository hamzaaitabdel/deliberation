package com.springboot.web;

import javax.validation.Valid;

import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.FiliereRepository;
import com.springboot.dao.InscriptionAdministrativeRepository;
import com.springboot.dao.InscriptionEnligneRepository;
import com.springboot.entities.Filiere;
import com.springboot.entities.InscriptionAdministrative;
import com.springboot.entities.InscriptionEnligne;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
			f.setId_filiere(filiere);
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


	//Supprission
	@GetMapping(path="/deleteAdmin")
	public String deleteAdmin(Long id ) {
		inscriptionAdministrativeRepository.deleteById(id);
		return "redirect:/adminsAll";
	}


}
