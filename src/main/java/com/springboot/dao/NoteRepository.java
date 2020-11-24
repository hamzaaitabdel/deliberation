package com.springboot.dao;

import org.springframework.stereotype.Repository;

import com.springboot.entities.Note;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface NoteRepository extends JpaRepository<Note,Long>{
    //ublic Page<Note> findAll(Pageable pageable);
}
