package com.springboot.dao;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Date;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Etudiant;
import com.springboot.entities.Filiere;
import com.springboot.entities.InscriptionAdministrative;
import com.springboot.entities.InscriptionEnligne;

@Repository
public interface InscriptionAdministrativeRepository extends JpaRepository<InscriptionAdministrative,Long> {
//	@Query(value="SELECT * FROM InscriptionAdministative",nativeQuery=true)
//	public Page<InscriptionAdministrative> selectAll(Pageable pa);
//	
	
//	@Query("select e from InscriptionAdministrative e where e.cne like :x ")
//    public Page<InscriptionAdministrative> findByCneContains(@Param("x")String keyword, Pageable of);
//	
	// @Query("select e from InscriptionAdministrative e where e.nom like :x ")
	// public Page<InscriptionAdministrative> findByNomContains(@Param("x")String keyword, PageRequest of);
	
	

}
