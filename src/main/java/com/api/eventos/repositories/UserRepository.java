package com.api.eventos.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.eventos.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByLogin(String login);

    boolean existsByEmail(String email);

    boolean existsByCelular(String celular);

    Optional<UserModel> findByLoginAndPassword(String login, String password);

    Optional<UserModel> findByLogin(String login);
}
