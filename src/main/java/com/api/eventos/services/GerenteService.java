package com.api.eventos.services;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.api.eventos.models.GerenteModel;
import com.api.eventos.repositories.GerenteRepository;

@Service
public class GerenteService {

    final GerenteRepository GerenteRepository;

    public GerenteService(GerenteRepository GerenteRepository) {
        this.GerenteRepository = GerenteRepository;
    }

    @Transactional
    public GerenteModel save(GerenteModel GerenteModel) {
        return GerenteRepository.save(GerenteModel);
    }

    public boolean existsByLogin(String login) {
        return GerenteRepository.existsByLogin(login);
    }

    public boolean existsByEmail(String email) {
        return GerenteRepository.existsByEmail(email);
    }

    public boolean existsByCelular(String celular) {
        return GerenteRepository.existsByCelular(celular);
    }

    public List<GerenteModel> findAll() {
        return GerenteRepository.findAll();
    }

    public Optional<GerenteModel> findById(Long id) {
        return GerenteRepository.findById(id);
    }

    public Optional<GerenteModel> findByLoginAndPassword(String login, String password) {
        return GerenteRepository.findByLoginAndPassword(login, password);
    }

    public Optional<GerenteModel> findByLogin(String login) {
        return GerenteRepository.findByLogin(login);
    }

    @Transactional
    public void delete(GerenteModel GerenteModel) {
        GerenteRepository.delete(GerenteModel);
    }
}