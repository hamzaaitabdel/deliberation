package com.springboot.entities;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor 
public class Filiere {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etablissement")
	private Etablissement etablissement;
	
	@OneToMany(mappedBy = "filiere" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<InscriptionAdministrative> inscriptionAdministratives;
	
//	@OneToMany(mappedBy = "filiere" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	List<InscriptionPedagogique> inscriptionPedagogiques;
//	
	@OneToMany(mappedBy = "filiere" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<Etudiant> etudiants;
	
	@OneToMany(mappedBy = "filiere" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<Etape> etapes;
	
	
}
