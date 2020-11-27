package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Filiere;
@Repository
public interface FiliereRepository extends JpaRepository<Filiere,Long> {
	
	@Query("select f from Filiere f where f.id_filiere =: id_filiere")
	public Filiere findById_filiere(@Param("filiere")Long id_filiere);

}
