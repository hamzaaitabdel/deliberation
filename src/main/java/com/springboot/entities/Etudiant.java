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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	private String annee_academique;

	private String prenom_etud;
	private String nom_etud;
	private String tel_etud;
	private String email_etud;
	
	
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinTable(
        name = "NOTES",
        joinColumns = { @JoinColumn(name = "cne") },
        inverseJoinColumns = { @JoinColumn(name = "id_module") }
    )
    private List<Module> modules;
	
	
	
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_filiere")
	private Filiere filiere;
	

	//Long id_filiere;
	
	
	
	
//	@OneToMany(mappedBy = "etudiant" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	List<InscriptionPedagogique> inscriptionPedagogiques;
//	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_resultat")
	private Resultat resultat;


	//li kan xi attribut khaso ytzad indiquiweh?
	
//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	private Date date_de_naissance;
//	private String lieu_de_naissance;	
//	private String CIN;
//	


	
}