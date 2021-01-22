package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Etablissement;
import com.springboot.entities.Professeur;

@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur, Long>{
	
	public Long findIdByNom(String professeur);
	@Query("select p from Professeur p where p.nom = :x and p.prenom = :name")
	public Professeur findByNom(@Param("x")String nom, 
			  @Param("name") String name);
}
