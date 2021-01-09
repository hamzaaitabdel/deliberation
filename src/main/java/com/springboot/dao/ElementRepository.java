package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.springboot.entities.*;
import com.springboot.entities.Module;

@Repository
public interface ElementRepository extends JpaRepository<Element, Long> {
    
    @Query("select e from Element e where module =:module")
    public List<Element> elements(@Param("module")Module module);

}
