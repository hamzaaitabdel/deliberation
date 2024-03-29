package com.springboot.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

import com.springboot.entities.DelibirationModule;
import com.springboot.entities.Module;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface DelibirationModuleRepository extends JpaRepository<DelibirationModule,Long>{
    @Query("select d from DelibirationModule d where d.module=:module")
    public List<DelibirationModule> findByModule(@Param("module")Module module);
}
