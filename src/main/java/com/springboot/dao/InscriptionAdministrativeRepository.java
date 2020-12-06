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

	/*@Query(value="SELECT * FROM InscriptionAdministative",nativeQuery=true)
	public Page<InscriptionAdministrative> selectAll(Pageable pa);
	*/
	/*
	@Query("select e from InscriptionAdministrative e where e.cne like :x ")
    public Page<InscriptionAdministrative> findByCneContains(@Param("x")String keyword, Pageable of);
*/	
	@Query("select e from InscriptionAdministrative e where e.annee_academique like :x ")
	public Page<InscriptionAdministrative> findByAnnee_academiqueContains(@Param("x")String keyword, Pageable of);
	/*
	 * @Modifying
	 * 
	 * @Transactional
	 * 
	 * @Query("UPDATE InscriptionAdministrative c SET c.annee_academique = :annee_academique  c.date_inscription_valide = :date_inscription_valide "
	 * 
	 * + "c.adresse_personnel = :adresse_personnel" + "c.ville = :ville" +
	 * "c.telephone = :telephone" + "c.email_etud = :email_etud" +
	 * "c.email_parent = :email_parent" + "c.cne = :cne" +
	 * "c.id_filiere = :id_filiere" +
	 * "WHERE c.id_inscription_administrative = :id_inscription_administrative"
	 * 
	 * ) public InscriptionAdministrative update(
	 * 
	 * @Param("id_inscription_administrative") Long id,
	 * 
	 * @Param("date_inscription_valide") String date_inscription_valide,
	 * 
	 * @Param("annee_academique") String annee_academique,
	 * 
	 * @Param("adresse_personnel") String adresse_personnel,
	 * 
	 * @Param("ville") String ville,
	 * 
	 * @Param("telephone") String telephone,
	 * 
	 * @Param("email_etud") String email_etud,
	 * 
	 * @Param("email_parent") String email_parent,
	 * 
	 * @Param("cne") InscriptionEnligne inscriptionEnligne,
	 * 
	 * @Param("id_filiere") Filiere filiere
	 * 
	 * );
	 * 
	 * 
	 */	
	
	/*@Query("select e from InscriptionAdministrative e where e.id=? ")
	public InscriptionAdministrative findById(String id);
	*/
}
