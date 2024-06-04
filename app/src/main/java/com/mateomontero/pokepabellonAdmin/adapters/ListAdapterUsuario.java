package com.mateomontero.pokepabellonAdmin.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mateomontero.pokepabellon.R;
import com.mateomontero.pokepabellonAdmin.modelo.Usuario;

import java.util.List;

public class ListAdapterUsuario extends RecyclerView.Adapter<ListAdapterUsuario.ViewHolder>{
    public Uri uriD;
    private Context context;
    private List<Usuario> mData;
    private LayoutInflater mInflater;
    public ListAdapterUsuario.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Usuario usuario);
    }

    public ListAdapterUsuario(List<Usuario> itemList, Context context, ListAdapterUsuario.OnItemClickListener listener) {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
        this.listener = listener;
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
    @Override
    public ListAdapterUsuario.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_usuario,parent,false);
        return new ListAdapterUsuario.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterUsuario.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Usuario> items) { mData = items; }

    public class ViewHolder  extends RecyclerView.ViewHolder {

        TextView nombre;
        TextView correo;

        public ViewHolder( View itemView) {


            super(itemView);
            nombre=itemView.findViewById(R.id.nombre_usuario);
            correo=itemView.findViewById(R.id.correo_usuario);
        }

        public void bindData(final Usuario item) {
                nombre.setText(item.getNombre());
                correo.setText(item.getCorreo());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }






}
