package com.springboot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import com.springboot.entities.InscriptionAdministrative;
import com.springboot.entities.InscriptionEnligne;
@Repository
public interface InscriptionEnligneRepository extends JpaRepository<InscriptionEnligne,Long> {
	@Query("select e from InscriptionEnligne e where e.nomFr like :x order by e.dateInscription")
	public Page<InscriptionEnligne> findByNomContains(@Param("x")String mc,Pageable pageable);
	
	
	@Query(value="select * from inscription_enligne where valide_enligne=1",nativeQuery = true)
	public Page<InscriptionEnligne> findByValideEnligne(Pageable pageable);

	@Query(value="select e.cne from inscription_enligne e where e.idie = ?",nativeQuery=true)
	public String findCneById(Long id);


	public String findByCne(String cne);
	
}
