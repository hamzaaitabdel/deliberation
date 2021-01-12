package com.springboot.entities;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DelibirationElement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_element")
    private Element element;

    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etudiant")
    private Etudiant etudiant;
    
    private double note;
    public String afficher(){
        return this.element.getName()+":"+this.note;
    }
    
}
