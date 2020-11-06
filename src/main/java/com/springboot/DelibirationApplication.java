package com.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.springboot.dao.*;
import com.springboot.entities.*;

@SpringBootApplication
public class DelibirationApplication implements CommandLineRunner{

	@Autowired
	private FiliereRepository filiereRepository;
	@Autowired
	private ResponsableFiliereRepository responsableFiliereRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(DelibirationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/*
		 * ResponsableFiliere responsableFiliere = new ResponsableFiliere();
		 * responsableFiliere.setNom_resp_fl("bekri");
		 * 
		 * Filiere filiere = new Filiere(); filiere.setNom_filiere("BGI");
		 * 
		 * responsableFiliere.setFiliere(filiere);
		 * filiere.setResponsableFiliere(responsableFiliere);
		  
		  responsableFiliereRepository.save(responsableFiliere);
		 */
	}
} 
