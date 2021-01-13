package com.springboot.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.springboot.entities.Etablissement;
import com.springboot.entities.Filiere;
@Repository
public interface FiliereRepository extends JpaRepository<Filiere,Long> {

//	public Long findIdByName(String filiere);
	
	@Query("select f from Filiere f")
	public List<Filiere> getAll();
	
	
	@Query("select f from Filiere f where f.id =: id")
	public Filiere findById_filiere(@Param("id")Long id);
	
	@Query("select f.id from Filiere f where f.name =: x")
	public Long getId_filiere(@Param("x")String nom);
	
//	@Query("select f from Filiere f where f.id_filiere =: id_filiere")
//	public Filiere findById_filiere(@Param("id_filiere")Long id_filiere);
//	
//	@Query("select f.id_filiere from Filiere f where f.nom_filiere =: x")
//	public Long getId_filiere(@Param("x")String nom);
//	
	@Query(value="select f from Filiere f where f.etablissement=:etablissement")
	public List<Filiere> findByEtablissement(@Param("etablissement")Etablissement etablissement);
//	
	
	@Transactional
	@Modifying
	@Query("delete from Filiere s where s.id = :id")
	public void DeleteIDF(@Param("id")Long id);
	
	
}
