package com.example.proveedorcontenido;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    int PETICION_PERMISOS_LLAMADAS = 0;

    private Button buttonAñadirLlamada;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAñadirLlamada = (Button) findViewById(R.id.buttonAñadirLlamada);
        textView = (TextView) findViewById(R.id.textView);

        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_DENIED) ||
           (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_DENIED) ||
           (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED)){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_CALL_LOG, Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_CONTACTS},
                    PETICION_PERMISOS_LLAMADAS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PETICION_PERMISOS_LLAMADAS){
            if((grantResults.length == 3) &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED &&
            grantResults[1] == PackageManager.PERMISSION_GRANTED &&
            grantResults[2] == PackageManager.PERMISSION_GRANTED){

                buttonAñadirLlamada.setVisibility(View.VISIBLE);

            }
            else{

                buttonAñadirLlamada.setVisibility(View.INVISIBLE);

            }
        }
    }

    public void escribirFilaEjemplo(View arg0){
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_DENIED)){
            return;
        }

        ContentValues valores = new ContentValues();
        valores.put(CallLog.Calls.DATE, new Date().getTime());
        valores.put(CallLog.Calls.NUMBER, "123456789");
        valores.put(CallLog.Calls.DURATION, 65);
        valores.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);

        ContentResolver cr = getContentResolver();
        Uri llamadasUri = CallLog.Calls.CONTENT_URI;

        Uri nuevoElemento = cr.insert(llamadasUri, valores);
    }
    public void eliminarFilasEjemplo(View arg0){
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_DENIED)){
            return;
        }
        ContentResolver cr = getContentResolver();
        Uri llamadasUri = CallLog.Calls.CONTENT_URI;

        int elementosEliminados = cr.delete(CallLog.Calls.CONTENT_URI, "number=123456789", null);
    }
    public void modificarFilasEjemplo(View view){
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_DENIED)){
            return;
        }

        ContentResolver cr = getContentResolver();
        Uri llamadasUri = CallLog.Calls.CONTENT_URI;

        ContentValues valores = new ContentValues();
        valores.put(CallLog.Calls.NUMBER, "112");

        int elementosModificados = cr.update(CallLog.Calls.CONTENT_URI,
                valores,
                "number=123456789",
                null);
    }

    public void llamadas (View view){

        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_DENIED) ||
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED)){
            return;
        }

        String[] projection = new String[]{
                CallLog.Calls._ID,
                CallLog.Calls.TYPE,
                CallLog.Calls.NUMBER};

        Uri llamadasUri = CallLog.Calls.CONTENT_URI;

        Cursor cur;

        ContentResolver cr = getContentResolver();

        textView.setText("");

        cur = cr.query(llamadasUri, projection, null, null, null);

        if(cur.getCount() != 0){
            cur.moveToFirst();

            int tipo;
            int id;
            String tipollamada = "";
            String telefono;

            int colID = cur.getColumnIndex(CallLog.Calls._ID);
            int colTipo = cur.getColumnIndex(CallLog.Calls.TYPE);
            int colTelefono = cur.getColumnIndex(CallLog.Calls.NUMBER);

            do{
                id = cur.getInt(colID);
                tipo = cur.getInt(colTipo);
                telefono = cur.getString(colTelefono);

                if(tipo == CallLog.Calls.INCOMING_TYPE) tipollamada = "Entrada";
                else if (tipo == CallLog.Calls.OUTGOING_TYPE) tipollamada = "Salida";
                else if (tipo == CallLog.Calls.MISSED_TYPE) tipollamada = "Perdida";

                textView.append(id + " - " + tipollamada + " + " + telefono + "\n");
            }while(cur.moveToNext());
            cur.close();
        }
    }
    public void modificarFila(View view){
        if((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_DENIED)){
            return;
        }

        EditText editTextNumber = findViewById(R.id.editTextNumber);

        ContentValues valores = new ContentValues();
        valores.put(CallLog.Calls.DATE, new Date().getTime());
        valores.put(CallLog.Calls.NUMBER, "111111111");
        valores.put(CallLog.Calls.DURATION, "11");
        valores.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);

        ContentResolver cr = getContentResolver();

        cr.update(CallLog.Calls.CONTENT_URI,
                valores,
                CallLog.Calls._ID + "=?",
                new String[]{editTextNumber.getText().toString()});
    }
    public void eliminarFila(View arg0) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALL_LOG) == PackageManager.PERMISSION_DENIED) {
            return;
        }

        EditText editTextNumber = findViewById(R.id.editTextNumber);

        ContentResolver cr = getContentResolver();

        cr.delete(CallLog.Calls.CONTENT_URI, CallLog.Calls._ID + " = ? ", new String[]{editTextNumber.getText().toString()});

    }
}