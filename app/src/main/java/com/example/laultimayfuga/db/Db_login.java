package com.example.laultimayfuga.db;

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;


import androidx.annotation.Nullable;



public class Db_login extends SQLilte_login {

    Context context;

    public Db_login(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarUsuario(String nombre, String edad){

        long id = 0;

        try {
            SQLilte_login sqlog = new SQLilte_login(context);
            SQLiteDatabase db = sqlog.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("usuario", nombre);
            values.put("edad", edad);

            id = db.insert(TABLE_USUARIOS, null, values);
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }

}
