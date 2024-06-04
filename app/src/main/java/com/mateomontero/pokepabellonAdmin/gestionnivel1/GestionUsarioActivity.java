package com.mateomontero.pokepabellonAdmin.gestionnivel1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mateomontero.pokepabellon.R;
import com.mateomontero.pokepabellonAdmin.gestionnivel2.ADminUsuariosActivity;
import com.mateomontero.pokepabellonAdmin.adapters.ListAdapterUsuario;
import com.mateomontero.pokepabellonAdmin.MainAdminActivity;
import com.mateomontero.pokepabellonAdmin.modelo.Datos;
import com.mateomontero.pokepabellonAdmin.modelo.Usuario;

import java.util.ArrayList;

public class GestionUsarioActivity extends AppCompatActivity {
    Datos datos;
    ArrayList<String> usuarios;
    ArrayList<Usuario> usuariosS=new ArrayList<Usuario>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_usario);

        //recup


        Button b_main = (Button) findViewById(R.id.GestionUActivitybotonP);


        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        new Handler().postDelayed(() -> {
            consultar();
        }, 2500);

        b_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GestionUsarioActivity.this, MainAdminActivity.class);
                i.putExtra("capsula", datos);
                startActivity(i);

                finish();
            }
        });
    }


    public void moveToDescription(Usuario usuario) {
        Intent intent = new Intent(this, ADminUsuariosActivity.class);
        intent.putExtra("usuario", usuario);
        startActivity(intent);
        finish();
    }

    private void consultar() {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String text = ds.getValue().toString();
                    Usuario usuario=new Usuario(text);
                    usuario.setKey(ds.getKey());
                    usuariosS.add(usuario);

                }


                ListAdapterUsuario listAdapter = new ListAdapterUsuario(usuariosS, GestionUsarioActivity.this, new ListAdapterUsuario.OnItemClickListener() {
                    @Override
                    public void onItemClick(Usuario usuario) {
                        moveToDescription(usuario);
                    }
                });
                RecyclerView recyclerView = findViewById(R.id.UsuariosRecyclerViewGestionUsusariosActivity);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(GestionUsarioActivity.this));
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }
}