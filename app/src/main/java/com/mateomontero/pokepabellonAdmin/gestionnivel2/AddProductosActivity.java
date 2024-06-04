package com.mateomontero.pokepabellonAdmin.gestionnivel2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mateomontero.pokepabellon.R;
import com.mateomontero.pokepabellonAdmin.MainAdminActivity;
import com.mateomontero.pokepabellonAdmin.gestionnivel1.GestionProductoActivity;
import com.mateomontero.pokepabellonAdmin.modelo.Datos;
import com.mateomontero.pokepabellonAdmin.modelo.Producto;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddProductosActivity extends AppCompatActivity {

    Datos datos;
    ImageView imagen1;
    private Bitmap imagePicked;
    private int numberImageSelected = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_productos);
        try {
            Bundle b = getIntent().getExtras();
            datos=(Datos)b.getSerializable("capsula");
        }
        catch (Exception e){}

        Button b_main=(Button) findViewById(R.id.GAddPRoductobotonMain);
        Button b_addP=(Button) findViewById(R.id.GAddPRoductobotonAdd);
        EditText nombre=(EditText) findViewById(R.id.GAddPRoductoEditTextNombre);
        EditText descripcion=(EditText) findViewById(R.id.GAddPRoductoextoDescripion);
        EditText precio=(EditText) findViewById(R.id.GAddPRoductoeditTextNumberPrecio);
        EditText size=(EditText) findViewById(R.id.GAddPRoductoeditTextNumberSize);
        EditText cantidad=(EditText) findViewById(R.id.GAddPRoductoeditTextNumberCantidad);
        EditText nombreimagen=(EditText) findViewById(R.id.GAddPRoductoeditTextNombreImagen);

        imagen1=(ImageView) findViewById(R.id.imageView2);





        demo();

        b_addP.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nombre.getText().length()==0||descripcion.getText().length()==0||precio.getText().length()==0||size.getText().length()==0||cantidad.getText().length()==0&&imagen1.getDrawable()!=null) {
                    Toast.makeText(AddProductosActivity.this, "hay que rellenar todos los campos ", Toast.LENGTH_SHORT).show();


                }else {
                    Intent i = new Intent(AddProductosActivity.this, GestionProductoActivity.class);
                    Producto p = new Producto(nombre.getText().toString(), Integer.parseInt(String.valueOf(precio.getText())), descripcion.getText().toString(), nombreimagen.getText().toString(), Integer.parseInt(String.valueOf(size.getText())));
                    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference d2=dbRef.push();
                    String key2=d2.getKey();
                    dbRef.child("productos").child(key2).setValue(p.toString());
                    dbRef.child("stockProductos").child(key2).setValue(Integer.parseInt(String.valueOf(cantidad.getText())));
                    String path=nombreimagen.getText().toString();




                        subirImagen(imagen1,path);


                    startActivity(i);

                    finish();
                }
            }
        });



        b_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddProductosActivity.this, GestionProductoActivity.class);
                i.putExtra("capsula",datos);
                startActivity(i);

                finish();
            }
        });
    }
    public void demo() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Selecciona algo"), 10);

        imagen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberImageSelected = 1;
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 10);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==10 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                imagePicked = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            SetImage(imagePicked, numberImageSelected);
        }
    }


    private void subirImagen(ImageView v,String path){

        path+=".jpg";
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference imgRef = storageRef.child(path);


        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) v.getDrawable()).getBitmap();


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imgRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(AddProductosActivity.this,"Fallo subir Imagen",Toast.LENGTH_SHORT).show();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddProductosActivity.this, "IMAGEN SUBIDA", Toast.LENGTH_SHORT).show();
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...

            }
        });

    }


    private void SetImage(Bitmap imageSelected, int ImageNumberSelected){
        if (ImageNumberSelected==1) {

            imagen1.setImageBitmap(imageSelected);
            numberImageSelected = 0;


        }else {
            Toast.makeText(this, "NO SE PUDO COLOCAR LA IMAGEN", Toast.LENGTH_SHORT).show();
            numberImageSelected = 0;
        }
    }
}