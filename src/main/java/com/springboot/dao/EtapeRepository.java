package com.springboot.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Etape;

@Repository
public interface EtapeRepository extends JpaRepository<Etape,Long> {
	
//	@Query(value="select id_etape from Etape where libelle_etape = ?",nativeQuery=true)
//	public Long findId_etapeByLibelle_etape(String libelle);
//	
}
