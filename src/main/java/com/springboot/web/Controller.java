package com.springboot.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.dao.EtudiantRepository;

@RestController
public class Controller {

	EtudiantRepository erud;
	@GetMapping(path="/index")
	public String index() {
		return "index";
	}
	
}
