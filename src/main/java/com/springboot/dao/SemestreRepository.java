package com.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
///
import com.springboot.entities.Semestre;
@Repository
public interface SemestreRepository extends JpaRepository<Semestre,Long> {
	
	@Query("select d from Semestre d where d.etape.filiere.id like :x ")
	public List<Semestre> findById_filiereContains(@Param("x")Long id_filiere);
	@Query(value="select id_semestre from Semestre where libelle_semestre = ?",nativeQuery=true)
	public Long findId_moduleByLibelle_semestre(String libelle);
	
	@Query("select d from Semestre d where d.etape.id like :x ")
	public List<Semestre> findById_etapeContains(@Param("x")Long idetape);
	
//	
//	@Query(value="select id_semestre from Semestre where libelle_semestre = ?",nativeQuery=true)
//	public Long findId_moduleByLibelle_semestre(String libelle);
//	

//	public Long findIdByName(String semestre);
}
