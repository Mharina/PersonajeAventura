package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "Objetos.db"
        private const val TABLA_OBJETOS = "objetos"
        private const val KEY_ID = "_id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_PESO = "peso"
        private const val COLUMN_TIPO = "tipo"
        private const val COLUMN_IMG = "img"
        private const val COLUMN_UNIDADES = "unidad"
        private const val COLUMN_VALOR = "valor"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_OBJETOS ($KEY_ID INTEGER PRIMARY KEY, $COLUMN_NOMBRE TEXT, $COLUMN_PESO INTEGER, " +
                "$COLUMN_TIPO TEXT, $COLUMN_IMG TEXT, $COLUMN_UNIDADES TEXT, $COLUMN_VALOR INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_OBJETOS")
        onCreate(db)
    }
    fun insertarArticulo(articuo: Articulo):Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, articuo.getNombre())
            put(COLUMN_PESO, articuo.getPESO())
            put(COLUMN_TIPO, articuo.getTIPO())
            put(COLUMN_IMG, articuo.getIMG())
            put(COLUMN_UNIDADES, articuo.getUNIDADES())
            put(COLUMN_VALOR, articuo.getVALOR())
        }
        val id= db.insert(TABLA_OBJETOS, null, values)
        db.close()
        return id
    }
    @SuppressLint("Range")
    fun getArticulo(): ArrayList<Personaje> {
        val personajes = ArrayList<Personaje>()
        val selectQuery = "SELECT * FROM $TABLA_OBJETOS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val pesoMochila = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO))
                val estadoVital = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO))
                val clase = cursor.getString(cursor.getColumnIndex(COLUMN_IMG))
                val raza = cursor.getString(cursor.getColumnIndex(COLUMN_VALOR))
                //personajes.add(Personaje(nombre, pesoMochila, estadoVital, clase, raza))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return personajes
    }

}