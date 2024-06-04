package com.mateomontero.pokepabellonAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mateomontero.pokepabellon.R;
import com.mateomontero.pokepabellonAdmin.gestionnivel1.GestionPedidosActivity;
import com.mateomontero.pokepabellonAdmin.gestionnivel1.GestionProductoActivity;
import com.mateomontero.pokepabellonAdmin.gestionnivel1.GestionUsarioActivity;
import com.mateomontero.pokepabellonAdmin.modelo.Carrito;
import com.mateomontero.pokepabellonAdmin.modelo.Datos;


public class MainAdminActivity extends AppCompatActivity {
    private Datos datos;
    Carrito carrito;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        //recup
        try {
            Bundle b = getIntent().getExtras();
            datos=(Datos)b.getSerializable("capsula");
        }
        catch (Exception e){}


        Button pedidos=(Button) findViewById(R.id.AdminActivityGestionPedido);
        Button b_usuarios=(Button) findViewById(R.id.AdminActivityGestionUsuario);
        Button b_productos=(Button) findViewById(R.id.adminActivityGestionProductos);
        Button b_imagenes=(Button) findViewById(R.id.AdminActivityImagenes);



        pedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainAdminActivity.this, GestionPedidosActivity.class);

                startActivity(i);

                finish();
            }
        });
        b_usuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainAdminActivity.this, GestionUsarioActivity.class);
                i.putExtra("capsula",datos);
                startActivity(i);

                finish();
            }
        });
        b_productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainAdminActivity.this, GestionProductoActivity.class);
                i.putExtra("capsula",datos);
                startActivity(i);

                finish();
            }
        });



      b_imagenes.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i=new Intent(MainAdminActivity.this,UploadImagesActivity.class);

              startActivity(i);

              finish();
          }
      });
    }
}