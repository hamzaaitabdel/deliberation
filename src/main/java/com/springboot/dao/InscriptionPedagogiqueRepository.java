package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.entities.Etudiant;
import com.springboot.entities.InscriptionPedagogique;
import com.springboot.entities.Module;
@Repository
public interface InscriptionPedagogiqueRepository extends JpaRepository<InscriptionPedagogique,Long> {
	
	@Transactional
	@Modifying
	@Query("update InscriptionPedagogique s set s.module= :x where s.id_inscription_pedagogique = :id")
	public void UpdateId_module(@Param("x")Module m, @Param("id")Long id);
	
	@Transactional
	@Modifying
	@Query("delete from InscriptionPedagogique s where s.id_inscription_pedagogique = :id")
	public void DeleteIDIP(@Param("id")Long id);
	
	
	
	
}
