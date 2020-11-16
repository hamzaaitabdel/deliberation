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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.dao.EtudiantRepository;
import com.springboot.dao.InscriptionEnligneRepository;
import com.springboot.entities.InscriptionEnligne;


@org.springframework.stereotype.Controller
public class Controller {

	@GetMapping("/index")
	public String index() {
		return "index";
	}
	@GetMapping("/profile")
	public String InscriptionOnLigne() {
		return "profile.html";
	}
	@GetMapping("/taples")
	public String Confirmation() {
		return "tables.html";
	}
	/*
	@GetMapping(path="/clients") 
	public String listClient(Model model ,
			@RequestParam(name="page",defaultValue = "0")int page ,
			@RequestParam(name="size",defaultValue = "5")int size , 
			@RequestParam(name="keyword",defaultValue = "")String keyword) {
		Page<Client> pageClients = clientRepository.findByNameContains(keyword,PageRequest.of(page, size));
		model.addAttribute("clients",pageClients.getContent());
		model.addAttribute("pages",new int[pageClients.getTotalPages()]);
		model.addAttribute("currentPage",page);
		model.addAttribute("keyword",keyword);
		model.addAttribute("size",size);
		return "clients";
	} 

	@GetMapping(path="/deleteClient")
	public String deleteClient(Long id , int page , int size , String keyword) {
		clientRepository.deleteById(id);
		return "redirect:/clients?page="+page+"&size="+size+"&keyword="+keyword;
	}
	@GetMapping(path="/formClient")
	public String formPatient(Model model) {
		//model.addAttribute("patient", new Patient(null,"qwertz",null,false,7));
		model.addAttribute("client", new Client());
		model.addAttribute("mode", "new");
		return "formClient";
	}
*/

	@Autowired
	InscriptionEnligneRepository inscriptionEnligneRepository;
	
	@RequestMapping(path="/enligne")
	public String inscriptionEnligne(Model model) {
		model.addAttribute("enligne", new InscriptionEnligne());
		model.addAttribute("mode", "new");
		return "formEnligne";
	}
	
	@GetMapping(path="/saveEnligne")
	public String saveEnligne(Model model,@Valid InscriptionEnligne inscriptionEnligne,BindingResult bindingResult,
			MultipartFile file) throws IOException{
		if(bindingResult.hasErrors()) return "formEnligne";
		if(!file.isEmpty()) {
			BufferedImage image = ImageIO.read(file.getInputStream());
			inscriptionEnligne.setPhoto(file.getBytes());
		}
		inscriptionEnligneRepository.save(inscriptionEnligne);
		model.addAttribute("enligne", inscriptionEnligne);
		return "confirmation_enligne";
	}
	@GetMapping(path="/photoEtud",produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] photoEtud(Long id) throws IOException{
		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id).get();
		
		return IOUtils.toByteArray(new ByteArrayInputStream(enligne.getPhoto()));
		
	}
	
	@GetMapping(path="/editEnligne")
	public String editClient(Model model,Long id) {
		InscriptionEnligne enligne = inscriptionEnligneRepository.findById(id).get();
		model.addAttribute("enligne", enligne);
		model.addAttribute("mode", "edit");
		return "formEnligne";
	}
}
