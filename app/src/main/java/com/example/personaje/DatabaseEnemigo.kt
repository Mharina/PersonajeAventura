package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseEnemigo (context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "OBJETOS_MERCADER.db"
        private const val TABLA_MONSTRUOS = "objetos"
        private const val KEY_ID = "_id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_NIVEL = "nivel"
        private const val COLUMN_SALUD = "salud"
        private const val COLUMN_ATAQUE = "ataque"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable =
            "CREATE TABLE $TABLA_MONSTRUOS ($KEY_ID INTEGER PRIMARY KEY, $COLUMN_NOMBRE TEXT, $COLUMN_NIVEL INTEGER, " +
                    "$COLUMN_SALUD INTEGER, $COLUMN_ATAQUE INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_MONSTRUOS")
        onCreate(db)
    }

    fun insertarMonstruo(monstruo: Monstruo): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, monstruo.getNombre())
            put(COLUMN_NIVEL, monstruo.getNivel().toString())
            put(COLUMN_SALUD, monstruo.getSalud().toString())
            put(COLUMN_ATAQUE, monstruo.getAtaque().toString())
        }
        val id = db.insert(TABLA_MONSTRUOS, null, values)
        db.close()
        return id
    }
    @SuppressLint("Range")
    fun getMonstruo(): ArrayList<Monstruo> {
        val monstruos = ArrayList<Monstruo>()
        val selectQuery = "SELECT * FROM ${TABLA_MONSTRUOS}"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val nivel = cursor.getInt(cursor.getColumnIndex(COLUMN_NIVEL))
                monstruos.add(Monstruo(nombre, nivel))} while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return monstruos
    }
    fun recreaTabla(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS $TABLA_MONSTRUOS")
        onCreate(db)

        val arrayMonstruo = ArrayList<Monstruo>()
        arrayMonstruo.add(Monstruo("Estrige",1))
        arrayMonstruo.add(Monstruo("Lamia",2))
        arrayMonstruo.add(Monstruo("Kikimora",3))
        arrayMonstruo.add(Monstruo("Sirena",4))
        arrayMonstruo.add(Monstruo("Ghuls",5))
        arrayMonstruo.add(Monstruo("Grifo",6))
        arrayMonstruo.add(Monstruo("Djin",7))
        arrayMonstruo.add(Monstruo("Basilisco",8))
        arrayMonstruo.add(Monstruo("Miriápodo",9))
        arrayMonstruo.add(Monstruo("Dragón",10))
        for (i in 0..9){
            insertarMonstruo(arrayMonstruo[i])
        }
    }
}