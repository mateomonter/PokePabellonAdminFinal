package com.mateomontero.pokepabellonAdmin.gestionnivel1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mateomontero.pokepabellon.R;
import com.mateomontero.pokepabellonAdmin.adapters.ListAdapterPedido;
import com.mateomontero.pokepabellonAdmin.MainAdminActivity;
import com.mateomontero.pokepabellonAdmin.gestionnivel2.PedidoActivity;
import com.mateomontero.pokepabellonAdmin.modelo.Pedido;
import com.mateomontero.pokepabellonAdmin.modelo.Usuario;
import com.mateomontero.pokepabellonAdmin.modelo.Direccion;


import java.util.ArrayList;

public class GestionPedidosActivity extends AppCompatActivity {
    ArrayList<Pedido> pedidos=new ArrayList<Pedido>();
    ArrayList<Usuario> usuarios=new ArrayList<Usuario>();
    ArrayList<Direccion> direccions=new ArrayList<Direccion>();

    Direccion direccionC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_pedidos);


        Button b_main=(Button) findViewById(R.id.PedidoActivityPrincipal);



        consultarUsuarios();
        consultarDireccion();
        new Handler().postDelayed(() -> {
            consultar();
        }, 2500);
        new Handler().postDelayed(() -> {
            adaptar();
        }, 3500);




        b_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GestionPedidosActivity.this, MainAdminActivity.class);


                startActivity(i);

                finish();
            }
        });







    }

    private void consultar() {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("pedidos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String text = ds.getValue().toString();
                    Pedido pedido2=new Pedido(text);
                    String correoCheckPedido=pedido2.getCorreo();
                    pedido2.setKey(ds.getKey());
                    for(Usuario u:usuarios){
                        String correoCheckUsuario=u.getCorreo();
                        for(Direccion d:direccions){
                            if (u.getKey().equalsIgnoreCase(d.getId())){
                                if(correoCheckPedido.equalsIgnoreCase(correoCheckUsuario)) {
                                    pedido2.setId_direccion(d.getId());
                                }
                            }
                        }
                    }
                    pedidos.add(pedido2);


                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }
    private void adaptar(){
        ListAdapterPedido listAdapter = new ListAdapterPedido(pedidos, GestionPedidosActivity.this, new ListAdapterPedido.OnItemClickListener() {
            @Override
            public void onItemClick(Pedido pedido) {
                moveToDescription(pedido);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.RecyclerPedidos);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(GestionPedidosActivity.this));
        recyclerView.setAdapter(listAdapter);
    }

    private void moveToDescription(Pedido pedido) {
        Intent intent = new Intent(this, PedidoActivity.class);

        intent.putExtra("pedido", pedido);

        startActivity(intent);
        finish();
    }

    private void consultarUsuarios() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String textUser = ds.getValue().toString();
                    Usuario usuario=new Usuario(textUser);

                        usuario.setKey(ds.getKey());
                        usuarios.add(usuario);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }
    private void consultarDireccion() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("direcciones");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String text = ds.getKey();

                        String direccionB = ds.getValue().toString();
                        direccionC=new Direccion(direccionB);
                        direccionC.setId(ds.getKey());
                        direccions.add(direccionC);
                    }
                }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }
}