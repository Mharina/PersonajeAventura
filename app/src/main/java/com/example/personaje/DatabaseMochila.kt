package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseMochila(context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "OBJETOS_MOCHILA.db"
        private const val TABLA_MOCHILA = "mochila"
        private const val COLUMN_UID = "uid"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_UNIDADES = "uni"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_MOCHILA ($COLUMN_UID TEXT, $COLUMN_NOMBRE TEXT, $COLUMN_UNIDADES INTEGER, PRIMARY KEY (UID_P, COLUMN_NOMBRE))"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_MOCHILA")
        onCreate(db)
    }

    fun insertarContenido(mochila: Mochila, uid:String) {
        val db = this.writableDatabase
        val c = mochila.getContenido()
        for (i in 0..c.size){
            val values = ContentValues().apply {
                put(COLUMN_UID, uid)
                put(COLUMN_NOMBRE, c[i].getNombre().name)
                put(COLUMN_UNIDADES, c[i].getUnidades())
            }
        }
        db.close()
    }
    @SuppressLint("Range")
    fun getContenidoMochila(uid: String, pj: Personaje){
        val selectQuery = "SELECT * FROM $TABLA_MOCHILA WHERE $COLUMN_UID=$uid"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val unidades = cursor.getInt(cursor.getColumnIndex(COLUMN_UNIDADES))
                val arti = FormarArticulo()
                pj.getMochila().addArticulo(arti.fArti(nombre,unidades),unidades)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
    }
}