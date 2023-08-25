package com.api.eventos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.eventos.models.EventoModel;
import com.api.eventos.repositories.EventoRepository;

import jakarta.transaction.Transactional;

@Service
public class EventoService {

    final EventoRepository EventoRepository;

    public EventoService(EventoRepository EventoRepository) {
        this.EventoRepository = EventoRepository;
    }

    @Transactional
    public EventoModel save(EventoModel EventoModel) {
        return EventoRepository.save(EventoModel);
    }

    public boolean existsByName(String name) {
        return EventoRepository.existsByName(name);
    }

    public List<EventoModel> findAll() {
        return EventoRepository.findAll();
    }

    public Optional<EventoModel> findById(Long id) {
        return EventoRepository.findById(id);
    }

    @Transactional
    public void delete(EventoModel EventoModel) {
        EventoRepository.delete(EventoModel);
    }

}
