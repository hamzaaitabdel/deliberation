package com.springboot.entities;

import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class InscriptionAdministrative {
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDIA" )
	private Long id;
	
	private String cne;
	private String nom;
	private String prenom;
	private String adressePersonnel;
	private String ville;
	private String emailEtud;
	private String emailParent;
	private String telephone;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateInscriptionValide;
		
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_filiere")
	private Filiere filiere;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etablissement")
	private Etablissement etablissement;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "annee_universitaire")
	AnneeUniversitaire anneeUniversitaire;
	
	@OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "id_enligne")
	private InscriptionEnligne inscriptionEnligne;

	
}
