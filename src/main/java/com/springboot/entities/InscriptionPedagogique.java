package com.springboot.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class InscriptionPedagogique {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDIP")
	Long id_inscription_pedagogique;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cne")
	Etudiant etudiant;
	
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_filiere")
	Filiere filiere;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_semestre")
	Semestre semestre;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date date_pres_inscription;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date date_inscription_valide;
	boolean inscription_valide;
	

}
