package com.mateomontero.pokepabellonAdmin.modelo;

import java.io.Serializable;

public class UsuarioFirebase {


    String nombre,correo,passward;


    public UsuarioFirebase() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassward() {
        return passward;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    public UsuarioFirebase(String nombre, String correo, String passward) {
        this.nombre = nombre;
        this.correo = correo;
        this.passward = passward;
    }
}
