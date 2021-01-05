package com.springboot.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class AnneeUniversitaire {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String annee;
	
	@OneToMany(mappedBy = "anneeUniversitaire" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<InscriptionAdministrative> inscriptionAdministratives;
	
	@OneToMany(mappedBy = "anneeUniversitaire" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<Note> notes;
	
	
}
