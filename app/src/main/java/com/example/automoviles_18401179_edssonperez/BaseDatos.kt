package com.example.automoviles_18401179_edssonperez

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BaseDatos(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE AUTO(IDAUTO INTEGER PRIMARY KEY AUTOINCREMENT," +
                "MODELO VARCHAR(50), MARCA VARCHAR(50),KILOMETRAJE INTEGER)")
        db.execSQL("CREATE TABLE ARRENDAMIENTO (IDA INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NOMBRE VARCHAR(50), DOMICILIO VARCHAR(50),LICENCIA VARCHAR(50), IDAUTO INTEGER, FECHA DATE," +
                "FOREIGN KEY(IDAUTO) REFERENCES AUTO(IDAUTO))")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}