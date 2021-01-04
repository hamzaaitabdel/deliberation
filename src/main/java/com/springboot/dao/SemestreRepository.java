package com.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Semestre;
@Repository
public interface SemestreRepository extends JpaRepository<Semestre,Long> {
	
	@Query("select d from Semestre d where d.etape.filiere.id_filiere like :x ")
	public List<Semestre> findById_filiereContains(@Param("x")Long id_filiere);
}
