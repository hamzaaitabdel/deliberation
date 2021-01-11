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
import lombok.ToString;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class Semestre {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private boolean etats;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etape")
	private Etape etape;
	
	@OneToMany(mappedBy = "semestre" , fetch = FetchType.LAZY , cascade = CascadeType.ALL)
	private List<Module> modules;
	
}
