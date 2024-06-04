package com.mateomontero.pokepabellonAdmin.gestionnivel2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mateomontero.pokepabellon.R;
import com.mateomontero.pokepabellonAdmin.gestionnivel1.GestionProductoActivity;
import com.mateomontero.pokepabellonAdmin.modelo.Datos;
import com.mateomontero.pokepabellonAdmin.modelo.Producto;

public class RemoveProductoActivity extends AppCompatActivity {
Datos datos;
    Producto producto;
    int scantidad=0;
    EditText cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_producto);


        try {
            Bundle b = getIntent().getExtras();
          producto = (Producto) getIntent().getSerializableExtra("producto");


        }
        catch (Exception e){}

        Button e=(Button) findViewById(R.id.RemoveButton);
        Button cambio=(Button) findViewById(R.id.cambiobotonRProductoActivity);
        Button cancel=(Button) findViewById(R.id.cancelButton);
        EditText nombre=(EditText) findViewById(R.id.editTextNombreProductoRActivity);
        EditText descripcion=(EditText) findViewById(R.id.editTextdescripcionRActivity);
        EditText precio=(EditText) findViewById(R.id.editTextprecioproductoRActivity);
        EditText size=(EditText) findViewById(R.id.editTextSizeProductoRActivity);
        EditText nombreimagen=(EditText) findViewById(R.id.editTextNombreImagen);
        cantidad=(EditText) findViewById(R.id.editTextStockRActivity);

        consultarCantidad();
        int sprecio=producto.getPrecio();
       double ssize= producto.getSize();

        nombre.setText(producto.getNombre().toString());
        descripcion.setText(producto.getDescripcion().toString());
        precio.setText(""+sprecio);
         size.setText(""+ssize);
         cantidad.setText(""+scantidad);

        nombreimagen.setText(producto.getNombreImagen().toString());
        cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("productos");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String text = ds.getValue().toString();
                            if(producto.toString().equals(text)){

                                int sprecio= Integer.parseInt(precio.getText().toString());
                                double ssize= Double.parseDouble(size.getText().toString());
                                Producto p=new Producto(nombre.getText().toString(),sprecio,descripcion.getText().toString(),nombreimagen.getText().toString(),ssize);
                                ds.getRef().setValue(p.toString());
                                producto.setKey(ds.getKey());
                            }

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });
                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("stockProductos");
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String text = ds.getKey();
                            if(producto.getKey().equals(text)){
                                int scantidadf=Integer.parseInt(cantidad.getText().toString());
                                ds.getRef().setValue(scantidadf);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });

                Intent i=new Intent(RemoveProductoActivity.this, GestionProductoActivity.class);

                startActivity(i);

                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RemoveProductoActivity.this, GestionProductoActivity.class);

                startActivity(i);

                finish();
            }
        });

       e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RemoveProductoActivity.this,GestionProductoActivity.class);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("productos");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String text = ds.getKey();
                            if(producto.getKey().equals(text)){
                                ds.getRef().removeValue();
                            }

                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });
                startActivity(i);

                finish();
            }
        });

    }



    private void consultarCantidad() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("stockProductos");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String text = ds.getKey();
                    if(producto.getKey().equals(text)){
                        scantidad = Integer.parseInt(ds.getValue().toString());
                    }

                }


                cantidad.setText(""+scantidad);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });



    }



}