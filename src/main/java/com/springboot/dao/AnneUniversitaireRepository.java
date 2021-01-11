package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.AnneeUniversitaire;
import com.springboot.entities.Semestre;
@Repository
public interface AnneUniversitaireRepository extends JpaRepository<AnneeUniversitaire,Long> {
//	
	@Query("select A from AnneeUniversitaire A where A.annee =: An")
	public AnneeUniversitaire findAUByAnne(@Param("An")String An);
}