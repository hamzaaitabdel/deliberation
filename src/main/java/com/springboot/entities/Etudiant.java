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
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etape")
	private Etape etape;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_filiere")
	private Filiere filiere;
	
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

	
	
}
