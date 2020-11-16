package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.springboot.entities.InscriptionEnligne;
@Repository
public interface InscriptionAdministrativeRepository extends JpaRepository<InscriptionEnligne,Long> {
	
	
}
