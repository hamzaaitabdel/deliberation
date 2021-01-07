package com.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Filiere;
@Repository
public interface FiliereRepository extends JpaRepository<Filiere,Long> {

//	public Long findIdByName(String filiere);
	
	@Query("select f from Filiere f")
	public List<Filiere> getAll();
	
	
	@Query("select f from Filiere f where f.id =: id_filiere")
	public Filiere findById_filiere(@Param("id_filiere")Long id_filiere);
	
	@Query("select f.id from Filiere f where f.name =: x")
	public Long getId_filiere(@Param("x")String nom);
	
//	@Query("select f from Filiere f where f.id_filiere =: id_filiere")
//	public Filiere findById_filiere(@Param("id_filiere")Long id_filiere);
//	
//	@Query("select f.id_filiere from Filiere f where f.nom_filiere =: x")
//	public Long getId_filiere(@Param("x")String nom);
//	
//	@Query(value="select id_filiere from Filiere where nom_filiere = ?",nativeQuery=true)
//	public Long findId_filiereByNome_filiere(String nom);
//	
}
