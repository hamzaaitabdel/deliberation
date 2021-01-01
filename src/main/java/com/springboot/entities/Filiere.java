package com.springboot.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Filiere {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_filiere;
	private String nom_filiere;
	
	
	@OneToMany(mappedBy = "filiere" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<InscriptionAdministrative> inscriptionAdministratives;
	
	@OneToMany(mappedBy = "filiere" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<Etudiant> etudiants;
	
	@OneToMany(mappedBy = "filiere" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<Etape> etapes;
	
	
	
	
}
