package com.springboot.web;


import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.InscriptionAdministrativeRepository;
import com.springboot.dao.InscriptionEnligneRepository;
import com.springboot.dao.InscriptionPedagogiqueRepository;
import com.springboot.dao.ModuleRepository;
import com.springboot.dao.SemestreRepository;

import com.springboot.entities.Etudiant;
import com.springboot.entities.InscriptionAdministrative;

import com.springboot.entities.InscriptionEnligne;
import com.springboot.entities.InscriptionPedagogique;
import com.springboot.entities.Module;
import com.springboot.entities.Semestre;

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

import com.springboot.dao.EtapeRepository;
import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.FiliereRepository;
import com.springboot.dao.InscriptionAdministrativeRepository;
import com.springboot.dao.InscriptionEnligneRepository;
import com.springboot.entities.Etape;
import com.springboot.entities.Filiere;
import com.springboot.entities.InscriptionAdministrative;
import com.springboot.entities.InscriptionEnligne;


@org.springframework.stereotype.Controller
public class PedagogiqueController {

	@Autowired
	InscriptionEnligneRepository inscriptionEnligneRepository;
	@Autowired
	InscriptionAdministrativeRepository inscriptionAdministrativeRepository;
	@Autowired
	EtudiantRepository etudiantRepository;
	@Autowired
	FiliereRepository filiereRepository;



	//Inscription pedagogique


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
	
	@GetMapping(path="/editEtudiant")
	public String editEtudiant(Model model, String cne) {
		Etudiant e =etudiantRepository.findById(cne).get();
		List<Filiere> lf = filiereRepository.findAll();
		
		model.addAttribute("etudiant", e);
		model.addAttribute("filieres", lf);
		
		return "/editEtudiant";
	}
	
	@PostMapping(path="/confirmEditEtudiant")
	public String confirmEditEtudiant(@RequestParam(name="cne")String cne,
			@RequestParam(name="nom")String nom,
			@RequestParam(name="prenom")String prenom,
			@RequestParam(name="email")String email,
			@RequestParam(name="tel")String tel,
			@RequestParam(name="filiere")String filiere) {
		
		Etudiant e=etudiantRepository.findById(cne).get();
		e.setCne(cne);
		e.setNom(nom);
		e.setPrenom(prenom);
		e.setEmail(email);
		//e.setTel_etud(tel);
		e.setFiliere(filiereRepository.findById_filiere(Long.parseLong(filiere)));
		/*
		InscriptionEnligne il = inscriptionEnligneRepository.findById(cne).get();
		il.setCne(cne);
		il.setNomFr(nom);
		il.setPrenomFr(prenom);
		//il.setTel(tel);
		//there is a tel duplicate in insc en ligne and admin
		/* the id of insc admin should be the cne 
		 * amirite? */
		//InscriptionAdministrative ai = inscriptionAdministrativeRepository.findById(id);
		etudiantRepository.UpdateEtudiant(e,cne);
		
		return "redirect:/etudiantsAll";
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

//	// etudiants par filieres
//	@GetMapping(path="/etudiantsParFiliere") 
//	public String etudiantsParFiliere(Model model ,
//			@RequestParam(name="page",defaultValue = "0")int page ,
//			@RequestParam(name="size",defaultValue = "5")int size , 
//			@RequestParam(name="keyword",defaultValue = "")String keyword,
//			Long id) {
//		Page<Etudiant> pageetudiants = etudiantRepository.findById_FiliereContains(id,PageRequest.of(page, size));
//		model.addAttribute("etudiants",pageetudiants.getContent());
//		model.addAttribute("pages",new int[pageetudiants.getTotalPages()]);
//		model.addAttribute("currentPage",page);
//		model.addAttribute("keyword",keyword);
//		model.addAttribute("size",size);
//		return "ListEtudiantParFiliere";
//	}

	//inscrire pedagogiquement
	@GetMapping(path="/inscrirePedagogiquement") 
	public String inscrirePedagogiquement(Model model, String cne) {
		Etudiant e = etudiantRepository.findById(cne).get();
		/* multiple etapes can belong to a single filiere so
		 * it would be preferable to fetch a list of etapes,
		 * a list of semeters of these etapes,
		 * and then lists of all modules
		 * or fetch all the modules that belong to a specific filiere
		 * i'm pretty sure it's way better
		 *  */
		List<Etape> et= etapeRepository.findById_filiereContains(e.getFiliere().getId());
		List<Semestre> s = semestreRepository.findById_filiereContains(e.getFiliere().getId());
		List<Module> lm=moduleRepository.findById_filiereContains(e.getFiliere().getId());

		model.addAttribute("etudiant", e);
		model.addAttribute("modules", lm);
		model.addAttribute("etapes", et);
		model.addAttribute("semestres", s); 

		return "formPedagogique";
	}

	@Autowired
	InscriptionPedagogiqueRepository inscriptionPedagogiqueRepository;

	//save pedagogique
	@PostMapping(path="/savePedagogique")
	public String savePedagogique(@RequestParam(name="cne")String cne,
			/*@RequestParam(name="module")String libelle_module,*/
			@RequestParam(name="mod")String mod) {

		Etudiant e = etudiantRepository.findById(cne).get();
		Filiere f = filiereRepository.findById_filiere(e.getFiliere().getId());
		String idmod="";
		System.out.println("mod: "+mod);
		for(int i=0;i<mod.length();i++) {
			
			if(mod.charAt(i)!='|') { 
				idmod=idmod.concat(""+mod.charAt(i));
				
			}
			else if(mod.charAt(i)=='|') {
				break;
			}
		}
		System.out.println("idmod: "+idmod);
		Module m = moduleRepository.findById(Long.parseLong(idmod)).get();
		InscriptionPedagogique ip = new InscriptionPedagogique();
		ip.setEtudiant(e);
		ip.setModule(m);

		inscriptionPedagogiqueRepository.save(ip);

		return "redirect:/listePedago";
	}

	// insc pedago
	@GetMapping(path="/listePedago") 
	public String listePedago(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		Page<InscriptionPedagogique> pageinscpedago = inscriptionPedagogiqueRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("pedagogiques",pageinscpedago.getContent());
		model.addAttribute("pages",new int[pageinscpedago.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",keyword);
		model.addAttribute("size",size);
		return "listePedago";
	}

	// insc pedago
	@GetMapping(path="/editPedago") 
	public String editPedago(Model model, Long id) {
		InscriptionPedagogique ip = inscriptionPedagogiqueRepository.findById(id).get();
		//List<Module> lm=moduleRepository.findById_filiereContains(ip.getFiliere().getId_filiere());

		model.addAttribute("ip", ip);
		//model.addAttribute("modules", lm);


		return "editPedago";
	}

	@PostMapping(path="/confirmEditPedago")
	public String confirmEditPedago(@RequestParam(name="id")Long id,
			@RequestParam(name="module")String mod) {
		Module m = moduleRepository.findById(Long.parseLong(mod)).get();
		inscriptionPedagogiqueRepository.UpdateId_module(m,id);
		return "redirect:/listePedago";
	}
	
	//delete
	@GetMapping(path="/deletePedago")
	public String deletePedago(Long id){
		inscriptionPedagogiqueRepository.DeleteIDIP(id);
		return "redirect:/listePedago";
	}

	@Autowired
	ModuleRepository moduleRepository;
	@Autowired
	EtapeRepository etapeRepository;
	@Autowired
	SemestreRepository semestreRepository;

}
