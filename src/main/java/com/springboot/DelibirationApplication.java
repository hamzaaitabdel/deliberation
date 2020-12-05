package com.springboot;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.springboot.dao.*;

@SpringBootApplication
public class DelibirationApplication implements CommandLineRunner{

	@Autowired
	private FiliereRepository filiereRepository;
	@Autowired
	private ResponsableFiliereRepository responsableFiliereRepository;
	@Autowired
	private InscriptionEnligneRepository inscriptionRepository;
	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private EtudiantRepository etudiantRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(DelibirationApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		/*
		  ResponsableFiliere responsableFiliere = new ResponsableFiliere();
		  responsableFiliere.setNom_resp_fl("bekri");
		  
		  Filiere filiere = new Filiere(); filiere.setNom_filiere("BGI");
		  
		  responsableFiliere.setFiliere(filiere);
		  filiere.setResponsableFiliere(responsableFiliere);
		  
		  responsableFiliereRepository.save(responsableFiliere);
		
		*/
	}
} 
