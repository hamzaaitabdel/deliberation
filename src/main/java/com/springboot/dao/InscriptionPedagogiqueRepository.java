package com.springboot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.*;
@Repository
public interface InscriptionPedagogiqueRepository extends JpaRepository<Etudiant,String> {
    // @Query(value="SELECT u from InscriptionPedagogique u where u.semestre =:s")
    // public Page<InscriptionPedagogique> findByid_moduleAndid_semestre(@Param("s")Semestre s,Pageable pageable);

    @Query(value="select i from InscriptionPedagogique i where i.etudiant =:cne order by i.anneeUniversitaire DESC")
    public InscriptionPedagogique getByCne(@Param("cne")Etudiant e);
}
