package com.springboot.entities;

import java.util.Date;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class InscriptionPedagogique {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDIP")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateInscriptionValide;	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etudiant")
	private Etudiant etudiant;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_element")
	private Element element;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "annee_universitaire")
	private AnneeUniversitaire anneeUniversitaire;
	

	

}