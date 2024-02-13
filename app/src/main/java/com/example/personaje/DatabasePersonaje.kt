package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabasePersonaje(context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "Personajes.db"
        private const val TABLA_PERSONAJE = "personaje"
        private const val KEY_ID = "_id"
        private const val UID = "uid"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_ESTADOVITAL = "estadoVital"
        private const val COLUMN_RAZA = "raza"
        private const val COLUMN_CLASE = "clase"
        private const val COLUMN_SALUD = "salud"
        private const val COLUMN_NIVEL = "nivel"
        private const val COLUMN_EXPERIENCIA = "expe"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_PERSONAJE ($KEY_ID INTEGER PRIMARY KEY, $UID TEXT, $COLUMN_NOMBRE TEXT, $COLUMN_ESTADOVITAL TEXT, $COLUMN_RAZA TEXT, $COLUMN_CLASE TEXT, $COLUMN_SALUD INTEGER, $COLUMN_NIVEL INTEGER, $COLUMN_EXPERIENCIA INTEGER)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PERSONAJE")
        onCreate(db)
    }

    fun insertarPersonaje(personaje: Personaje, uid:String):Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(UID, uid)
            put(COLUMN_NOMBRE, personaje.getNombre())
            put(COLUMN_ESTADOVITAL, personaje.getEstadoVital())
            put(COLUMN_RAZA, personaje.getRaza())
            put(COLUMN_CLASE, personaje.getClase())
            put(COLUMN_SALUD, personaje.getSalud())
            put(COLUMN_NIVEL, personaje.getNivel())
            put(COLUMN_EXPERIENCIA, personaje.getExperiencia())
        }
        val id= db.insert(TABLA_PERSONAJE, null, values)
        db.close()
        return id
    }


    @SuppressLint("Range")
    fun getPersonaje(uid: String): Personaje? {
        var personaje:Personaje?=null
        val selectQuery = "SELECT * FROM $TABLA_PERSONAJE WHERE $UID=$uid"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
            val estadoVital = cursor.getString(cursor.getColumnIndex(COLUMN_ESTADOVITAL))
            val clase = cursor.getString(cursor.getColumnIndex(COLUMN_CLASE))
            val raza = cursor.getString(cursor.getColumnIndex(COLUMN_RAZA))
            val salud = cursor.getInt(cursor.getColumnIndex(COLUMN_SALUD))
            val nivel = cursor.getInt(cursor.getColumnIndex(COLUMN_NIVEL))
            val experiencia = cursor.getInt(cursor.getColumnIndex(COLUMN_EXPERIENCIA))
            personaje = Personaje(nombre, raza, clase, estadoVital)
            personaje.setNivel(nivel)
            personaje.setExperiencia(experiencia)
            personaje.setSalud(salud)
        }
        cursor.close()
        db.close()
        return personaje
    }
}