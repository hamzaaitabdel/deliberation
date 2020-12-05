package com.springboot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Etudiant;
@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant,String> {

	
	@Query("select e from Etudiant e where e.nom_etud like :x ")
    public Page<Etudiant> findByNom_etudContains(@Param("x")String keyword, Pageable of);
	
}
