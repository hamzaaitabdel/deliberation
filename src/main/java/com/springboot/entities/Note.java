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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Note {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_note;
	private double note;
	private int year;
	private String etats;
	private String session;
		
	private String cne;
	private Long id_module;
	
}
