package com.mateomontero.pokepabellonAdmin.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mateomontero.pokepabellon.R;
import com.mateomontero.pokepabellonAdmin.modelo.Direccion;
import com.mateomontero.pokepabellonAdmin.modelo.Pedido;

import java.util.List;

public class ListAdapterPedido extends RecyclerView.Adapter<ListAdapterPedido.ViewHolder>{
    public Uri uriD;
    private Context context;
    private List<Pedido> mData;
    private LayoutInflater mInflater;
    public ListAdapterPedido.OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(Pedido pedido);
    }

    public ListAdapterPedido(List<Pedido> itemList, Context context, ListAdapterPedido.OnItemClickListener listener) {
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
    public ListAdapterPedido.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_item_pedido,parent,false);
        return new ListAdapterPedido.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapterPedido.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItems(List<Pedido> items) { mData = items; }

    public class ViewHolder  extends RecyclerView.ViewHolder {

        TextView usuario1;
        TextView direccion1;
        TextView direccionR;
        TextView precio1;
        Switch switchEntrega;


        public ViewHolder(View itemView) {
            super(itemView);
            usuario1=itemView.findViewById(R.id.nombreusuariopedido);
            direccion1=itemView.findViewById(R.id.idpedido);
            precio1=itemView.findViewById(R.id.preciopedido);
            switchEntrega=itemView.findViewById(R.id.switchEntrega);
            direccionR=itemView.findViewById(R.id.textViewDireccion);

        }






        public void bindData(final Pedido item) {
               usuario1.setText("correo del comprador: "+item.getCorreo());
                direccion1.setText("id_pedido: "+item.getKey());
               precio1.setText(item.getPrecio()+" €");

            DatabaseReference refD = FirebaseDatabase.getInstance().getReference("direcciones");
            refD.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String text = ds.getKey();
                        Direccion direccionP=new Direccion(ds.getValue().toString());
                        String dCheck=item.getId_direccion();
                        String direccionS=direccionP.getPais()+" "+direccionP.getCiudad()+" "+direccionP.getProvincia()+" "+direccionP.getCalle()+" "+direccionP.getCp()+" ";
                        if (dCheck.equalsIgnoreCase(text)) {
                            direccionR.setText("direccion: "+direccionS);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // ...
                }
            });
            DatabaseReference refE = FirebaseDatabase.getInstance().getReference("estado");
            refE.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String text = ds.getValue().toString();
                        String key=item.getKey();
                        if(text.equalsIgnoreCase("entragado")&&item.getKey().equalsIgnoreCase(ds.getKey())){

                            switchEntrega.setChecked(true);
                        }else {
                            if(text.equalsIgnoreCase("no entragado")&&item.getKey().equalsIgnoreCase(ds.getKey())){

                                switchEntrega.setChecked(false);
                            }

                        }


                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // ...
                }
            });


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

            switchEntrega.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (switchEntrega.isChecked())
                    {
                        //Perform action when you touch on checkbox and it change to selected state
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("estado");
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String text = ds.getKey();
                                    String key=item.getKey();
                                    if(key.equals(text)){
                                        ds.getRef().setValue("entragado");
                                    }


                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // ...
                            }
                        });
                    }
                    else
                    {
                        //Perform action when you touch on checkbox and it change to unselected state
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("estado");
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    String text = ds.getKey();
                                    String key=item.getKey();
                                    if(key.equals(text)){
                                        ds.getRef().setValue("no entragado");
                                    }


                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                // ...
                            }
                        });
                    }
                }
            });
        }
    }






}
