package com.springboot.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springboot.entities.Module;
@Repository
public interface ModuleRepository extends JpaRepository<Module,Long> {
	
	@Query(value="select * from Module  where id= :x ",nativeQuery=true)
	public List<Module> findById_semestreContains(@Param("x")Long Id_semestre);

	@Query(value="select * from Module  where libelle_module= :x ",nativeQuery=true)
	public Module findByLibelle_module(@Param("x")String libelle_module);

	@Query("select m from Module m where m.semestre.etape.filiere.id= :x")
	public List<Module> findById_filiereContains(@Param("x")Long id_filiere);
	
	
	@Transactional
	@Modifying
	@Query("delete from Module s where s.id = :id")
	public void DeleteIDM(@Param("id")Long id);
	
	
}
