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
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Professeur {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private String prenom;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etablissement")
	private Etablissement etablissement;
	
	@OneToOne(mappedBy = "professeur" )
	private User user;
	
	@OneToMany(mappedBy = "professeur" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<Element> elements;
	
}
