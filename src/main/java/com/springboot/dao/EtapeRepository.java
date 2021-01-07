package com.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Etape;

@Repository
public interface EtapeRepository extends JpaRepository<Etape,Long> {

	@Query("select e from Etape e where e.filiere.id= :x ")
	public List<Etape> findById_filiereContains(@Param("x")Long id_filiere);
	
	public Long findIdByName(String etape);
}
