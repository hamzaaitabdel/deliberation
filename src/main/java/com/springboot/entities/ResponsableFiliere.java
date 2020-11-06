package com.springboot.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString

public class ResponsableFiliere {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_responsableFiliere;
	private String nom_resp_fl;
	@OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_Filiere")
	private Filiere filiere;
	private String email;
	private String password;
	
}
