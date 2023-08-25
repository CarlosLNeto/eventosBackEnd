package com.api.eventos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.eventos.models.EventoModel;

@Repository
public interface EventoRepository extends JpaRepository<EventoModel, Long> {
    boolean existsByName(String name);

    Optional<EventoModel> findByStatus(Boolean status);
}
