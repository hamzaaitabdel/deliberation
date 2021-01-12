package com.springboot.web;

import java.io.IOException;
import java.util.Base64;
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
import com.springboot.service.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


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
	
	@Autowired
	com.springboot.service.MailService mailService;
	

	//1.Inscription Enligne

	//Insertion
	@RequestMapping(path="/formEnligne")
	public String inscriptionEnligne(Model model) {
		model.addAttribute("enligne", new InscriptionEnligne());
		return "formEnligne";
	}

	//Affichage avec pagination : chercher
	@GetMapping(path="/enlignes") 
	public String listEnligne(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		Page<InscriptionEnligne> pageEnlignes = inscriptionEnligneRepository.findByNomContains("%"+keyword+"%",PageRequest.of(page, size));
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
		model.addAttribute("mode", "false");
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
	public String saveEnligne(Model model,
			@Valid InscriptionEnligne inscriptionEnligne,
			BindingResult bindingResult,
			@RequestParam("file")MultipartFile file
			
			){
		if(bindingResult.hasErrors()) return "formEnligne";
		
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		if(filename.contains(".."))System.out.println("not a valid file");
		try {
			inscriptionEnligne.setPhotoEtud(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		

		
		inscriptionEnligneRepository.save(inscriptionEnligne);
		
		Mail mail = new Mail();
		mail.setMailFrom("ryfysafwane@gmail.com");
		mail.setMailTo(inscriptionEnligne.getEmail());
		mail.setMailSubject("Inscription Enligne");
		mail.setMailContent("Bonjour Monsieur :" + inscriptionEnligne.getNomFr() +" "
				+ inscriptionEnligne.getPrenomFr() +".\n" +
				"Votre inscription est effectuee avec success." + "\n" + "Le numero de votre inscription est : " + inscriptionEnligne.getId());

		mailService.sendEmail(mail);
		
		
		model.addAttribute("enligne", inscriptionEnligne);
		return "EnregistrementEnligne";
	}	 

	// mis valide_enligne=true
	@GetMapping(path="/validerEnligne")
	public String validerEnligne(Model model,Long id ,
			@RequestParam(value="bar", required = true , defaultValue = "true")
	boolean bar) {
		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id).get();
		enligne.setValideEnligne(bar);
		inscriptionEnligneRepository.save(enligne);
		model.addAttribute("bar", bar);
		model.addAttribute("mode", "true");
		
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
	//Valider InscriptionEnligne :
	
	@GetMapping(path="/administrativeAll") 
	public String listAdministrativeAll(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size  
			){
		Page<InscriptionEnligne> pageEnlignes = inscriptionEnligneRepository.findByValideEnligne(PageRequest.of(page, size));

		model.addAttribute("enligne",pageEnlignes.getContent());
		model.addAttribute("pages",new int[pageEnlignes.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("size",size);
		
		return "confirmAdmini";
	}

}
