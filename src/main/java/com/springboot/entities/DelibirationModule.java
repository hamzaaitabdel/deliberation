package com.springboot.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class DelibirationModule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_module")
    private Module module;

    private double note;

    @ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_etudiant")
    private Etudiant etudiant;

    @Override
    public String toString() {
        return "DelibirationModule [etudiant=" + etudiant + ", id=" + id + ", module=" + module + ", note=" + note
                + "]";
    }

    public DelibirationModule(Module module, double note, Etudiant etudiant) {
        this.module = module;
        this.note = note;
        this.etudiant = etudiant;
    }
    
}
