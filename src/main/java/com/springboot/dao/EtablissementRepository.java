package com.springboot.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.springboot.entities.DelibirationModule;
import com.springboot.entities.Etablissement;
import com.springboot.entities.Module;
import com.springboot.entities.SemestreResult;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {
    
}
