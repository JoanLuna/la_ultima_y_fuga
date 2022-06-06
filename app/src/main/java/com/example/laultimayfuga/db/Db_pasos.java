package com.example.laultimayfuga.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class Db_pasos extends SQLite_pasos{

    Context context;

    public Db_pasos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarPasos(String nombree, String edadd, String pasoss){

        long id = 0;

        try {
            SQLite_pasos sqlog = new SQLite_pasos(context);
            SQLiteDatabase db = sqlog.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("usuario", nombree);
                values.put("edad", edadd);
                values.put("pasos", pasoss);

                id = db.insert(TABLE_USUARIOS_PASOS, null, values);
        } catch (Exception ex){
            ex.toString();
        }
        return id;
    }


}
