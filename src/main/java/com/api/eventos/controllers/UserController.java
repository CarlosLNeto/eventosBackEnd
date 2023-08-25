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

import com.api.eventos.dtos.LoginUserDto;
import com.api.eventos.dtos.LoginUserResponseDto;
import com.api.eventos.dtos.UserDto;
import com.api.eventos.dtos.UserEventDto;
import com.api.eventos.models.UserModel;
import com.api.eventos.services.EventoService;
import com.api.eventos.services.UserService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

    final UserService UserService;

    @Autowired
    private EventoService eventService;

    public UserController(UserService UserService) {
        this.UserService = UserService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto UserDto) {
        if (UserService.existsByLogin(UserDto.getLogin())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Login já está em uso!");
        }
        if (UserService.existsByEmail(UserDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Email já está em uso!");
        }
        if (UserService.existsByCelular(UserDto.getCelular())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Celular já está em uso!");
        }
        var UserModel = new UserModel();
        BeanUtils.copyProperties(UserDto, UserModel);
        UserModel.setEmailDate(LocalDateTime.now(ZoneId.of("UTC")));
        UserModel.setPassword(passwordEncoder.encode(UserDto.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(UserService.save(UserModel));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginUserDto loginUserDto) {
        Optional<UserModel> userModelOptional = UserService.findByLogin(loginUserDto.getLogin());

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }

        UserModel userModel = userModelOptional.get();

        if (!passwordEncoder.matches(loginUserDto.getPassword(), userModel.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid login credentials");
        }

        LoginUserResponseDto loginUserResponseDto = new LoginUserResponseDto();
        loginUserResponseDto.setName(userModel.getName());
        loginUserResponseDto.setId(userModel.getId());
        loginUserResponseDto.setEmail(userModel.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body(loginUserResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(UserService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") Long id) {
        Optional<UserModel> UserModelOptional = UserService.findById(id);
        if (!UserModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(UserModelOptional.get());
    }

    @PostMapping("/selectEvent")
    public ResponseEntity<String> selectEvent(@RequestBody @Valid UserEventDto userEventDto) {
        boolean success = eventService.saveUserEvent(userEventDto.getUserId(), userEventDto.getEventId());

        if (success) {
            return ResponseEntity.status(HttpStatus.OK).body("Evento selecionado salvo.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao salvar o evento selecionado.");
        }
    }

}