package com.springboot.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.ArrayList;
import com.springboot.entities.Module;
import com.springboot.entities.*;
@Repository
public interface ModuleRepository extends JpaRepository<Module,Long> {
    
    // @Query(value = "select m from Module m where m.libelle_module = :libelle")
    // public Module getByLibellemodule(@Param("libelle")String libelle_module);
    // @Query(value = "select m from Module m where m.id_module = :id")
    // public Module findByid(@Param("id")Long id);
    
    // @Query(value="select m from Module m where m.semestre = :sem")
    // public Page<Module>getModuleBySemestre(@Param("sem")Semestre s,Pageable p);
	
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
