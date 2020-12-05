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
	private String cne;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date annee_academique;
	
	
	
	
	@OneToMany(mappedBy = "etudiant" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<InscriptionPedagogique> inscriptionPedagogiques;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_resultat")
	private Resultat resultat;
	
	@OneToMany(mappedBy = "etudiant" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<Note> notes;
	
	private String prenom_etud;
	private String nom_etud;
	private String tel_etud;
	private String email_etud;

	public String getCne() {
		return cne;
	}

	public void setCne(String cne) {
		this.cne = cne;
	}

	public Date getAnnee_academique() {
		return annee_academique;
	}

	public void setAnnee_academique(Date annee_academique) {
		this.annee_academique = annee_academique;
	}

	public List<InscriptionPedagogique> getInscriptionPedagogiques() {
		return inscriptionPedagogiques;
	}

	public void setInscriptionPedagogiques(List<InscriptionPedagogique> inscriptionPedagogiques) {
		this.inscriptionPedagogiques = inscriptionPedagogiques;
	}

	public Resultat getResultat() {
		return resultat;
	}

	public void setResultat(Resultat resultat) {
		this.resultat = resultat;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
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

	
	
}
