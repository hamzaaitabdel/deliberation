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
@Data @NoArgsConstructor @AllArgsConstructor
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
	
	//yyoofu
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

	public Long getId_inscription_administrative() {
		return id_inscription_administrative;
	}

	public void setId_inscription_administrative(Long id_inscription_administrative) {
		this.id_inscription_administrative = id_inscription_administrative;
	}

	public InscriptionEnligne getInscriptionEnligne() {
		return inscriptionEnligne;
	}

	public void setInscriptionEnligne(InscriptionEnligne inscriptionEnligne) {
		this.inscriptionEnligne = inscriptionEnligne;
	}

	public String getAnnee_academique() {
		return annee_academique;
	}

	public void setAnnee_academique(String annee_academique) {
		this.annee_academique = annee_academique;
	}

	public Date getDate_inscription_valide() {
		return date_inscription_valide;
	}

	public void setDate_inscription_valide(Date date_inscription_valide) {
		this.date_inscription_valide = date_inscription_valide;
	}

	public String getAdresse_personnel() {
		return adresse_personnel;
	}

	public void setAdresse_personnel(String adresse_personnel) {
		this.adresse_personnel = adresse_personnel;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail_etud() {
		return email_etud;
	}

	public void setEmail_etud(String email_etud) {
		this.email_etud = email_etud;
	}

	public String getEmail_parent() {
		return email_parent;
	}

	public void setEmail_parent(String email_parent) {
		this.email_parent = email_parent;
	}

	public Filiere getFiliere() {
		return filiere;
	}

	public void setFiliere(Filiere filiere) {
		this.filiere = filiere;
	}
	
	
	
}
