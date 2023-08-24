package com.api.eventos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.eventos.models.GerenteModel;

@Repository
public interface GerenteRepository extends JpaRepository<GerenteModel, Long> {
    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByCelular(String celular);

    Optional<GerenteModel> findByLoginAndPassword(String login, String password);

    Optional<GerenteModel> findByLogin(String login);
}
