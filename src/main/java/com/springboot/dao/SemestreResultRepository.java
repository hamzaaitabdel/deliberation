package com.springboot.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.springboot.entities.DelibirationModule;
import com.springboot.entities.Etudiant;
import com.springboot.entities.Module;
import com.springboot.entities.Semestre;
import com.springboot.entities.SemestreResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface SemestreResultRepository extends JpaRepository<SemestreResult,Long>{
    @Query("select r from SemestreResult r where r.semestre=:semestre")
    public List<SemestreResult> findBySemestre(@Param("semestre")Semestre semestre);

    @Query("select r from SemestreResult r where r.etudiant=:etudiant")
    public List<SemestreResult> findByEtudiant(@Param("etudiant")Etudiant etudiant);
}
