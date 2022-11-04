package com.example.beta_version_1;

public class plantilla_usuario {
    String nombre,contraseña;

    public plantilla_usuario(String nombre,String pass) {
        this.nombre = nombre;
        this.contraseña=pass;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }
}
