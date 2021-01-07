package com.springboot.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Element {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	private String name;
	private double coefficient;
	private double noteEliminatoire;
	private double noteValidation;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_module")
	private Module module;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_professeur")
	private Professeur professeur;
	
	
	@OneToMany(mappedBy = "element" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<InscriptionPedagogique> inscriptionPedagogiques;
	
	@OneToMany(mappedBy = "element" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<Note> notes;
}
