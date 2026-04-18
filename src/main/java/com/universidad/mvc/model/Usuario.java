package com.universidad.mvc.model;

public class Usuario implements java.io.Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String email;
    private String rol;

    public Usuario(String username, String email, String rol) {
        this.username = username;
        this.email = email;
        this.rol = rol;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRol() {
        return rol;
    }
}

