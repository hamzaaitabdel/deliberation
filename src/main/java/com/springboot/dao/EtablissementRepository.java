package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Etablissement;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long>{
	
//	public Long findIdByName(String etabli);
	
}
