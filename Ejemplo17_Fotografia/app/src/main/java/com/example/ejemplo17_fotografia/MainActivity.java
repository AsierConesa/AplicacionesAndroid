package com.example.ejemplo17_fotografia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private int permissionCheck, permissionCheck2, permissionCheck3;

    private final static int PETICION_PERMISOS = 0;

    //variables para comprobar el activity result de cada operación de la camara
    private final static int CAPTURA_IMAGEN_THUMBINAL = 1;
    private final static int CAPTURA_IMAGEN_GUARDAR_GALERIA = 2;
    private final static int CAPTURA_IMAGEN_GUARDAR_GALERIA_REDIMENSIONADA = 3;
    private final static int CARGAR_IMAGEN_GALERIA = 4;

    //Views
    private Button btn_guardar_en_galeria, btn_redimensionar, btn_seleccionar_de_galeria, btn_img;
    private ImageView imageView;
    private String fotopath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findviewbyid de las views
        btn_guardar_en_galeria = findViewById(R.id.btn_guardar_en_galeria);
        btn_redimensionar = findViewById(R.id.btn_redi);
        btn_seleccionar_de_galeria = findViewById(R.id.btn_seleccionar_de_galeria);
        btn_img = findViewById(R.id.btn_img);

        imageView = findViewById(R.id.imgView);

        //onclick de seleccionar de la galería
        btn_seleccionar_de_galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });



        //onclick de guardar en la galería
        btn_guardar_en_galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                    tomarCapturaIntent(CAPTURA_IMAGEN_GUARDAR_GALERIA);
                }
            }
        });
    }

    //metodo para mostrar la imagen de la galería
    private void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent, CARGAR_IMAGEN_GALERIA);
    }

    public void hacerFotoThumbinal(View view){
        permissionCheck = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionCheck2 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionCheck3 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED &&
            permissionCheck2 == PackageManager.PERMISSION_GRANTED &&
            permissionCheck3 == PackageManager.PERMISSION_GRANTED){

            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                Intent hacerFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(hacerFotoIntent, CAPTURA_IMAGEN_THUMBINAL);
            }

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, PETICION_PERMISOS);
        }
    }

    public void tomarCapturaIntent(int code){
        permissionCheck = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permissionCheck2 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        permissionCheck3 = ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);

        if(permissionCheck == PackageManager.PERMISSION_GRANTED &&
                permissionCheck2 == PackageManager.PERMISSION_GRANTED &&
                permissionCheck3 == PackageManager.PERMISSION_GRANTED){

            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
                Intent hacerFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (hacerFotoIntent.resolveActivity(getPackageManager()) != null){
                    File photoFile = createImageFile();
                    if (photoFile != null){
                        fotopath = photoFile.getPath();

                        Uri photoURI = FileProvider.getUriForFile(this, "com.example.ejemplo17_fotografia", photoFile);
                        hacerFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(hacerFotoIntent, code);
                    }
                }

                startActivityForResult(hacerFotoIntent, CAPTURA_IMAGEN_THUMBINAL);
            }

        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, PETICION_PERMISOS);
        }
    }

    private void galeryAddPid(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(fotopath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private File createImageFile(){
        File image = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPG_"+ timeStamp + "_";

        File storageDir = null;
        storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)+"/CamaraClase");

        if(!storageDir.exists()){
            storageDir.mkdirs();
        }

        try {
            image = File.createTempFile(
                    imageFileName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException ex){

        }

        return image;
    }

    //listener de los eventos de cada botón
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch(requestCode){
                case CARGAR_IMAGEN_GALERIA:
                    Uri path = data.getData();
                    imageView.setImageURI(path);
                    break;
                case CAPTURA_IMAGEN_THUMBINAL:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);
                    break;
                case CAPTURA_IMAGEN_GUARDAR_GALERIA:
                    galeryAddPid();
                    imageView.setImageURI(Uri.fromFile(new File(fotopath)));
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PETICION_PERMISOS:
                if (grantResults.length == 3 &&
                        grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                        grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                        grantResults[2] != PackageManager.PERMISSION_GRANTED) {

                    //Desactivar Botones
                }
                break;
        }
    }
}