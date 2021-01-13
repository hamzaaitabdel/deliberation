package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.entities.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	@Query("select f from Role f where f.id = :id")
	public Role findById_Role(@Param("id")Long id);
}