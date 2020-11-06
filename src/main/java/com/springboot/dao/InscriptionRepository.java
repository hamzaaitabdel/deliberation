package com.springboot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.springboot.entities.InscriptionOnligne;
@Repository
public interface InscriptionRepository extends JpaRepository<InscriptionOnligne,Long> {

}
