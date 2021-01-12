package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entities.AnneeResult;
import com.springboot.entities.AnneeUniversitaire;
import com.springboot.entities.Element;

@Repository
public interface AnneResultRepository extends JpaRepository<AnneeResult, Long> {
	
//	Long findByAnnee(String annee);
	
}
