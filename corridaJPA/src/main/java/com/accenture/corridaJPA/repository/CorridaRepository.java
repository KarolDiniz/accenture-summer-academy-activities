package com.accenture.corridaJPA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accenture.corridaJPA.model.Corrida;
import jakarta.transaction.Transactional;

@Repository
public interface CorridaRepository extends JpaRepository<Corrida, Long>
{
	List<Corrida> findByLocalId(Long localId);
	
	@Transactional
	void deleteByLocalId(Long localId);
}

