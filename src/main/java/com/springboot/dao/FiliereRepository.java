package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Filiere;
@Repository
public interface FiliereRepository extends JpaRepository<Filiere,Long> {

}
