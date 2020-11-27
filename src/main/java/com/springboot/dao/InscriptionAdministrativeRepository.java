package com.springboot.dao;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import com.springboot.entities.InscriptionAdministrative;
import com.springboot.entities.InscriptionEnligne;
@Repository
public interface InscriptionAdministrativeRepository extends JpaRepository<InscriptionAdministrative,Long> {
	
	@Query("select e from InscriptionAdministrative e where e.annee_academique like :x ")
	public Page<InscriptionAdministrative> findByAnnee_academiqueContains(@Param("x")String keyword, Pageable of);
	/*
	 * @Modifying
	 * 
	 * @Query("UPDATE InscriptionAdministrative c SET c.annee_academique = :annee_academique  c.date_inscription_valide = :date_inscription_valide "
	 * 
	 * + "c.adresse_personnel = :adresse_personnel" + "c.ville = :ville" +
	 * "c.telephone = :telephone" + "c.email_etud = :email_etud" +
	 * "c.email_parent = :email_parent"
	 * 
	 * + "WHERE c.id_inscription_administrative = :id_inscription_administrative")
	 * public InscriptionAdministrative update(
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
	 * @Param("email_parent") String email_parent);
	 */
}
