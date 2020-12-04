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
	private String cne;
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

	public String getCne() {
		return cne;
	}

	public void setCne(String cne) {
		this.cne = cne;
	}

	public String getNom_fr() {
		return nom_fr;
	}

	public void setNom_fr(String nom_fr) {
		this.nom_fr = nom_fr;
	}

	public String getPrenom_fr() {
		return prenom_fr;
	}

	public void setPrenom_fr(String prenom_fr) {
		this.prenom_fr = prenom_fr;
	}

	public String getNom_ar() {
		return nom_ar;
	}

	public void setNom_ar(String nom_ar) {
		this.nom_ar = nom_ar;
	}

	public String getPrenom_ar() {
		return prenom_ar;
	}

	public void setPrenom_ar(String prenom_ar) {
		this.prenom_ar = prenom_ar;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public Date getDate_naissance() {
		return date_naissance;
	}

	public void setDate_naissance(Date date_naissance) {
		this.date_naissance = date_naissance;
	}

	public String getLieu_naissance_fr() {
		return lieu_naissance_fr;
	}

	public void setLieu_naissance_fr(String lieu_naissance_fr) {
		this.lieu_naissance_fr = lieu_naissance_fr;
	}

	public String getLieu_naissance_ar() {
		return lieu_naissance_ar;
	}

	public void setLieu_naissance_ar(String lieu_naissance_ar) {
		this.lieu_naissance_ar = lieu_naissance_ar;
	}

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public Date getDate_inscription() {
		return date_inscription;
	}

	public void setDate_inscription(Date date_inscription) {
		this.date_inscription = date_inscription;
	}

	public String getAnnee_bac() {
		return annee_bac;
	}

	public void setAnnee_bac(String annee_bac) {
		this.annee_bac = annee_bac;
	}

	public String getSerie_bac() {
		return serie_bac;
	}

	public void setSerie_bac(String serie_bac) {
		this.serie_bac = serie_bac;
	}

	public String getMention_bac() {
		return mention_bac;
	}

	public void setMention_bac(String mention_bac) {
		this.mention_bac = mention_bac;
	}

	public String getLycee_bac() {
		return lycee_bac;
	}

	public void setLycee_bac(String lycee_bac) {
		this.lycee_bac = lycee_bac;
	}

	public String getVille_bac() {
		return ville_bac;
	}

	public void setVille_bac(String ville_bac) {
		this.ville_bac = ville_bac;
	}

	public String getAcademie() {
		return academie;
	}

	public void setAcademie(String academie) {
		this.academie = academie;
	}

	public String getNationalite() {
		return nationalite;
	}

	public void setNationalite(String nationalite) {
		this.nationalite = nationalite;
	}

	public String getVille_naissance() {
		return ville_naissance;
	}

	public void setVille_naissance(String ville_naissance) {
		this.ville_naissance = ville_naissance;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getEtat_physique() {
		return etat_physique;
	}

	public void setEtat_physique(String etat_physique) {
		this.etat_physique = etat_physique;
	}

	public String getGroupe_socioprofessionnel() {
		return groupe_socioprofessionnel;
	}

	public void setGroupe_socioprofessionnel(String groupe_socioprofessionnel) {
		this.groupe_socioprofessionnel = groupe_socioprofessionnel;
	}

	public boolean isValide_enligne() {
		return valide_enligne;
	}

	public void setValide_enligne(boolean valide_enligne) {
		this.valide_enligne = valide_enligne;
	}

	public InscriptionAdministrative getInscriptionAdministrative() {
		return inscriptionAdministrative;
	}

	public void setInscriptionAdministrative(InscriptionAdministrative inscriptionAdministrative) {
		this.inscriptionAdministrative = inscriptionAdministrative;
	}
	
}
