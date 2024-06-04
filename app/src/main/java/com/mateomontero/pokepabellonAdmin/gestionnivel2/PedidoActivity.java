package com.mateomontero.pokepabellonAdmin.gestionnivel2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mateomontero.pokepabellon.R;
import com.mateomontero.pokepabellonAdmin.MainAdminActivity;
import com.mateomontero.pokepabellonAdmin.adapters.ListAdapterDetallesPedido;
import com.mateomontero.pokepabellonAdmin.gestionnivel1.GestionPedidosActivity;
import com.mateomontero.pokepabellonAdmin.modelo.DatosProducto;
import com.mateomontero.pokepabellonAdmin.modelo.Direccion;
import com.mateomontero.pokepabellonAdmin.modelo.Pedido;
import com.mateomontero.pokepabellonAdmin.modelo.Usuario;

import java.util.ArrayList;

public class PedidoActivity extends AppCompatActivity {
Pedido pedido;
int id_pedido;
    Pedido pedido2;
ArrayList<DatosProducto> productos=new ArrayList<DatosProducto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        try {
            pedido=(Pedido) getIntent().getSerializableExtra("pedido");
        }
        catch (Exception e){}

        consultarProductos();
        new Handler().postDelayed(() -> {
          listar();
        }, 3500);
        Button b_main=(Button) findViewById(R.id.pedidoActivity_PaginaPrincipal);
        Button b_cancelarPedido=(Button) findViewById(R.id.pedidoActivity_CancelarPedido);

        b_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PedidoActivity.this, GestionPedidosActivity.class);

                startActivity(i);

                finish();
            }
        });

        b_cancelarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(PedidoActivity.this, GestionPedidosActivity.class);
                cancelar();

                startActivity(i);

                finish();
            }
        });
    }





    private void cancelar(){
        productos=new ArrayList<DatosProducto>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("pedidos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String text = ds.getValue().toString();
                    Pedido pedido2=new Pedido(text);
                    pedido2.setKey(ds.getKey());
                            if (pedido.getKey().equalsIgnoreCase(pedido2.getKey())){
                                ds.getRef().removeValue();
                    }



                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        DatabaseReference refEst = FirebaseDatabase.getInstance().getReference("estado");
        refEst.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String text = ds.getValue().toString();
                    if (pedido.getKey().equalsIgnoreCase(ds.getKey())){
                        ds.getRef().removeValue();
                    }

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

        DatabaseReference refPag = FirebaseDatabase.getInstance().getReference("pago");
        refPag.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    if (pedido.getKey().equalsIgnoreCase(ds.getKey())){
                        ds.getRef().removeValue();
                    }



                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }

    private void consultarProductos(){
        productos=new ArrayList<DatosProducto>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("pedidos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String text = ds.getValue().toString();
                    Pedido pedido2=new Pedido(text);
                    pedido2.setKey(ds.getKey());
                    String[] textoSeparado=text.split(";");
                    if (pedido.getKey().equalsIgnoreCase(pedido2.getKey())){

                        for (int i=3;i<textoSeparado.length;i=i+3){
                            int cantidad= Integer.parseInt(textoSeparado[i+1]);
                            int precio=Integer.parseInt(textoSeparado[i+2]);
                            DatosProducto datosProducto2=new DatosProducto(textoSeparado[i],cantidad,precio);
                            productos.add(datosProducto2);
                        }
                    }



                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }

    private void listar() {

        ListAdapterDetallesPedido listAdapterDetallesPedido = new ListAdapterDetallesPedido(productos, PedidoActivity.this);



        RecyclerView recyclerView = findViewById(R.id.listaP);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PedidoActivity.this));
        recyclerView.setAdapter(listAdapterDetallesPedido);
    }


}