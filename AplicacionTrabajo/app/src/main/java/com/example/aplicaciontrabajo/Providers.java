package com.example.aplicaciontrabajo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;

public class Providers extends AppCompatActivity {

    int PETICION_PERSMISOS_CONTACTOS = 0;
    private Button button_consultar;
    private Button button_añadir;
    private Button button_eliminar;
    private Button button_modificar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_providers);

        textView = findViewById(R.id.textView3);
        button_añadir = findViewById(R.id.button_insert);
        button_eliminar = findViewById(R.id.button_eliminar);
        button_consultar = findViewById(R.id.button_consultar);
        button_modificar = findViewById(R.id.button_modificacion);

        //solicitar permisos
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) ||
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_DENIED)){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS},
                    PETICION_PERSMISOS_CONTACTOS);
        }
    }

    //se ocultan los botones de la activity si el usuario no ha otorgado permisos
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PETICION_PERSMISOS_CONTACTOS){
            if((grantResults.length == 2) &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED){

                button_añadir.setVisibility(View.VISIBLE);
                button_consultar.setVisibility(View.VISIBLE);
                button_eliminar.setVisibility(View.VISIBLE);
                button_modificar.setVisibility(View.VISIBLE);

            }
            else{

                button_añadir.setVisibility(View.INVISIBLE);
                button_consultar.setVisibility(View.INVISIBLE);
                button_eliminar.setVisibility(View.INVISIBLE);
                button_modificar.setVisibility(View.INVISIBLE);

            }
        }
    }

    //consulta contactos
    public void click_consultar(View v){
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED) ||
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_DENIED)){
            return;
        }

        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        ContentResolver contentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor c = contentResolver.query(uri,projection,null,null,null);

        if(c.getCount() != 0){
            c.moveToFirst();


            int id;
            String nombre;

            int colID = c.getColumnIndex(ContactsContract.Contacts._ID);
            int colNombre = c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            textView.setText("");

            do{
                id = c.getInt(colID);
                nombre = c.getString(colNombre);


                textView.append(id + " - " + nombre + "\n");
            }while(c.moveToNext());
            c.close();
        }
    }

    //modifica contacto
    public void modificarContactos(View v){
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_DENIED)){
            return;
        }

        ContentResolver cr = getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        ContentValues valores = new ContentValues();
        valores.put(ContactsContract.CommonDataKinds.Nickname.NAME, "Contacto modificado");

        int elementosModificados = cr.update(ContactsContract.Contacts.CONTENT_URI,
                valores,
                "display_name='Contacto nuevo'",
                null);
        Log.i("numero de registros modificados",Integer.toString(elementosModificados));
    }

    //crea contacto (no funciona)
    public void crearContacto(View v){
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_DENIED)){
            return;
        }
        //NOT IMPLEMENTED YET

        /*
        ContentValues valores = new ContentValues();
        valores.put(ContactsContract.CommonDataKinds.Phone.NUMBER, "123456789");
        valores.put(ContactsContract.Contacts.DISPLAY_NAME, "Contacto nuevo");


        ContentResolver cr = getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        Uri nuevoElemento = cr.insert(uri, valores);

        */
    }

    //borra contacto
    public void borrarContacto(View v){
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_DENIED)){
            return;
        }
        ContentResolver cr = getContentResolver();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        int elementosEliminados = cr.delete(uri, "display_name=Contacto modificado", null);
    }

    
}