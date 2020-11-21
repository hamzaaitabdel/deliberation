package com.springboot.entities;
import java.time.Year;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class InscriptionEnligne {
	
	@Id
	@Column(name = "CNE" , unique = true)
	String cne;
	private String nom_fr;
	private String prenom_fr;
	private String nom_ar;
	private String prenom_ar;
	private String sexe;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_naissance;
	private String lieu_naissance_fr; 
	private String lieu_naissance_ar;
	@Column(name = "CIN")
	private String cin;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_inscription;
	//@Lob
	//private byte[] photo;
	private String annee_bac;
	private String serie_bac;
	private String mention_bac;
	private String lycee_bac;
	private String ville_bac;
	private String academie;
	private String nationalite;
	private String ville_naissance;
	private String province;
	private String region;
	private String etat_physique;
	@Column(name = "GSP")
	private String groupe_socioprofessionnel;
	
	private boolean valide_enligne;
	
	@OneToOne(mappedBy = "inscriptionEnligne")
	InscriptionAdministrative inscriptionAdministrative;
	
}
