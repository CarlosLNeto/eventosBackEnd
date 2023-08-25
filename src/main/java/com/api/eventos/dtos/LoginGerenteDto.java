package com.api.eventos.dtos;

import jakarta.validation.constraints.NotBlank;

public class LoginGerenteDto {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
