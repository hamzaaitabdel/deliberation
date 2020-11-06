package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Semestre;
@Repository
public interface SemestreRepository extends JpaRepository<Semestre,Long> {

}
