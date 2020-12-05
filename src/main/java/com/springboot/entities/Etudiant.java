package com.springboot.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import lombok.ToString;

@Entity

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Etudiant {

	@Id
	@Column(name = "CNE")
	private String massarEtud;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date annee_academique;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etape")
	private Etape etape;



	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_filiere")
	private Filiere filiere;



	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_resultat")
	private Resultat resultat;



	private String prenom_etud;
	private String nom_etud;
	private String tel_etud;
	private String email_etud;


	//new 

	private String prenomAr_etud;
	private String nomAr_etud;
	private String sexe;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_de_naissance;

	private String lieu_de_naissance;
	private String lieu_de_naissanceAr;
	private String CIN;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date_premiere_inscription;

	//@
	private Byte[] photo;

	
	private String annee_de_bac;


	private String mention_de_bac;
	private String lycee_bac;
	private String serie_bac;
	private String ville_bac;
	private String academie;
	private String nationalite;
	private String province;
	private String region;
	private String etat_physique;
	
	@Column(name = "GSP")
	private String groupe_socioprofessionnel;

	private String adresse;
	

	

	public String getPrenomAr_etud() {
		return prenomAr_etud;
	}

	public void setPrenomAr_etud(String prenomAr_etud) {
		this.prenomAr_etud = prenomAr_etud;
	}

	public String getNomAr_etud() {
		return nomAr_etud;
	}

	public void setNomAr_etud(String nomAr_etud) {
		this.nomAr_etud = nomAr_etud;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public Date getDate_de_naissance() {
		return date_de_naissance;
	}

	public void setDate_de_naissance(Date date_de_naissance) {
		this.date_de_naissance = date_de_naissance;
	}

	public String getLieu_de_naissance() {
		return lieu_de_naissance;
	}

	public void setLieu_de_naissance(String lieu_de_naissance) {
		this.lieu_de_naissance = lieu_de_naissance;
	}

	public String getLieu_de_naissanceAr() {
		return lieu_de_naissanceAr;
	}

	public void setLieu_de_naissanceAr(String lieu_de_naissanceAr) {
		this.lieu_de_naissanceAr = lieu_de_naissanceAr;
	}

	public String getCIN() {
		return CIN;
	}

	public void setCIN(String cIN) {
		CIN = cIN;
	}

	public Date getDate_premiere_inscription() {
		return date_premiere_inscription;
	}

	public void setDate_premiere_inscription(Date date_premiere_inscription) {
		this.date_premiere_inscription = date_premiere_inscription;
	}

	public Byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(Byte[] photo) {
		this.photo = photo;
	}

	

	public String getMention_de_bac() {
		return mention_de_bac;
	}

	public void setMention_de_bac(String mention_de_bac) {
		this.mention_de_bac = mention_de_bac;
	}

	public String getLycee_bac() {
		return lycee_bac;
	}

	public void setLycee_bac(String lycee_bac) {
		this.lycee_bac = lycee_bac;
	}

	public String getSerie_bac() {
		return serie_bac;
	}

	public void setSerie_bac(String serie_bac) {
		this.serie_bac = serie_bac;
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

	public String getMassarEtud() {
		return massarEtud;
	}

	public void setMassarEtud(String massarEtud) {
		this.massarEtud = massarEtud;
	}

	public Date getAnnee_academique() {
		return annee_academique;
	}

	public void setAnnee_academique(Date annee_academique) {
		this.annee_academique = annee_academique;
	}

	public Etape getEtape() {
		return etape;
	}

	public void setEtape(Etape etape) {
		this.etape = etape;
	}

	public Filiere getFiliere() {
		return filiere;
	}

	public void setFiliere(Filiere filiere) {
		this.filiere = filiere;
	}

	public Resultat getResultat() {
		return resultat;
	}

	public void setResultat(Resultat resultat) {
		this.resultat = resultat;
	}

	public String getPrenom_etud() {
		return prenom_etud;
	}

	public void setPrenom_etud(String prenom_etud) {
		this.prenom_etud = prenom_etud;
	}

	public String getNom_etud() {
		return nom_etud;
	}

	public void setNom_etud(String nom_etud) {
		this.nom_etud = nom_etud;
	}

	public String getTel_etud() {
		return tel_etud;
	}

	public void setTel_etud(String tel_etud) {
		this.tel_etud = tel_etud;
	}

	public String getEmail_etud() {
		return email_etud;
	}

	public void setEmail_etud(String email_etud) {
		this.email_etud = email_etud;
	}

	public String getAnnee_de_bac() {
		return annee_de_bac;
	}

	public void setAnnee_de_bac(String annee_de_bac) {
		this.annee_de_bac = annee_de_bac;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	
}
