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
import javax.validation.constraints.Email;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Etudiant {

	@Id
	private String cne;
	
	private String prenom;
	private String nom;
	private String telephone;
	@Email
	private String email;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_filiere")
	private Filiere filiere;
	
	@OneToMany(mappedBy = "etudiant" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<InscriptionPedagogique> inscriptionPedagogiques;
	
	@OneToMany(mappedBy = "etudiant" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<Note> notes;
}
