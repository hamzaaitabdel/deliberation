package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Element;
@Repository
public interface ElementRepository extends JpaRepository<Element,Long> {

}
