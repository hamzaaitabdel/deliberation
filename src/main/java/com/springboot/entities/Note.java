package com.springboot.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor


@Entity
public class Note {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private double note;
	
	private String etats;
	private String session;
			
	@Enumerated(EnumType.STRING)
	private Examen examen;
	
	public static enum Examen {
		TP,CC,EXAM
	}
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etudiant")
	Etudiant etudiant;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_element")
	Element element;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "annee_universitaire")
	AnneeUniversitaire anneeUniversitaire;
	
}
