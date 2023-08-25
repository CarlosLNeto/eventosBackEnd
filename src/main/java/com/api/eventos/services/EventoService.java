package com.api.eventos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.eventos.models.EventoModel;
import com.api.eventos.models.UserEventModel;
import com.api.eventos.models.UserModel;
import com.api.eventos.repositories.EventoRepository;
import com.api.eventos.repositories.UserEventRepository;
import com.api.eventos.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class EventoService {

    final EventoRepository EventoRepository;

    final UserEventRepository userEventRepository;

    public EventoService(EventoRepository EventoRepository, UserEventRepository userEventRepository) {
        this.EventoRepository = EventoRepository;
        this.userEventRepository = userEventRepository;
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

    @Autowired
    private UserRepository userRepository;

    public boolean saveUserEvent(Long userId, Long eventId) {
        Optional<EventoModel> eventoModelOptional = EventoRepository.findById(eventId);
        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (eventoModelOptional.isPresent() && userModelOptional.isPresent()) {
            UserEventModel userEventModel = new UserEventModel();
            userEventModel.setUserId(userId);
            userEventModel.setEventId(eventId);

            userEventRepository.save(userEventModel);
            return true;
        }

        return false;
    }

    public List<EventoModel> findEventosByUserId(Long userId) {
        List<UserEventModel> userEvents = userEventRepository.findByUserId(userId);
        List<EventoModel> eventos = new ArrayList<>();

        for (UserEventModel userEvent : userEvents) {
            Optional<EventoModel> eventoModelOptional = EventoRepository.findById(userEvent.getEventId());
            eventoModelOptional.ifPresent(eventos::add);
        }

        return eventos;
    }

}
