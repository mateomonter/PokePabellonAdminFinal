package com.mateomontero.pokepabellonAdmin.modelo;

import java.io.Serializable;

public class Datos implements Serializable {

    Usuario usuario;
    Carrito carrito;

    Producto producto;

    public Datos(){
        usuario=null;
        carrito=null;
        producto=null;

    }


    public Datos(Usuario usuario, Carrito carrito) {
        this.usuario = usuario;
        this.carrito = carrito;
    }


    public Datos(Usuario usuario, Carrito carrito, Producto producto) {
        this.usuario = usuario;
        this.carrito = carrito;
        this.producto = producto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}

