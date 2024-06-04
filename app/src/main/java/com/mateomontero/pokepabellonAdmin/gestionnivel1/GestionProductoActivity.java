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
import com.mateomontero.pokepabellonAdmin.adapters.ListAdapterProducto;
import com.mateomontero.pokepabellonAdmin.MainAdminActivity;
import com.mateomontero.pokepabellonAdmin.gestionnivel2.AddProductosActivity;
import com.mateomontero.pokepabellonAdmin.gestionnivel2.RemoveProductoActivity;
import com.mateomontero.pokepabellonAdmin.modelo.Producto;

import java.util.ArrayList;

public class GestionProductoActivity extends AppCompatActivity {


ArrayList<Producto> productos=new ArrayList<Producto>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_producto);

        //recup


        Button b_main=(Button) findViewById(R.id.GestionActivityProdbotonMain);
        Button b_Add=(Button) findViewById(R.id.GProductoActivityAddPedido);


        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("productos");

        new Handler().postDelayed(() -> {
            constuirListaProductos();
        }, 2500);


        b_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GestionProductoActivity.this, MainAdminActivity.class);

                startActivity(i);

                finish();
            }
        });

        b_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(GestionProductoActivity.this, AddProductosActivity.class);

                startActivity(i);

                finish();
            }
        });













    }    public void moveToDescription(Producto producto) {
        Intent intent = new Intent(this, RemoveProductoActivity.class);
        intent.putExtra("producto", producto);
        startActivity(intent);
        //finish();
    }
    private void constuirListaProductos() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("productos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String text = ds.getValue().toString();
                    Producto producto=new Producto(text);
                    producto.setKey(ds.getKey());
                    productos.add(producto);


                }


                ListAdapterProducto listAdapter = new ListAdapterProducto(productos, GestionProductoActivity.this, new ListAdapterProducto.OnItemClickListener() {
                    @Override
                    public void onItemClick(Producto producto) {
                        moveToDescription(producto);
                    }
                });
                RecyclerView recyclerView = findViewById(R.id.GestionActivitylistaProductos);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(GestionProductoActivity.this));
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }

}