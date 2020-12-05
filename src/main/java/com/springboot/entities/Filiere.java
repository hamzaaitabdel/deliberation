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
	
	@OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL , mappedBy = "filiere")
	private ResponsableFiliere responsableFiliere;
	
	@OneToMany(mappedBy = "filiere" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<InscriptionAdministrative> inscriptionAdministratives;
	
	@OneToMany(mappedBy = "filiere" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<InscriptionPedagogique> inscriptionPedagogiques;

	public Long getId_filiere() {
		return id_filiere;
	}

	public void setId_filiere(Long id_filiere) {
		this.id_filiere = id_filiere;
	}

	public String getNom_filiere() {
		return nom_filiere;
	}

	public void setNom_filiere(String nom_filiere) {
		this.nom_filiere = nom_filiere;
	}

	public ResponsableFiliere getResponsableFiliere() {
		return responsableFiliere;
	}

	public void setResponsableFiliere(ResponsableFiliere responsableFiliere) {
		this.responsableFiliere = responsableFiliere;
	}

	public List<InscriptionAdministrative> getInscriptionAdministratives() {
		return inscriptionAdministratives;
	}

	public void setInscriptionAdministratives(List<InscriptionAdministrative> inscriptionAdministratives) {
		this.inscriptionAdministratives = inscriptionAdministratives;
	}

	public List<InscriptionPedagogique> getInscriptionPedagogiques() {
		return inscriptionPedagogiques;
	}

	public void setInscriptionPedagogiques(List<InscriptionPedagogique> inscriptionPedagogiques) {
		this.inscriptionPedagogiques = inscriptionPedagogiques;
	}
	
	
	
	
}
