package com.springboot.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.springboot.service.ImportXLSX;

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
	EtablissementRepository etablissementRepository;

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


	//@RequestParam(name = "id_filiere" , defaultValue = "1")Long id_filiere
	@GetMapping(path="/ConfirmationAdmin")
	public String ConfirmationAdmin(Model model , Long id_enligne,String cne ,
			String nom,
			String prenom,
			String email,
			String tel
			) {

		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id_enligne).get();
		model.addAttribute("enligne", enligne);

		List<Filiere> f = filiereRepository.findAll();

		List<AnneeUniversitaire> u = anneeUniversitaireRepository.findAll();

		List<Etablissement> le = etablissementRepository.findAll();

		model.addAttribute("id", id_enligne);
		model.addAttribute("cne", cne);
		model.addAttribute("nom", nom);
		model.addAttribute("prenom", prenom);
		model.addAttribute("tel", tel);
		model.addAttribute("email", email);

		model.addAttribute("annee", u);
		model.addAttribute("filiere", f);
		model.addAttribute("etablissement", le);
		model.addAttribute("mode", "new");

		return "ConfirmationAdmin";
	}


	@PostMapping(path="/updateAdmin")
	public String updateAdmin(Model model ,

			
			@RequestParam(name="id")Long id,
			@RequestParam(name="cne")String cne,
			@RequestParam(name="nom")String nom,
			
			@RequestParam(name="emailEtud")String emailE,
			@RequestParam(name="emailParent")String emailP,
			@RequestParam(name="adressePersonnel")String address,
			//@RequestParam(name="dateInscriptionValide")String date,
			@RequestParam(name="ville")String ville,
			
			@RequestParam(name="telephone")String telephone,
			@RequestParam(name="prenom")String prenom,
			@RequestParam(name="filiere")String id_filiere,
			@RequestParam(name="id_enligne")Long id_enligne,
			@RequestParam(name="annee")String annee,
			@RequestParam(name="etablissement")String etabli,
			@RequestParam(name="doc1")MultipartFile file1,
			@RequestParam(name="doc2")MultipartFile file2
			


			){

		InscriptionAdministrative admin = inscriptionAdministrativeRepository.findById(id).get();
		admin.setId(id);
		admin.setAdressePersonnel(address);
		admin.setFiliere(filiereRepository.findById(Long.parseLong(id_filiere)).get());
		admin.setAnneeUniversitaire(anneeUniversitaireRepository.findById(Long.parseLong(annee)).get());
		admin.setEtablissement(etablissementRepository.findById(Long.parseLong(etabli)).get());
		admin.setCne(cne);
		admin.setDateInscriptionValide(admin.getDateInscriptionValide());
		admin.setVille(ville);
		admin.setTelephone(telephone);
		admin.setEmailEtud(emailE);
		admin.setEmailParent(emailP);
		
		
		
		String filename1 = StringUtils.cleanPath(file1.getOriginalFilename());

		if(filename1.contains(".."))System.out.println("not a valid file");
		try {
			admin.setFile1(Base64.getEncoder().encodeToString(file1.getBytes()));
		} catch (IOException io) {
			// TODO Auto-generated catch block
			io.printStackTrace();
		}
		String filename2 = StringUtils.cleanPath(file2.getOriginalFilename());
		if(filename2.contains(".."))System.out.println("not a valid file");
		try {
			admin.setFile2(Base64.getEncoder().encodeToString(file2.getBytes()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		admin.setPrenom(prenom);
		admin.setNom(nom);
		inscriptionAdministrativeRepository.UpdateAdmin(admin, id);
		model.addAttribute("admin", admin);
		return "redirect:/adminsAll";
	}

	@RequestMapping(path="/infoAdmin")
	public String inscriptionEnligne(Model model, Long id) {
		InscriptionAdministrative admin = inscriptionAdministrativeRepository.findById(id).get();
		model.addAttribute("admin", admin);
		return "InformationAdmin";
	}

	@GetMapping(path="/admins") 
	public String listAdmin(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {

		Page<InscriptionAdministrative> pageAdmins = inscriptionAdministrativeRepository.findByNomContains("%"+keyword+"%", PageRequest.of(page, size));
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
	public String deleteAdmin(Long id ,int page, int size, String keyword) {
		inscriptionAdministrativeRepository.DeleteIDIA(id);
		return "redirect:/adminsAll?page="+ page + "&size="+size + "&keyword=" + keyword;
	}


	@GetMapping(path="/editAdmin") 
	public String editAdmin(Model model,Long id , Long id_enligne,String cne) {
		InscriptionAdministrative admin = inscriptionAdministrativeRepository.findById(id).get();
		model.addAttribute("admin", admin);
		
		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id_enligne).get();
		model.addAttribute("enligne", enligne);

		model.addAttribute("id", id_enligne);
		model.addAttribute("cne", cne);
		
		List<Filiere> f = filiereRepository.findAll();

		List<AnneeUniversitaire> u = anneeUniversitaireRepository.findAll();

		List<Etablissement> le = etablissementRepository.findAll();


		model.addAttribute("annee", u);
		model.addAttribute("filiere", f);
		model.addAttribute("etablissement", le);
		model.addAttribute("mode","edit");

		return "EditAdmin"; 
	}
	


	@PostMapping(path="/saveAdmin")
	public String saveAdmin(Model model ,
			@RequestParam("cne")String cne,
			@RequestParam("nom")String nom,
			@RequestParam("prenom")String prenom,
			@RequestParam("filiere")Long id_filiere,
			@RequestParam("id_enligne")Long id_enligne,
			@RequestParam("annee")Long annee,
			@RequestParam("etablissement")Long etabli,
			@RequestParam("doc1")MultipartFile file1,
			@RequestParam("doc2")MultipartFile file2,
			@Valid InscriptionAdministrative admin,
			BindingResult bindingResult


			){
		if(bindingResult.hasErrors()) return "ConfirmationAdmin";


		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id_enligne).get();
		admin.setInscriptionEnligne(enligne);

		Filiere f = filiereRepository.findById(id_filiere).get();
		admin.setFiliere(f);

		AnneeUniversitaire a = anneeUniversitaireRepository.findById(annee).get();
		admin.setAnneeUniversitaire(a);

		Etablissement e = etablissementRepository.findById(etabli).get();
		admin.setEtablissement(e);


		String filename1 = StringUtils.cleanPath(file1.getOriginalFilename());

		if(filename1.contains(".."))System.out.println("not a valid file");
		try {
			admin.setFile1(Base64.getEncoder().encodeToString(file1.getBytes()));
		} catch (IOException io) {
			// TODO Auto-generated catch block
			io.printStackTrace();
		}
		String filename2 = StringUtils.cleanPath(file2.getOriginalFilename());
		if(filename2.contains(".."))System.out.println("not a valid file");
		try {
			admin.setFile2(Base64.getEncoder().encodeToString(file2.getBytes()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		inscriptionAdministrativeRepository.save(admin);
		return "redirect:/adminsAll";
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


	// list etudiantsAll
	@GetMapping(path = "/etudiantsAll")
	public String etudiantsAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		Page<Etudiant> pageEtudiants = etudiantRepository.findAll(PageRequest.of(page, size));
		model.addAttribute("etudiants", pageEtudiants.getContent());
		model.addAttribute("pages", new int[pageEtudiants.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("size", size);
		return "ListEtudiant";
	}

	// list etudiants
	@GetMapping(path = "/etudiants")
	public String etudiants(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		Page<Etudiant> pageEtudiants = etudiantRepository.findByNomEtudContains(keyword, PageRequest.of(page, size));
		model.addAttribute("etudiants", pageEtudiants.getContent());
		model.addAttribute("pages", new int[pageEtudiants.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("size", size);
		return "ListEtudiant";
	}

	@GetMapping(path = "/editEtudiant")
	public String editEtudiant(Model model, String cne) {
		Etudiant e = etudiantRepository.findById(cne).get();
		List<Filiere> lf = filiereRepository.findAll();

		model.addAttribute("etudiant", e);
		model.addAttribute("filieres", lf);

		return "/editEtudiant";
	}

	@PostMapping(path = "/confirmEditEtudiant")
	public String confirmEditEtudiant(@RequestParam(name = "cne") String cne, @RequestParam(name = "nom") String nom,
			@RequestParam(name = "prenom") String prenom, @RequestParam(name = "email") String email,
			@RequestParam(name = "tel") String tel, @RequestParam(name = "filiere") String filiere) {

		Etudiant e = etudiantRepository.findById(cne).get();
		e.setCne(cne);
		e.setNom(nom);
		e.setPrenom(prenom);
		e.setEmail(email);
		e.setFiliere(filiereRepository.findById(Long.parseLong(filiere)).get());
		
		etudiantRepository.UpdateEtudiant(e, cne);

		return "redirect:/etudiantsAll";
	}
	
	 //list administrativesAll
	@GetMapping(path = "/administrativesAll")
	public String administrativesAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "5") int size,
			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
		Page<InscriptionAdministrative> pageadministratives = inscriptionAdministrativeRepository
				.findAll(PageRequest.of(page, size));
		model.addAttribute("administratives", pageadministratives.getContent());
		model.addAttribute("pages", new int[pageadministratives.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("size", size);
		return "ListAdmin";
	}
	
	// import etudiants
	@PostMapping(path = "/importetudiants")
	public String importetudiants(Model model, @RequestParam("file") MultipartFile reapExcelDataFile) {

		ImportXLSX imp = new ImportXLSX();
		List<Etudiant> le = null;
		try {
			le = imp.readXLSX(reapExcelDataFile.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		etudiantRepository.saveAll(le);

		model.addAttribute("imports", le);
		return "redirect:/etudiantsAll";
	}
	

	
}

