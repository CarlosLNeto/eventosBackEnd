package com.api.eventos.services;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.api.eventos.models.UserModel;
import com.api.eventos.repositories.UserRepository;

@Service
public class UserService {

    final UserRepository UserRepository;

    public UserService(UserRepository UserRepository) {
        this.UserRepository = UserRepository;
    }

    @Transactional
    public UserModel save(UserModel UserModel) {
        return UserRepository.save(UserModel);
    }

    public boolean existsByLogin(String login) {
        return UserRepository.existsByLogin(login);
    }

    public boolean existsByEmail(String email) {
        return UserRepository.existsByEmail(email);
    }

    public boolean existsByCelular(String celular) {
        return UserRepository.existsByCelular(celular);
    }

    public List<UserModel> findAll() {
        return UserRepository.findAll();
    }

    public Optional<UserModel> findById(Long id) {
        return UserRepository.findById(id);
    }

    public Optional<UserModel> findByLoginAndPassword(String login, String password) {
        return UserRepository.findByLoginAndPassword(login, password);
    }

    public Optional<UserModel> findByLogin(String login) {
        return UserRepository.findByLogin(login);
    }

    @Transactional
    public void delete(UserModel UserModel) {
        UserRepository.delete(UserModel);
    }
}