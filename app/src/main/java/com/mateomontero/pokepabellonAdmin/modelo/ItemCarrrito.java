package com.mateomontero.pokepabellonAdmin.modelo;

import java.io.Serializable;

public class ItemCarrrito implements Serializable

{
    Producto producto;
    int cantidad;
    int precio_unidad;

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio_unidad(int precio_unidad) {
        this.precio_unidad = precio_unidad;
    }

    public ItemCarrrito(Producto producto, int cantidad, int precio_unidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio_unidad = precio_unidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getPrecio_unidad() {
        return precio_unidad;
    }
}
