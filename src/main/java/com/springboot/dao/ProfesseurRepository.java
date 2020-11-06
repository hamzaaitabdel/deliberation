package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.entities.Professeur;
@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur,Long> {

}
