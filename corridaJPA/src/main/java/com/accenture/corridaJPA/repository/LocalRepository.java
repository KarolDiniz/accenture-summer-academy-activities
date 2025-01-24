package com.accenture.corridaJPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.corridaJPA.model.Local;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long>
{
}



