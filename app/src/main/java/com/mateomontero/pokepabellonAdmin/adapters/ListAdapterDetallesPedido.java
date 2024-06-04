package com.mateomontero.pokepabellonAdmin.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mateomontero.pokepabellon.R;
import com.mateomontero.pokepabellonAdmin.modelo.DatosProducto;
import com.mateomontero.pokepabellonAdmin.modelo.ItemCarrrito;
import com.mateomontero.pokepabellonAdmin.modelo.Pedido;
import com.mateomontero.pokepabellonAdmin.modelo.Producto;

import java.util.List;

public class ListAdapterDetallesPedido extends RecyclerView.Adapter<ListAdapterDetallesPedido.ViewHolder> {
    public Uri uriD;
    private Context context;
    private List<DatosProducto> mData;
    private LayoutInflater mInflater;
    public ListAdapterDetallesPedido.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Pedido pedido);
    }

    public ListAdapterDetallesPedido(List<DatosProducto> itemList, Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public ListAdapterDetallesPedido.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_pedido_detalles, parent,false);
        return new ListAdapterDetallesPedido.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterDetallesPedido.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<DatosProducto> items) {
        mData = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView producto;
        TextView precio;
        TextView cantidad;



        public ViewHolder(View itemView) {
            super(itemView);
            producto = itemView.findViewById(R.id.textViewProductoLayoutCarrito);
            precio = itemView.findViewById(R.id.textViewPrecioProductoLayoutCarrito);
            cantidad = itemView.findViewById(R.id.textViewCantidadProductoCarrito);
        }

        public void bindData(final DatosProducto item) {
            producto.setText(item.getNombre());
            precio.setText(item.getPrecio() + " €");
            int c = item.getCantidad();
            cantidad.setText("" + c);




        }
    }






}


