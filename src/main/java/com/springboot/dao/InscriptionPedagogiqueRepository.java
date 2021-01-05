package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Etudiant;
import com.springboot.entities.InscriptionPedagogique;
@Repository
public interface InscriptionPedagogiqueRepository extends JpaRepository<InscriptionPedagogique,Long> {
	
}
