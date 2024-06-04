package com.mateomontero.pokepabellonAdmin.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Carrito implements Serializable {
    private ArrayList<ItemCarrrito> items;

    public Carrito(){
        items=new ArrayList<ItemCarrrito>();
    }

    public void addProducto(Producto p,int cantidad,int precio){
     if (buscarPosicionProducto(p)==-1)
            items.add(new ItemCarrrito(p,cantidad,precio));
    }

    public int buscarPosicionProducto(Producto p){
        for (int i=0;i<items.size();i++) {
            if (items.get(i).getProducto().getNombre().equals(p.getNombre())) return i;
        }
            return -1;
    }

    public void modificarProducto(Producto p,int nueva_cantidad){
        int pos=buscarPosicionProducto(p);
        if (pos!=-1){
            items.get(pos).setCantidad(nueva_cantidad);
        }
    }



    public ArrayList<String> getDatos() {
        ArrayList<String> lista=new ArrayList<String>();
        for (ItemCarrrito ic : items){
            lista.add(ic.getProducto().getNombre()+" "+ic.getCantidad()+" unidades "+ic.getPrecio_unidad()*ic.getCantidad()+"€");
        }
        return lista;
    }
    public int getPrecio() {
        int precio=0;
        for (ItemCarrrito ic : items){
            precio=precio+(ic.getPrecio_unidad()*ic.getCantidad());
        }
        return precio;
    }

    public ArrayList<String> getNombre() {
        ArrayList<String> nombres=new ArrayList<String>();
        for (ItemCarrrito ic : items){
            nombres.add(ic.getProducto().getNombre());
        }
        return nombres;
    }
    public void resetCarrito(){
        items=new ArrayList<ItemCarrrito>();
    }
}
