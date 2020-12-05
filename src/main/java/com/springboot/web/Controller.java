package com.springboot.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.InscriptionAdministrativeRepository;
import com.springboot.dao.InscriptionEnligneRepository;
import com.springboot.entities.Etudiant;
import com.springboot.entities.InscriptionAdministrative;
import com.springboot.entities.InscriptionEnligne;
import com.springboot.service.ImportXLSX;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;

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
	EtudiantRepository etudiantRepository;
	@Autowired
	FiliereRepository filiereRepository;
	
	/*
	 * @Autowired com.springboot.service.MailService mailService;
	 * 
	 * @GetMapping(path="/send") public String test(String recipient, String
	 * objectif , String message ) { com.springboot.bean.Mail mail = new
	 * com.springboot.bean.Mail(); mail.setMailFrom("ryfysafwane@gmail.com");
	 * mail.setMailTo("ryfy1103.saf1999@gmail.com"); mail.setMailSubject("dfgfgh");
	 * mail.setMailContent(" rghm");
	 * 
	 * mailService.sendEmail(mail);
	 * 
	 * return "email"; }
	 */	
	
	
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
		InscriptionAdministrative ia = new InscriptionAdministrative();
		ia.setInscriptionEnligne(enligne);
		inscriptionAdministrativeRepository.save(ia);
		model.addAttribute("bar", bar);
		model.addAttribute("enligne",enligne);
		return "redirect:/enlignesAll?bar="+bar;
	}
	//Les enregistrements
	@GetMapping(path="/ConfirmationEnligne")
	public String ConfirmationEnligne(Model model,String id) {
		InscriptionEnligne enligne = inscriptionEnligneRepository.findByCne(id);
		model.addAttribute("enligne", enligne);
		return "ConfirmationEnligne";
	}

	//list administrativesAll
	@GetMapping(path="/administrativesAll") 
	public String administrativesAll(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		Page<InscriptionAdministrative> pageadministratives = inscriptionAdministrativeRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("administratives",pageadministratives.getContent());
		model.addAttribute("pages",new int[pageadministratives.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",keyword);
		model.addAttribute("size",size);
		return "ListAdmin";
	}

	//list administratives
	//not working
	@GetMapping(path="/administratives") 
	public String administratives(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
	/*	Page<InscriptionAdministrative> pageadministratives = inscriptionAdministrativeRepository.findByCneContains(keyword,PageRequest.of(page, size));
		model.addAttribute("administratives",pageadministratives.getContent());
		model.addAttribute("pages",new int[pageadministratives.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",keyword);
		model.addAttribute("size",size);*/
		//not working :)
		return "ListAdmin";
	}

	//import etudiants
		@PostMapping(path="/importetudiants") 
		public String importetudiants(Model model,@RequestParam("file") MultipartFile reapExcelDataFile) {

			ImportXLSX imp = new ImportXLSX();
			List<Etudiant> le=null;
			try {
				le=imp.readXLSX(reapExcelDataFile.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			etudiantRepository.saveAll(le);
			
			model.addAttribute("imports", le);
			return "redirect:/etudiantsAll";
		}

		
		
		


	//Valider InscriptionAdmin : fill the etudiant table
	@GetMapping(path="/validerAdministrative")
	public String validerAdministrative(Model model,Long id ) {
		InscriptionAdministrative admin = inscriptionAdministrativeRepository.findById(id).get();
		InscriptionEnligne enligne = inscriptionEnligneRepository.findByCne(admin.getInscriptionEnligne().getCne());
		Etudiant e = new Etudiant();  
		e.setNom_etud(enligne.getNom_fr());
		e.setPrenom_etud(enligne.getPrenom_fr());
		e.setNomAr_etud(enligne.getNom_ar());
		e.setPrenomAr_etud(enligne.getPrenom_ar());
		e.setAcademie(enligne.getAcademie());
		//e.setAnnee_academique(enligne.);
		e.setAnnee_de_bac(enligne.getAnnee_bac());
		e.setCIN(enligne.getCin());
		e.setDate_de_naissance(enligne.getDate_naissance());
		e.setDate_premiere_inscription(admin.getDate_inscription_valide());
		e.setEmail_etud(enligne.getEmail());
		e.setEtat_physique(enligne.getEtat_physique());
		e.setFiliere(admin.getFiliere());
		e.setGroupe_socioprofessionnel(enligne.getGroupe_socioprofessionnel());
		e.setLieu_de_naissance(enligne.getLieu_naissance_fr());
		e.setLieu_de_naissanceAr(enligne.getLieu_naissance_ar());
		e.setLycee_bac(enligne.getLycee_bac());
		e.setMention_de_bac(enligne.getMention_bac());
		e.setNationalite(enligne.getNationalite());
		e.setPhoto(enligne.getPhoto());
		e.setProvince(enligne.getProvince());
		e.setRegion(enligne.getRegion());
		//e.setResultat(enligne.getResultat());
		e.setSerie_bac(enligne.getSerie_bac());
		e.setSexe(enligne.getSexe());
		e.setTel_etud(enligne.getTel());
		e.setVille_bac(enligne.getVille_bac());
		e.setCne(enligne.getCne());

		etudiantRepository.save(e);
		model.addAttribute("enligne",enligne);
		return "redirect:/etudiants";
	}

	//list etudiantsAll
	@GetMapping(path="/etudiantsAll") 
	public String etudiantsAll(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		Page<Etudiant> pageEtudiants = etudiantRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("etudiants",pageEtudiants.getContent());
		model.addAttribute("pages",new int[pageEtudiants.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",keyword);
		model.addAttribute("size",size);
		return "ListEtudiant";
	}

	//list etudiants
		@GetMapping(path="/etudiants") 
		public String etudiants(Model model ,
				@RequestParam(name="page",defaultValue = "0")int page ,
				@RequestParam(name="size",defaultValue = "5")int size , 
				@RequestParam(name="keyword",defaultValue = "")String keyword) {
			Page<Etudiant> pageEtudiants = etudiantRepository.findByNom_etudContains(keyword, PageRequest.of(page, size));
			model.addAttribute("etudiants",pageEtudiants.getContent());
			model.addAttribute("pages",new int[pageEtudiants.getTotalPages()]);
			model.addAttribute("currentPage",page);
			model.addAttribute("keyword",keyword);
			model.addAttribute("size",size);
			return "ListEtudiant";
		}
		
		// filieres
		@GetMapping(path="/filiere") 
		public String filiere(Model model ,
				@RequestParam(name="page",defaultValue = "0")int page ,
				@RequestParam(name="size",defaultValue = "5")int size , 
				@RequestParam(name="keyword",defaultValue = "")String keyword) {
			Page<Filiere> pagefiliere = filiereRepository.findAll(PageRequest.of(page, size));
			model.addAttribute("filiere",pagefiliere.getContent());
			model.addAttribute("pages",new int[pagefiliere.getTotalPages()]);
			model.addAttribute("currentPage",page);
			model.addAttribute("keyword",keyword);
			model.addAttribute("size",size);
			return "ListFiliere";
		}
		
	

	/*	
	@GetMapping(path="/saveEnligne")
	public String saveEnligne(Model model,@Valid InscriptionEnligne inscriptionEnligne,BindingResult bindingResult,
			MultipartFile file) throws IOException{
		if(bindingResult.hasErrors()) return "bootstrap-form";
		if(!file.isEmpty()) {
			BufferedImage image = ImageIO.read(file.getInputStream());
			inscriptionEnligne.setPhoto(file.getBytes());
=======
	
		//2.Inscription Administrative
	
	//Les enregistrements avec Insertion Admin
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
>>>>>>> branch 'master' of https://github.com/hamzaaitabdel/deliberation
		}
<<<<<<< HEAD
		inscriptionEnligneRepository.save(inscriptionEnligne);
		model.addAttribute("enligne", inscriptionEnligne);
		return "index-0";
	}
	@GetMapping(path="/photoEtud",produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] photoEtud(Long id) throws IOException{
		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id).get();

		return IOUtils.toByteArray(new ByteArrayInputStream(enligne.getPhoto()));

=======
//		
//		//Validation 
//		@RequestMapping(path="/saveAdmin" , method = RequestMethod.POST)
//		public String saveAdmin(@Valid InscriptionEnligne enligne,@Valid Filiere filiere,
//				@Valid InscriptionAdministrative admin,
//				BindingResult bindingResult){
//			if(bindingResult.hasErrors()) return "formAdmin";
//			
//			//InscriptionEnligne enligne = inscriptionEnligneRepository.getOne(id);
//			//id = inscriptionEnligneRepository.findByCne(id).getCne();
//			//Filiere filiere = filiereRepository.getOne(id_filiere);
//			//id_filiere = filiereRepository.findById(id_filiere).get().getId_filiere();
//			
//			//filiere.setId_filiere(id_filiere);
//			//enligne.setCne(id);
//			//admin.setInscriptionEnligne(enligne);
//			//admin.setFiliere(filiere);
//			inscriptionAdministrativeRepository.save(admin);
//			model.addAttribute("admin", admin);
//			model.addAttribute("enligne", enligne);
//			model.addAttribute("filiere", filiere);
//			
//			model.addAttribute("cne", id);
//			
//			return "redirect:/adminsAll";
//		}
//		

		//Validation 
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

		
		//Edition
		@RequestMapping(path="/editAdmin")
		public String editAdmin(Model model , Long id,String cne 
				) {
			InscriptionAdministrative administrative = inscriptionAdministrativeRepository.findById(id).get();
			
			model.addAttribute("admin", administrative);
			InscriptionEnligne enligne =inscriptionEnligneRepository.findById(cne).get();
			model.addAttribute("enligne", enligne);
			cne =inscriptionEnligneRepository.findByCne(cne).getCne();

			model.addAttribute("cne", cne);
			
			model.addAttribute("mode", "edit");
			return "ConfirmationAdmin";//Encore des problemes ici , pas encore fini, il fait pas l'edition , il fait l'ajout
		}
	/*
	 * //Edition
	 * 
	 * @RequestMapping(path="/editAdmin") public String editAdmin(Model model , Long
	 * id , @RequestParam("cne")String cne
	 * ,@RequestParam(name="filiere",defaultValue = "1")Long id_filiere ) {
	 * InscriptionAdministrative administrative =
	 * inscriptionAdministrativeRepository.getOne(id);
	 * 
	 * model.addAttribute("admin", administrative); InscriptionEnligne enligne =
	 * inscriptionEnligneRepository.getOne(cne); cne =
	 * inscriptionEnligneRepository.findByCne(cne).getCne(); //Filiere filiere =
	 * filiereRepository.getOne(id); id_filiere =
	 * filiereRepository.findById(id_filiere).get().getId_filiere();
	 * model.addAttribute("enligne", enligne);
	 * model.addAttribute("filiere",id_filiere); model.addAttribute("cne", cne);
	 * model.addAttribute("mode", "edit"); return "ConfirmationAdmin";//Encore des
	 * problemes ici , pas encore fini, il fait pas l'edition , il fait l'ajout }
	 * 
	 */
		
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
		return "ListAdmin";
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
