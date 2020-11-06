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
public class Element {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_element;
	private String libelle_element;
	private int coefficient;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_Module")
	private Module module;
	
}
