package com.springboot.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString

public class Etape {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_etape;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date annee_universitaire;
	private String libelle_etape;
	private boolean etats;
	
	
	@OneToMany(mappedBy = "etape" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<Semestre> semestres;
	
	@OneToMany(mappedBy = "etape" , fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	List<InscriptionPedagogique> inscriptionPedagogiques;
	
	
	private boolean hasDiplome;
}
