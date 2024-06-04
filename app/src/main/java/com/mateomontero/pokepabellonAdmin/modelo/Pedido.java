package com.mateomontero.pokepabellonAdmin.modelo;

import java.io.Serializable;

public class Pedido implements Serializable {


    String id_direccion;

    int precio;

    String correo;

    String key;


    public Pedido(String key, int precio, String correo) {
        this.key=key;
        this.precio = precio;
        this.correo=correo;
    }

    public Pedido (String txt){
        try {
            String[]data=txt.split(";");
            this.correo=data[0];
            this.precio= Integer.parseInt(data[1]);
            this.key= data[2];
        }catch (Exception e){

        }

    }



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(String id_direccion) {
        this.id_direccion = id_direccion;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
