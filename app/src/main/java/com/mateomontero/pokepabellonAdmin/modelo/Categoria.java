package com.mateomontero.pokepabellonAdmin.modelo;

import java.io.Serializable;

public class Categoria implements Serializable {
    private String tipo;
    private int generacion;

    public Categoria(String tipo, int generacion) {
        this.tipo = tipo;
        this.generacion = generacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getGeneracion() {
        return generacion;
    }

    public void setGeneracion(int generacion) {
        this.generacion = generacion;
    }


}
