package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.entities.ResponsableFiliere;
@Repository
public interface ResponsableFiliereRepository extends JpaRepository<ResponsableFiliere,Long> {

}
