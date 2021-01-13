package com.springboot.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Etape;
import com.springboot.entities.InscriptionAdministrative;

@Repository
public interface EtapeRepository extends JpaRepository<Etape,Long> {

	@Query("select e from Etape e where e.filiere.id= :x ")
	public List<Etape> findById_filiereContains(@Param("x")Long id_filiere);
	
	public Long findIdByName(String etape);
	
	@Transactional
	@Modifying
	@Query("delete from Etape s where s.id = :id")
	public void DeleteIDEt(@Param("id")Long id);
	
//	@Transactional
//	@Modifying
//	@Query("update Etape s set s= :e where s.id= :id")
//	public void UpdateAdmin(@Param("e")Etape e, @Param("id")Long id);
//	
	
}
