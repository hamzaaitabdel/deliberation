package com.springboot.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Element;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {
	
	public Long findIdByName(String element);
	
	@Transactional
	@Modifying
	@Query("delete from Element s where s.id = :id")
	public void DeleteIDEl(@Param("id")Long id);
	
	
}
