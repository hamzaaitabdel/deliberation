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
	
	@Id @GeneratedValue
	@Column(name = "IDIE")
	private Long id;
	
	private String cne;
	
	private String nomFr;
	private String prenomFr;
	private String nomAr;
	private String prenomAr;
	private String sexe;
	
	private String lieuNaissanceFr; 
	private String lieuNaissanceAr;
	private String cin;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateNaissance;

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateInscription;
	
	private String anneeBac;
	private String serieBac;
	private String mentionBac;
	private String lyceeBac;
	private String villeBac;
	private String academie;
	private String nationalite;
	private String province;
	private String region;
	private String etatPhysique;
	
	@Column(name = "GSP")
	private String groupeSocioProfessionnel;
	
	@Lob
	private Byte[] photo; 
	
	private boolean valideEnligne;
	
	@OneToOne(mappedBy = "inscriptionEnligne")
	InscriptionAdministrative inscriptionAdministrative;
	
}
