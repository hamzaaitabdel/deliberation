package com.springboot.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

import com.springboot.entities.*;
@Repository
public interface NoteRepository extends JpaRepository<Note,Long>{
    // @Query
    // public Page<Note> findAll(Pageable pageable);

    @Query
    public ArrayList<Note> findByEtudiant(String cne);

    @Query("select n from Note n where n.element= :element")
    public List<Note> getNoteByElement(@Param("element")Element element);
    
    @Query(value = "select n from Note n where n.etudiant =:etudiant and n.element =:element")
    public List<Note> findByCneAndElement(@Param("etudiant")Etudiant etudiant,@Param("element")Element element);

    @Query(value = "select n from Note n where n.etudiant =:etudiant")
    public List<Note> findByCne(@Param("etudiant")Etudiant etudiant);
    
    // @Query(value = "select n from Note n where n.cne =:cne and year=:year")
    // public Page<Note> findByCneAndYear(@Param("cne")String cne,@Param("year")int year,Pageable pageable);

    // @Query(value = "select n from Note n where n.cne =:cne and n.id_module =:id_module")
    // public Note findByCneAndId_module(@Param("cne")String cne,@Param("id_module")Long id_module);

    // @Query(value = "select n from Note n where n.id_module =:id")
    // public Page<Note> findByIdmodule(@Param("id")Long id_module,Pageable pageable);

    // @Query(value = "select n from Note n where n.id_note =:id")
    // public Page<Note> findById(@Param("id")Long id_note,Pageable pageable);

}
