package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.entities.InscriptionEnligne;
@Repository
public interface InscriptionEnligneRepository extends JpaRepository<InscriptionEnligne,Long> {
	//public InscriptionEnligne findByMassar_etud(String massar);
	
}
