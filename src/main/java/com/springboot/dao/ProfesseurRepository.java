package com.springboot.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Etudiant;
import com.springboot.entities.Filiere;
import com.springboot.entities.Professeur;
@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur,Long> {


}