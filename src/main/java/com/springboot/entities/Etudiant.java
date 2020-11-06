package com.springboot.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Etudiant {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_etudiant;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_Etape")
	private Etape etape;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_Filiere")
	private Filiere filiere;
	private String prenom_etud;
	private String nom_etud;
	private String tel_etud;
	
	
}
