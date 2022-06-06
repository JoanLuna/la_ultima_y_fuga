package com.example.laultimayfuga;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

 class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DB_FILENAME = "Login.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_USERS = "usuarios";
    public static final String COLUMN_USER = "usuario";
    public static final String COLUMN_PASS = "contraseña";
    public SQLiteHelper (@Nullable Context context) {
        super(context, DB_FILENAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USERS + " ( " + COLUMN_USER + " TEXT PRIMARY KEY, " + COLUMN_PASS + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    public boolean insertUser(String usuario, String contraseña) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER, usuario);
        contentValues.put(COLUMN_PASS, contraseña);
        long result = sqLiteDatabase.insert(TABLE_USERS, null, contentValues);
        return result != -1;
    }

    public boolean checkUsername(String usuario) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER + " = ?", new String[]{usuario});
        return cursor.getCount() > 0;
    }

    public boolean checkUsernamePass(String usuario, String contraseña) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER + " = ? AND " + COLUMN_PASS + " = ?", new String[]{usuario,contraseña});
        return cursor.getCount() > 0;
    }
}

