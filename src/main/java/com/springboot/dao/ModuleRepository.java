package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
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
}
