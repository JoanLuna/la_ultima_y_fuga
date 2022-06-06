package com.example.laultimayfuga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.laultimayfuga.db.Db_login;


public class MainActivity extends AppCompatActivity {

    EditText edit_nombre, edit_pass;
    Button bot_register;

    String nombrex, edadx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_nombre = (EditText) findViewById(R.id.editTextTextPersonName);
        edit_pass = (EditText) findViewById(R.id.editTextTextEdad);
        bot_register = (Button) findViewById(R.id.boton_register);

        bot_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Db_login db_login = new Db_login(MainActivity.this);
                long id = db_login.insertarUsuario(edit_nombre.getText().toString(), edit_pass.getText().toString());

                if (id > 0) {
                    Toast.makeText(MainActivity.this, "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
                    guardar_preferencias();
                    nombrex = edit_nombre.getText().toString();
                    edadx = edit_pass.getText().toString();
                    Intent intent = new Intent(MainActivity.this, PasitoApasito.class);
                    intent.putExtra("dato_nombre", nombrex);
                    intent.putExtra("dato_edad", edadx);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "ERROR EN EL REGISTRO", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    private void guardar_preferencias(){

        SharedPreferences preferences =  getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String usuario = edit_nombre.getText().toString();
        String pass = edit_pass.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user",usuario);
        editor.putString("pass", pass);
        editor.commit();
    }
}