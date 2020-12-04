package com.springboot.entities;

import javax.persistence.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
public class InscriptionAdministrative {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDIA" )
	private Long id_inscription_administrative;
	
	@OneToOne(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name = "cne")//foreign key
	private InscriptionEnligne inscriptionEnligne;
	
	private String annee_academique;
	
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_inscription_valide;
	//diplome ?
	//bourse ?
	private String adresse_personnel;
	private String ville;
	private String telephone;
	private String email_etud;
	private String email_parent;
	
	
	//?
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_filiere")
	private Filiere filiere;
	
}
