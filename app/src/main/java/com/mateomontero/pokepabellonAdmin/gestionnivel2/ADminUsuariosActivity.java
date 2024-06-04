package com.mateomontero.pokepabellonAdmin.gestionnivel2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mateomontero.pokepabellon.R;
import com.mateomontero.pokepabellonAdmin.MainAdminActivity;
import com.mateomontero.pokepabellonAdmin.gestionnivel1.GestionUsarioActivity;
import com.mateomontero.pokepabellonAdmin.modelo.Datos;
import com.mateomontero.pokepabellonAdmin.modelo.Direccion;
import com.mateomontero.pokepabellonAdmin.modelo.Pedido;
import com.mateomontero.pokepabellonAdmin.modelo.Usuario;

import java.util.ArrayList;
import java.util.Arrays;

public class ADminUsuariosActivity extends AppCompatActivity {
Datos datos;
    Usuario usuario;
int id_usuario;
String direccionB;
Direccion direccion;
EditText pais;
EditText cp;
EditText provincia;
EditText ciudad;
EditText calle;
    ArrayList<String> listaLlaves=new ArrayList<String>();
    boolean pendiente=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_usuarios);



        try {
            Bundle b = getIntent().getExtras();
            usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        }
        catch (Exception e){}

        Button b_main=(Button) findViewById(R.id.AUserActivity_PaginaPrincipal);
        Button b_delete=(Button) findViewById(R.id.AUserActivityEliminarUsuario);
        Button b_cambio=(Button) findViewById(R.id.AUserActivityCambiarCaracteristicas);
        EditText nnombre=(EditText) findViewById(R.id.AdminUserEditNombre);
        EditText ncorreo=(EditText) findViewById(R.id.AdminUserEditCorreo);
        EditText npassword=(EditText) findViewById(R.id.AdminUserEditPassword);
        pais = (EditText) findViewById(R.id.AdminUserActivityeditTextTextPais);
        ciudad = (EditText) findViewById(R.id.AdminUserActivityeditTextTextCiudad);
        cp = (EditText) findViewById(R.id.AdminUserActivityeditTextTextCP);
        calle = (EditText) findViewById(R.id.AdminUserActivityeditTextTextCalle);
        provincia = (EditText) findViewById(R.id.AdminUserActivityeditTextTextProvincia);

        b_cambio.setVisibility(View.INVISIBLE);
        b_delete.setVisibility(View.INVISIBLE);
        consultarDireccion();
        consultarPedidos();
        new Handler().postDelayed(() -> {
            consultarEstados();
        }, 3500);
        new Handler().postDelayed(() -> {
            b_cambio.setVisibility(View.VISIBLE);
            b_delete.setVisibility(View.VISIBLE);
        }, 5500);



        nnombre.setText(usuario.getNombre().toString());
        ncorreo.setText(usuario.getCorreo().toString());
        npassword.setText(usuario.getPassward().toString());


        String uid="";
       //FirebaseAuth aut=FirebaseAuth.getInstance().listUsers(null));


        b_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String text = ds.getValue().toString();

                        if(usuario.toString().equals(text)){
                            if (pendiente==false) {

                                ds.getRef().removeValue();
                                borrarPedidos();
                                 borrarEstados();
                                 borrarPagos();


                                Intent i=new Intent(ADminUsuariosActivity.this, GestionUsarioActivity.class);


                                startActivity(i);

                                finish();
                            }else{
                                Toast.makeText(ADminUsuariosActivity.this, R.string.falloUsuario, Toast.LENGTH_SHORT).show();
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
        });
        b_cambio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String text = ds.getKey();
                            if(usuario.getKey().equals(text)){
                                Usuario u=new Usuario(nnombre.getText().toString(),ncorreo.getText().toString(),npassword.getText().toString());
                                ds.getRef().setValue(u.toString());
                            }


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });

                DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("direcciones");
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            String text = ds.getKey();
                            if(usuario.getKey().equals(text)){

                                Direccion direccion=new Direccion(ciudad.getText().toString(),cp.getText().toString(),pais.getText().toString(),provincia.getText().toString(),calle.getText().toString());
                                ds.getRef().setValue(direccion.toString());

                            }
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });

                DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("pedidos");
                ref3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {


                               String ped=ds.getValue().toString();
                               Pedido pedido=new Pedido(ped);
                            String[] textoSeparado = ped.split(";");
                               if (pedido.getKey().equalsIgnoreCase(usuario.getKey())) {
                                   textoSeparado[0]=ncorreo.getText().toString();
                                   String nuevoPedido="";
                                   for (int i = 0; i < textoSeparado.length; i++) {
                                       if (i==textoSeparado.length){
                                           nuevoPedido = nuevoPedido + textoSeparado[i];
                                       }else {
                                           nuevoPedido = nuevoPedido + textoSeparado[i] + ";";
                                       }
                                   }


                                   ds.getRef().setValue(nuevoPedido);
                               }


                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // ...
                    }
                });

                Intent i=new Intent(ADminUsuariosActivity.this,GestionUsarioActivity.class);

                startActivity(i);

                finish();
            }
        });


        b_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ADminUsuariosActivity.this, GestionUsarioActivity.class);

                startActivity(i);

                finish();
            }
        });
    }

    private void consultarPedidos(){
        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("pedidos");
        ref3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {


                    String ped=ds.getValue().toString();
                    Pedido pedido=new Pedido(ped);
                    String[] textoSeparado = ped.split(";");
                    if (textoSeparado[0].equalsIgnoreCase(usuario.getCorreo())) {

                    listaLlaves.add(ds.getKey());
                    }


                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });

    }
    private void borrarPedidos(){
        DatabaseReference refPedido = FirebaseDatabase.getInstance().getReference("pedidos");
        refPedido.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String text = ds.getValue().toString();
                    Usuario usuario1=new Usuario(text);
                    if(usuario1.getNombre().equals(usuario.getCorreo())){

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

    private void borrarEstados(){
        DatabaseReference refEstado = FirebaseDatabase.getInstance().getReference("estado");
        refEstado.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();


                    for(String llave:listaLlaves) {

                        if(llave.equalsIgnoreCase(key))
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
    private void borrarPagos(){
        DatabaseReference refPago = FirebaseDatabase.getInstance().getReference("pago");
        refPago.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();


                    for(String llave:listaLlaves) {

                        if(llave.equalsIgnoreCase(key))
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
    private void consultarEstados(){
        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference("estado");
        ref3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String val=ds.getValue().toString();
                    if(listaLlaves.size()!=0) {
                        for (String llave : listaLlaves) {
                            if (llave.equalsIgnoreCase(ds.getKey())){
                            if (pendiente == false) {
                                if (val.equalsIgnoreCase("no entregado")) {
                                    pendiente = true;
                                } else {
                                    pendiente = false;
                                }
                            }
                            }
                        }
                    }else {
                            pendiente=false;
                        }
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
                    if (usuario.getKey().equals(text)) {
                        direccionB = ds.getValue().toString();
                        direccion=new Direccion(direccionB);
                    }

                }
                pais.setText(direccion.getPais().toString());
                ciudad.setText(direccion.getCiudad().toString());
                cp.setText(""+direccion.getCp().toString());
                calle.setText(direccion.getCalle().toString());
                provincia.setText(direccion.getProvincia().toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }

    }
