// package com.springboot.web;

// import java.util.List;

// import java.io.ByteArrayInputStream;
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.ArrayList;
// import java.util.Date;
// import java.util.List;
// import java.util.Optional;

// import javax.validation.Valid;

// import com.springboot.dao.EtudiantRepository;
// import com.springboot.dao.InscriptionAdministrativeRepository;
// import com.springboot.dao.InscriptionEnligneRepository;

// import com.springboot.dao.ModuleRepository;
// import com.springboot.dao.SemestreRepository;

// import com.springboot.entities.Etudiant;
// import com.springboot.entities.InscriptionAdministrative;

// import com.springboot.entities.InscriptionEnligne;

// import com.springboot.entities.Module;
// import com.springboot.entities.Semestre;

// import com.springboot.service.ImportXLSX;

// import org.apache.poi.ss.usermodel.Row;
// import org.apache.poi.xssf.usermodel.XSSFSheet;
// import org.apache.poi.xssf.usermodel.XSSFWorkbook;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestMethod;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;

// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.springboot.dao.EtapeRepository;
// import com.springboot.dao.EtudiantRepository;
// import com.springboot.dao.FiliereRepository;
// import com.springboot.dao.InscriptionAdministrativeRepository;
// import com.springboot.dao.InscriptionEnligneRepository;
// import com.springboot.entities.Etape;
// import com.springboot.entities.Filiere;
// import com.springboot.entities.InscriptionAdministrative;
// import com.springboot.entities.InscriptionEnligne;

// @org.springframework.stereotype.Controller
// public class Controller {

// 	@Autowired
// 	InscriptionEnligneRepository inscriptionEnligneRepository;
// 	@Autowired
// 	InscriptionAdministrativeRepository inscriptionAdministrativeRepository;
// 	@Autowired
// 	EtudiantRepository etudiantRepository;
// 	@Autowired
// 	FiliereRepository filiereRepository;

// 	// list administrativesAll
// 	@GetMapping(path = "/administrativesAll")
// 	public String administrativesAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
// 			@RequestParam(name = "size", defaultValue = "5") int size,
// 			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
// 		Page<InscriptionAdministrative> pageadministratives = inscriptionAdministrativeRepository
// 				.findAll(PageRequest.of(page, size));
// 		model.addAttribute("administratives", pageadministratives.getContent());
// 		model.addAttribute("pages", new int[pageadministratives.getTotalPages()]);
// 		model.addAttribute("currentPage", page);
// 		model.addAttribute("keyword", keyword);
// 		model.addAttribute("size", size);
// 		return "ListAdmin";
// 	}

// 	// import etudiants
// 	@PostMapping(path = "/importStudents")
// 	public String importetudiants(Model model, @RequestParam("file") MultipartFile reapExcelDataFile) {

// 		ImportXLSX imp = new ImportXLSX();
// 		List<Etudiant> le = null;
// 		try {
// 			le = imp.readXLSX(reapExcelDataFile.getBytes());
// 		} catch (IOException e) {
// 			// TODO Auto-generated catch block
// 			e.printStackTrace();
// 		}
// 		etudiantRepository.saveAll(le);

// 		model.addAttribute("imports", le);
// 		return "redirect:/etudiantsAll";
// 	}

// 	// list etudiantsAll
// 	@GetMapping(path = "/etudiantsAll")
// 	public String etudiantsAll(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
// 			@RequestParam(name = "size", defaultValue = "5") int size,
// 			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
// 		Page<Etudiant> pageEtudiants = etudiantRepository.findAll(PageRequest.of(page, size));
// 		model.addAttribute("etudiants", pageEtudiants.getContent());
// 		model.addAttribute("pages", new int[pageEtudiants.getTotalPages()]);
// 		model.addAttribute("currentPage", page);
// 		model.addAttribute("keyword", keyword);
// 		model.addAttribute("size", size);
// 		return "ListEtudiant";
// 	}

// 	// list etudiants
// 	@GetMapping(path = "/etudiants")
// 	public String etudiants(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
// 			@RequestParam(name = "size", defaultValue = "5") int size,
// 			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
// 		Page<Etudiant> pageEtudiants = etudiantRepository.findByNom_etudContains(keyword, PageRequest.of(page, size));
// 		model.addAttribute("etudiants", pageEtudiants.getContent());
// 		model.addAttribute("pages", new int[pageEtudiants.getTotalPages()]);
// 		model.addAttribute("currentPage", page);
// 		model.addAttribute("keyword", keyword);
// 		model.addAttribute("size", size);
// 		return "ListEtudiant";
// 	}

// 	// filieres
// 	@GetMapping(path = "/filiere")
// 	public String filiere(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
// 			@RequestParam(name = "size", defaultValue = "5") int size,
// 			@RequestParam(name = "keyword", defaultValue = "") String keyword) {
// 		Page<Filiere> pagefiliere = filiereRepository.findAll(PageRequest.of(page, size));
// 		model.addAttribute("filiere", pagefiliere.getContent());
// 		model.addAttribute("pages", new int[pagefiliere.getTotalPages()]);
// 		model.addAttribute("currentPage", page);
// 		model.addAttribute("keyword", keyword);
// 		model.addAttribute("size", size);
// 		return "ListFiliere";
// 	}

// }
