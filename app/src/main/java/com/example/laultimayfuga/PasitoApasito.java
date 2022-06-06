package com.example.laultimayfuga;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.biometrics.BiometricManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.laultimayfuga.db.Db_pasos;

public class PasitoApasito extends AppCompatActivity implements SensorEventListener {

    TextView nombre, edad, pasos;
    Button registrar;
    SensorManager sensorManager;
    Sensor mStepCounter;
    boolean isCounterSensorPresent,running=false;
    int stepCount = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_pasito_apasito);
        nombre = (TextView) findViewById(R.id.Texto_nombre);
        edad = (TextView) findViewById(R.id.Texto_edad);
        pasos = (TextView) findViewById(R.id.Texto_pasos);
        registrar = (Button) findViewById(R.id.boton_registrp);

        if (ContextCompat.checkSelfPermission(PasitoApasito.this, Manifest.permission.ACTIVITY_RECOGNITION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
        }
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        cargar_preferencias();
        //String nd = getIntent().getStringExtra("dato_nombre");
        //nombre.setText(nd);
        //String ed = getIntent().getStringExtra("dato_edad");
        //edad.setText(ed);


        if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
            mStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isCounterSensorPresent = true;
        }else{
            pasos.setText("NO HAY SENSOR DE CONTADOR DE PASOS");
            isCounterSensorPresent = false;
        }


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Db_pasos dbp = new Db_pasos(PasitoApasito.this);
                long id = dbp.insertarPasos(nombre.getText().toString(), edad.getText().toString(), pasos.getText().toString());

                if (id > 0){
                    Toast.makeText(PasitoApasito.this, "REGISTRO CORRECTO", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PasitoApasito.this,MainActivity.class);
                    stepCount = 0;


                    startActivity(intent);
                }else{
                    Toast.makeText(PasitoApasito.this, "ERROR REGISTRO", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor == mStepCounter){
           stepCount = (int) sensorEvent.values[0];
           pasos.setText(Integer.toString(stepCount));
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null){
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);

        }else{
            Toast.makeText(this,"NO HAY SENSOR",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        running = false;
        //if(sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!=null){
        //    sensorManager.unregisterListener(this, mStepCounter);
        //}
    }
    private void cargar_preferencias(){
        SharedPreferences preferences =  getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String user = preferences.getString("user","NOT TODAY");
        String pass = preferences.getString("pass","NOT TODAY");

        nombre.setText(user);
        edad.setText(pass);
    }
}