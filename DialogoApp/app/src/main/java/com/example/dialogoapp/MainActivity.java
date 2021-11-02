package com.example.dialogoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {

    TextView editTextDepartamento, incidenciaSeleccionada, resultadoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextDepartamento = findViewById(R.id.editTextDepartamento);
        incidenciaSeleccionada = findViewById(R.id.incidenciaSeleccionada);
        resultadoTextView = findViewById(R.id.resultadoTextView);

        registerForContextMenu(editTextDepartamento);
    }

    public void enviarAceptar(View view){
        AlertDialog dialogoAlerta = new MaterialAlertDialogBuilder(this)
                .setMessage("Se ha enviado el mensaje")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    public void enviarAceptarCancelar(View view){
        AlertDialog dialogoAlerta = new MaterialAlertDialogBuilder(this, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle("¿Desea realizar el envío?")
                .setMessage("Una vez enviado no se podrá cancelar la incidencia")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resultadoTextView.setVisibility(View.VISIBLE);
                        resultadoTextView.setText("Operacion aceptada");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resultadoTextView.setVisibility(View.VISIBLE);
                        resultadoTextView.setText("Operacion NO aceptada");
                    }
                })
                .setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }
    public void seleccionarVariasOpciones(View view){
        Resources res = getResources();

        String departamentos[];
        departamentos = res.getStringArray(R.array.departamentos);

        AlertDialog dialogoAlerta = new MaterialAlertDialogBuilder(this, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle("Seleccione el departamento")
                .setItems(R.array.departamentos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editTextDepartamento.setText(departamentos[which]);
                    }
                })
                .show();
    }

    public void seleccionarVariasOpcionesConUnaMarcada(View view){
        Resources res = getResources();

        String tiposIncidencias[];
        tiposIncidencias = res.getStringArray(R.array.tiposIncidencias);

        AlertDialog dialogoAlerta = new MaterialAlertDialogBuilder(this, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle("Seleccione la incidencia")
                .setSingleChoiceItems(R.array.tiposIncidencias, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        incidenciaSeleccionada.setText(tiposIncidencias[which]);
                        dialog.cancel();
                    }
                })
                .show();
    }
    public void seleccionarVariasOpcionesMarcadas(View view){
        Resources res = getResources();

        String tiposIncidencias[];
        tiposIncidencias = res.getStringArray(R.array.tiposIncidencias);

        AlertDialog dialogoAlerta = new MaterialAlertDialogBuilder(this, R.style.Body_ThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle("Seleccione los tipos de incidencias")
                .setMultiChoiceItems(R.array.tiposIncidencias, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Log.i("Dialogos", "Opción elegida: "+tiposIncidencias[which] + " " + isChecked);
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ctx_etiqueta, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        resultadoTextView.setVisibility(View.VISIBLE);
        switch (item.getItemId()) {
            case R.id.CtxLblOpc1:
                resultadoTextView.setText("Etiqueta: Opcion 1 pulsada!");
                return true;
            case R.id.CtxLblOpc2:
                resultadoTextView.setText("Etiqueta: Opcion 2 pulsada!");
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

}