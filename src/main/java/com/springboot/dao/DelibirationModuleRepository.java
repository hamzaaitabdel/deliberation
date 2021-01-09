package com.springboot.dao;

import org.springframework.stereotype.Repository;

import com.springboot.entities.DelibirationModule;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface DelibirationModuleRepository extends JpaRepository<DelibirationModule,Long>{
    
}
