package com.api.eventos.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.eventos.dtos.GerenteDto;
import com.api.eventos.dtos.LoginGerenteDto;
import com.api.eventos.dtos.LoginGerenteResponseDto;
import com.api.eventos.models.GerenteModel;
import com.api.eventos.services.GerenteService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/gerente")
public class GerenteController {

    final GerenteService GerenteService;

    public GerenteController(GerenteService GerenteService) {
        this.GerenteService = GerenteService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Object> saveGerente(@RequestBody @Valid GerenteDto GerenteDto) {
        if (GerenteService.existsByLogin(GerenteDto.getLogin())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Login já está em uso!");
        }
        if (GerenteService.existsByEmail(GerenteDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Email já está em uso!");
        }
        if (GerenteService.existsByCelular(GerenteDto.getCelular())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Celular já está em uso!");
        }
        var GerenteModel = new GerenteModel();
        BeanUtils.copyProperties(GerenteDto, GerenteModel);
        GerenteModel.setEmailDate(LocalDateTime.now(ZoneId.of("UTC")));
        GerenteModel.setPassword(passwordEncoder.encode(GerenteDto.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(GerenteService.save(GerenteModel));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginGerenteDto loginGerenteDto) {
        Optional<GerenteModel> gerenteModelOptional = GerenteService.findByLogin(loginGerenteDto.getLogin());

        if (gerenteModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }

        GerenteModel gerenteModel = gerenteModelOptional.get();

        if (!passwordEncoder.matches(loginGerenteDto.getPassword(), gerenteModel.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }

        LoginGerenteResponseDto loginGerenteResponseDto = new LoginGerenteResponseDto();
        loginGerenteResponseDto.setName(gerenteModel.getName());
        loginGerenteResponseDto.setId(gerenteModel.getId());
        loginGerenteResponseDto.setEmail(gerenteModel.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(loginGerenteResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<GerenteModel>> getAllGerentes() {
        return ResponseEntity.status(HttpStatus.OK).body(GerenteService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneGerente(@PathVariable(value = "id") Long id) {
        Optional<GerenteModel> gerenteOptional = GerenteService.findById(id);
        if (!gerenteOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Gerente não encontrado.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(gerenteOptional.get());
    }
}
