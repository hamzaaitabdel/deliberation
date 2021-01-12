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

	@Query("select e from InscriptionAdministrative e where e.nom like :x")
	public Page<InscriptionAdministrative> findByNomContains(@Param("x")String keyword, Pageable of);

	//	@Query(value="select e.* from inscription_administrative e , annee_niversitaire a where  a.id=e.annee_universitaire and a.annee like ?",nativeQuery=true)
//	public Page<InscriptionAdministrative> findByAnneeUniversitaire(String keyword, Pageable of);
	
	@Transactional
	@Modifying
	@Query("update InscriptionAdministrative s set s= :e where s.id= :id")
	public void UpdateAdmin(@Param("e")InscriptionAdministrative e, @Param("id")Long id);
	
	@Transactional
	@Modifying
	@Query("delete from InscriptionAdministrative s where s.id = :id")
	public void DeleteIDIA(@Param("id")Long id);
	
	
}
