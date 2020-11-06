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
public class Module {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_module;
	private String libelle_module;
	private boolean hasElement;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_Semestre")
	private Semestre semestre;
	@OneToMany(mappedBy = "module" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<Element> elements;
	@OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL , mappedBy = "module")
	private Professeur professeur;
	@OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL , mappedBy = "module")
	private ResponsableModul responsableModul;
	private boolean valide;
}
