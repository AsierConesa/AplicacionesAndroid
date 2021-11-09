package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase bbdd;

    //se inicializan las views
    EditText editTextCodigo, editTextPersonName, editTextEdad, editTextRowId;
    TextView textViewSalida;

    bbddEmpresa conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // se guaardan las views en nuestras variables locales
        editTextCodigo = findViewById(R.id.editTextCodigo);
        editTextPersonName = findViewById(R.id.editTextPersonName);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextRowId = findViewById(R.id.editTextRowId);
        textViewSalida = findViewById(R.id.textViewSalida);

        conexion = new bbddEmpresa(this, "bbddEmpresa", null, 1);

        bbdd = conexion.getWritableDatabase();
    }
    //metodo para el boton "insertar"
    public void insertarRegistro(View view){

        //coger los datos insertados por el usuario
        int codigo = Integer.parseInt(editTextCodigo.getText().toString());
        String nombrePersona = editTextPersonName.getText().toString();
        int edad = Integer.parseInt(editTextEdad.getText().toString());

        //SE INSERTAN ESOS DATOS EN LA BBDD
        if(bbdd!=null){

            String sql = "INSERT INTO EMPLEADOS (CODIGO, NOMBRE, EDAD) VALUES (?,?,?)";

            SQLiteStatement statement = bbdd.compileStatement(sql);

            statement.clearBindings();

            statement.bindLong(1, codigo);
            statement.bindString(2, nombrePersona);
            statement.bindLong(3, edad);

            long rowId = statement.executeInsert();

            editTextRowId.setText(String.valueOf(rowId));
        }
    }

    //metodo para el boton eliminar
    public void eliminarRegistro (View view){

        int codigo = Integer.parseInt(editTextCodigo.getText().toString());

        //SE BORRAN ESOS DATOS EN LA BBDD
        if((bbdd!=null) && (!editTextCodigo.getText().toString().isEmpty())){

            String sql = "DELETE FROM EMPLEADOS WHERE CODIGO = ?";

            SQLiteStatement statement = bbdd.compileStatement(sql);

            statement.clearBindings();

            statement.bindLong(1, codigo);

            statement.executeUpdateDelete();
        }

    }

    //Metodo para actualizar registros
    public void actualizarRegistro (View view){

        int codigo = Integer.parseInt(editTextCodigo.getText().toString());
        String nombrePersona = editTextPersonName.getText().toString();
        int edad = Integer.parseInt(editTextEdad.getText().toString());

        //SE ACTUALIZAN ESOS DATOS EN LA BBDD
        if(bbdd!=null){

            String sql = "UPDATE EMPLEADOS SET NOMBRE = ?, EDAD = ? WHERE CODIGO = ?";

            SQLiteStatement statement = bbdd.compileStatement(sql);

            statement.clearBindings();

            statement.bindString(1, nombrePersona);
            statement.bindLong(2, edad);
            statement.bindLong(3, codigo);

            statement.executeUpdateDelete();
        }

    }

    //metodo para el boton "cargar"

    public void consultarRegistro(View view){
        String[] camposMostrar = new String[]{"codigo","nombre","edad","rowid"};
        String[] valoresWhere = new String[]{editTextCodigo.getText().toString()};

        if(bbdd != null){
            Cursor c1 = bbdd.query("Empleados",camposMostrar, "codigo=?", valoresWhere, null, null, null);

            if(c1.moveToFirst()){
                do{
                    editTextCodigo.setText(String.valueOf(c1.getInt(0)));
                    editTextPersonName.setText(c1.getString(1));
                    editTextEdad.setText(String.valueOf(c1.getInt(2)));
                    editTextRowId.setText(String.valueOf(c1.getInt(3)));
                }while(c1.moveToNext());
            }
        }

    }

    public void consultarTodosRegistros(View view){
        String[] camposMostrar = new String[]{"codigo","nombre","edad","rowid"};

        textViewSalida.setText("");

        if(bbdd != null){
            Cursor c1 = bbdd.query("Empleados",camposMostrar, null, null, null, null, null);

            if(c1.moveToFirst()){
                do{
                    //aqui habria que meter los datos en un list para usarlos en el recyclerView
                    textViewSalida.append(c1.getInt(0)+" "+c1.getString(1)+" "+c1.getInt(2)+"\n");
                }while(c1.moveToNext());
            }
        }

    }

    public void consultarRegistroPorRowId(View view){
        String[] camposMostrar = new String[]{"codigo","nombre","edad","rowid"};
        String[] valoresWhere = new String[]{editTextRowId.getText().toString()};

        if(bbdd != null){
            Cursor c1 = bbdd.query("Empleados",camposMostrar, "rowid=?", valoresWhere, null, null, null);

            if(c1.moveToFirst()){
                do{
                    editTextCodigo.setText(String.valueOf(c1.getInt(c1.getColumnIndex("codigo"))));
                    editTextPersonName.setText(c1.getString(c1.getColumnIndex("nombre")));
                    editTextEdad.setText(String.valueOf(c1.getInt(c1.getColumnIndex("edad"))));
                    editTextRowId.setText(String.valueOf(c1.getInt(c1.getColumnIndex("rowid"))));
                }while(c1.moveToNext());
            }
        }

    }

    //metodo para el boton "cerrar"
    public void cerrarBBDD (View view){
        bbdd.close();
    }
}