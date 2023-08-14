package com.api.pontoeletronico.services;

import java.util.Optional;

import com.api.pontoeletronico.models.UserModel;
import com.api.pontoeletronico.repositories.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

    public Page<UserModel> findAll(Pageable pageable) {
        return UserRepository.findAll(pageable);
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