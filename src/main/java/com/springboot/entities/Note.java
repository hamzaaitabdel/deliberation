package com.springboot.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class Note {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_note;
	private double note;
	
	@OneToMany(mappedBy = "note" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<Module> modules;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cne")
	private Etudiant etudiant;
	
	public Long getId_note() {
		return id_note;
	}

	public void setId_note(Long id_note) {
		this.id_note = id_note;
	}

	public double getNote() {
		return note;
	}

	public void setNote(double note) {
		this.note = note;
	}

	public List<Module> getModules() {
		return modules;
	}

	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}
	
	
}
