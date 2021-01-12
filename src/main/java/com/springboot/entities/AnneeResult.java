package com.springboot.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AnneeResult {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etape")
    private Etape etape;

    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etudiant")
    private Etudiant etudiant;
    
    private double note;
    public String afficher(){
        return this.etape.getName()+" : "+this.note;
    }
}
