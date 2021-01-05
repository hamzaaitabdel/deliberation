package com.springboot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.springboot.entities.Semestre;

@Repository
public interface SemestreRepository extends JpaRepository<Semestre, Long> {
    // @Query
    // public ArrayList<Semestre> findByid_semestre(Long id_semestre);
    // No property id found for type Semestre!
    @Query("select s from Semestre s where s.id_semestre = :id_semestre")
    public Semestre findById_semestre(@Param("id_semestre")Long id_semestre);
    
    @Query("select s from Semestre s where s.libelle_semestre = :libelle_semestre")
	public Semestre findBylibelle(@Param("libelle_semestre")String libelle_semestre);
}
