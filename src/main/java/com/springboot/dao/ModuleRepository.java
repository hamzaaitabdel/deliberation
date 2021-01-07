package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.entities.Module;
@Repository
public interface ModuleRepository extends JpaRepository<Module,Long> {
	
	public Long findIdByName(String module);
}
