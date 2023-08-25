package com.api.eventos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.eventos.dtos.EventoDto;
import com.api.eventos.models.EventoModel;
import com.api.eventos.services.EventoService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/evento")
public class EventoController {

    final EventoService EventoService;

    public EventoController(EventoService EventoService) {
        this.EventoService = EventoService;
    }

    @PostMapping
    public ResponseEntity<Object> saveEvento(@RequestBody @Valid EventoDto EventoDto) {
        if (EventoService.existsByName(EventoDto.getName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Nome já está em uso!");
        }
        var EventoModel = new EventoModel();
        BeanUtils.copyProperties(EventoDto, EventoModel);
        EventoModel.setStatus(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(EventoService.save(EventoModel));
    }

    @GetMapping
    public ResponseEntity<List<EventoModel>> getAllEventos() {
        return ResponseEntity.status(HttpStatus.OK).body(EventoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneEvento(@PathVariable(value = "id") Long id) {
        Optional<EventoModel> eventoModelOptional = EventoService.findById(id);
        if (!eventoModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(eventoModelOptional.get());
    }
}
