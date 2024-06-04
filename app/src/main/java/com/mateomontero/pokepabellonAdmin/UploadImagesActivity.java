package com.mateomontero.pokepabellonAdmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mateomontero.pokepabellon.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class UploadImagesActivity extends AppCompatActivity {


    ArrayList<ImageView> listaImagenes;
    String paths[];
    private  ImageView imagen1,imagen2,imagen3,imagen4,imagen5,imagen6,imagen7,imagen8;

    Button buttonAceptarSubirImg,buttonCancelar;

    private Bitmap imagePicked;
    private int numberImageSelected = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);

        //bloqueo pantalla vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);





        imagen1=(ImageView) findViewById(R.id.imagen1);

        buttonAceptarSubirImg = (Button) findViewById(R.id.buttonAceptarAlSubirImagenes);
        buttonCancelar = (Button) findViewById(R.id.buttonCancelAlSubirImagenes);

        EditText nombreImagen=(EditText) findViewById(R.id.editTextImagenNombrar);

        demo();

        listaImagenes = new ArrayList<ImageView>();

        buttonAceptarSubirImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> imPath =new ArrayList<String>();
                String path=nombreImagen.getText().toString();

                if (imagen1.getDrawable()!=null){
                    imPath.add(path);
                    subirImagen(imagen1,path);
                }



                Intent intent = new Intent(UploadImagesActivity.this,MainAdminActivity.class);

                startActivity(intent);
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UploadImagesActivity.this,MainAdminActivity.class);

                startActivity(intent);
                finish();
            }
        });
    }





    public void demo(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent,"Selecciona algo"),10);

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
        //    paths[numberImageSelected-1]=imageUri.getLastPathSegment();
           SetImage(imagePicked, 1);
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
                Toast.makeText(UploadImagesActivity.this,"Fallo subir Imagen",Toast.LENGTH_SHORT).show();
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UploadImagesActivity.this, "IMAGEN SUBIDA", Toast.LENGTH_SHORT).show();
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

