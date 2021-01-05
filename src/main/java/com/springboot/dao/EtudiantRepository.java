package com.springboot.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.entities.Etudiant;
import com.springboot.entities.Filiere;
@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant,String> {

	
	@Query("select e from Etudiant e where e.nom_etud like :x ")
    public Page<Etudiant> findByNom_etudContains(@Param("x")String keyword, Pageable of);

	@Query(value="select iu from Etudiant iu where iu.filiere.id_filiere like :x ")
	public Page<Etudiant> findById_FiliereContains(@Param("x")Long Id_filiere,Pageable of);

	@Transactional
	@Modifying
	@Query("update Etudiant s set s= :e where s.cne= :cne")
	public void UpdateEtudiant(@Param("e")Etudiant e, @Param("cne")String cne);
	
	/*
	 * @Query("select e from Etudiant e where e.Filiere.id_filiere =:x") public
	 * List<Etudiant> findAllById_filiere(@Param("x")Long filiere);
	 */
}
