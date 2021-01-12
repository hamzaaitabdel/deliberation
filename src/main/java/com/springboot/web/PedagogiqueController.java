package com.springboot.web;


import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.springboot.dao.AnneeUniversitaireRepository;
import com.springboot.dao.ElementRepository;
import com.springboot.dao.EtapeRepository;
import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.FiliereRepository;
import com.springboot.dao.InscriptionAdministrativeRepository;
import com.springboot.dao.InscriptionEnligneRepository;
import com.springboot.entities.AnneeUniversitaire;
import com.springboot.entities.Element;
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
	@Autowired
	AnneeUniversitaireRepository anneeUniversitaireRepository;


	//Inscription pedagogique

	@GetMapping(path="/importStudents")
	public String importStudents(){
		
		return "importStudents";
	}

	//import etudiants
	@PostMapping(path="/importetudiants") 
	public String importetudiants(Model model,@RequestParam("file") MultipartFile reapExcelDataFile) {
		
		
		ImportXLSX imp = new ImportXLSX();
		
		try {
			imp.readXLSX(reapExcelDataFile.getBytes(),anneeUniversitaireRepository,filiereRepository,inscriptionEnligneRepository,inscriptionAdministrativeRepository,etudiantRepository);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			@RequestParam(name="oldcne")String oldcne,
			@RequestParam(name="nom")String nom,
			@RequestParam(name="prenom")String prenom,
			@RequestParam(name="email")String email,
			@RequestParam(name="tel")String tel,
			@RequestParam(name="filiere")String filiere) {

		Etudiant e=etudiantRepository.findById(oldcne).get();
		e.setCne(cne);
		e.setNom(nom);
		e.setPrenom(prenom);
		e.setEmail(email);
		//e.setTel_etud(tel);
		e.setFiliere(filiereRepository.findById(Long.parseLong(filiere)).get());
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
		etudiantRepository.UpdateEtudiant(e,oldcne);

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

	// etudiants par filieres
	@GetMapping(path="/etudiantsParFiliere") 
	public String etudiantsParFiliere(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword,
			Long id) {
		Page<Etudiant> pageetudiants = etudiantRepository.findById_FiliereContains(id,PageRequest.of(page, size));
		model.addAttribute("etudiants",pageetudiants.getContent());
		model.addAttribute("pages",new int[pageetudiants.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",keyword);
		model.addAttribute("size",size);
		return "ListEtudiantParFiliere";
	}

	//inscrire pedagogiquement
	@GetMapping(path="/inscrirePedagogiquement") 
	public String inscrirePedagogiquement(Model model, String cne) {
		Etudiant e = etudiantRepository.findById(cne).get();

		List<Etape> et= etapeRepository.findById_filiereContains(e.getFiliere().getId());

		model.addAttribute("etudiant", e);
		model.addAttribute("etapes", et);

		return "formPedagogique";
	}

	//inscrire pedagogiquement
	@PostMapping(path="/inscrirePedagogiquement2") 
	public String inscrirePedagogiquement2(Model model, @RequestParam(name="cne")String cne
			,@RequestParam(name="nom_filiere")String nom_filiere,
			@RequestParam(name="etape")String etape) {
		Etudiant e = etudiantRepository.findById(cne).get();

		String idetape="";
		System.out.println("etape: "+etape);
		for(int i=0;i<etape.length();i++) {
			if(etape.charAt(i)!='|')  
				idetape=idetape.concat(""+etape.charAt(i));
			else if(etape.charAt(i)=='|') 
				break;
		}

		List<Semestre> ls = semestreRepository.findById_etapeContains(Long.parseLong(idetape));
		Etape et = etapeRepository.findById(Long.parseLong(idetape)).get();
		model.addAttribute("etudiant", e);
		model.addAttribute("semestres", ls);
		model.addAttribute("etape",et);

		return "formPedagogique2";
	}

	//inscrire pedagogiquement
	@PostMapping(path="/inscrirePedagogiquement3") 
	public String inscrirePedagogiquement3(Model model, @RequestParam(name="cne")String cne
			,@RequestParam(name="nom_filiere")String nom_filiere,
			@RequestParam(name="etape")String etape,
			@RequestParam(name="semestre")String semestre) {
		Etudiant e = etudiantRepository.findById(cne).get();

		String idsemestre="";
		System.out.println("semestre: "+semestre);
		for(int i=0;i<semestre.length();i++) {
			if(semestre.charAt(i)!='|')  
				idsemestre=idsemestre.concat(""+semestre.charAt(i));
			else if(semestre.charAt(i)=='|') 
				break;
		}

		Semestre s = semestreRepository.findById(Long.parseLong(idsemestre)).get();
		List<Module> ls = moduleRepository.findById_semestreContains(Long.parseLong(idsemestre));
		model.addAttribute("etudiant", e);
		model.addAttribute("modules", ls);
		model.addAttribute("etape",etape);
		model.addAttribute("semestre", s);

		return "formPedagogique3";
	}

	//inscrire pedagogiquement
	@PostMapping(path="/inscrirePedagogiquement4") 
	public String inscrirePedagogiquement4(Model model, @RequestParam(name="cne")String cne
			,@RequestParam(name="nom_filiere")String nom_filiere,
			@RequestParam(name="etape")String etape,
			@RequestParam(name="semestre")String semestre,
			@RequestParam(name="module")String module) {
		Etudiant e = etudiantRepository.findById(cne).get();

		String idmodule="";
		System.out.println("module: "+module);
		for(int i=0;i<module.length();i++) {
			if(module.charAt(i)!='|')  
				idmodule=idmodule.concat(""+module.charAt(i));
			else if(module.charAt(i)=='|') 
				break;
		}

		List<AnneeUniversitaire> la = anneeUniversitaireRepository.findAll();
		Module m = moduleRepository.findById(Long.parseLong(idmodule)).get();
		List<Element> ls = elementRepository.findById_moduleContains(Long.parseLong(idmodule));
		model.addAttribute("etudiant", e);
		model.addAttribute("elements", ls);
		model.addAttribute("etape",etape);
		model.addAttribute("semestre", semestre);
		model.addAttribute("module", m);
		model.addAttribute("annees", la);

		return "formPedagogique4";
	}

	@Autowired
	ElementRepository elementRepository;


	//inscrire pedagogiquement
	@PostMapping(path="/inscrirePedagogiquement5") 
	public String inscrirePedagogiquement5(Model model, @RequestParam(name="cne")String cne
			,@RequestParam(name="nom_filiere")String nom_filiere,
			@RequestParam(name="etape")String etape,
			@RequestParam(name="semestre")String semestre,
			@RequestParam(name="module")String module,
			@RequestParam(name="id_module")Long id_module,
			@RequestParam(name="annee")String annee,
			HttpServletRequest request) {
		
		String idannee="";
		System.out.println("annee: "+annee);
		for(int i=0;i<annee.length();i++) {
			if(annee.charAt(i)!='|')  
				idannee=idannee.concat(""+annee.charAt(i));
			else if(annee.charAt(i)=='|') 
				break;
		}
		
		AnneeUniversitaire au = anneeUniversitaireRepository.findById(Long.parseLong(idannee)).get();
		
		Etudiant e = etudiantRepository.findById(cne).get();
		List<Element> le = elementRepository.findById_moduleContains(id_module);
		List<String> id_el=new ArrayList<String>();
		for(int i=0;i<le.size();i++) {
			System.out.println("id element before: "+le.get(i).getId());
			String n = request.getParameter(le.get(i).getName()+"");
			System.out.println("id element: "+n);
			if(n!=null)	id_el.add(n);
		}
		
		for(int i=0;i<id_el.size();i++) {
			InscriptionPedagogique ip = new InscriptionPedagogique();
			ip.setEtudiant(e);
			Element el = elementRepository.findById(Long.parseLong(id_el.get(i))).get();
			ip.setElement(el);
			LocalDate todayLocalDate = LocalDate.now( ZoneId.of( "GMT+1" ) );
			java.sql.Date sqlDate = java.sql.Date.valueOf( todayLocalDate );
			ip.setDateInscriptionValide(sqlDate);
			ip.setAnneeUniversitaire(au);
			inscriptionPedagogiqueRepository.save(ip);
		}
		
		return "redirect:/listePedago";
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
		//ip.setModule(m);

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
		Element el = elementRepository.findById(ip.getElement().getId()).get();
		List<Element> lm=elementRepository.findById_moduleContains(el.getModule().getId());
		List<AnneeUniversitaire> lu = anneeUniversitaireRepository.findAll();
		model.addAttribute("ip", ip);
		model.addAttribute("elements", lm);
		model.addAttribute("annees", lu);


		return "editPedago";
	}

	@PostMapping(path="/confirmEditPedago")
	public String confirmEditPedago(@RequestParam(name="id")Long id,
			@RequestParam(name="element")String mod,
			@RequestParam(name="annee")String annee) {
		InscriptionPedagogique ip = inscriptionPedagogiqueRepository.findById(id).get();
		Element m = elementRepository.findById(Long.parseLong(mod)).get();
		String idannee="";
		System.out.println("annee: "+annee);
		for(int i=0;i<annee.length();i++) {
			if(annee.charAt(i)!='|')  
				idannee=idannee.concat(""+annee.charAt(i));
			else if(annee.charAt(i)=='|') 
				break;
		}
		
		AnneeUniversitaire au = anneeUniversitaireRepository.findById(Long.parseLong(idannee)).get();
		inscriptionPedagogiqueRepository.UpdateId_element(m,au,id);
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
