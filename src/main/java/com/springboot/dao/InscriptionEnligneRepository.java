package com.springboot.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;
import com.springboot.entities.InscriptionEnligne;
@Repository
public interface InscriptionEnligneRepository extends JpaRepository<InscriptionEnligne,Long> {
	@Query("select e from InscriptionEnligne e where e.nom_fr like :x order by e.date_inscription")
	public Page<InscriptionEnligne> findByNom_frContains(@Param("x")String mc,Pageable pageable);
	
	public InscriptionEnligne findByCne(String id);
	
	public void deleteByCne(String id);
}
