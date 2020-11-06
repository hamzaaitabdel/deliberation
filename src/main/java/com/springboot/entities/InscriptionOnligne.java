package com.springboot.entities;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class InscriptionOnligne {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_inscription;
	private String massar_etud;
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
	private String cin;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_inscription;
	//Photo
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
	private String groupe_socioprofessionnel;
	
}
