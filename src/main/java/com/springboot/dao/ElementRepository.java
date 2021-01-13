package com.springboot.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.springboot.entities.*;
import com.springboot.entities.Module;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {
    
	
	@Query("select s from Element s where s.id = :id")
	public Element findById_Element(@Param("id")Long id);
	
    @Query("select e from Element e where module =:module")
    public List<Element> elements(@Param("module")Module module);

	public Long findIdByName(String element);

	@Query("select d from Element d where d.module.id= :x")
	public List<Element> findById_moduleContains(@Param("x")Long parseLong);
}
