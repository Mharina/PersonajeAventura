package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabasePelea (context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "REGISTRO_PELEA.db"
        private const val TABLA_PELEA = "peleas"
        private const val KEY_ID = "_id"
        private const val COLUMN_UID_PERSONAJE = "uid"
        private const val COLUMN_NOM_MONSTRUO = "id_mon"
        private const val COLUMN_RESULTADO = "nombre"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_PELEA ($KEY_ID INTEGER PRIMARY KEY, $COLUMN_UID_PERSONAJE TEXT, $COLUMN_NOM_MONSTRUO TEXT, $COLUMN_RESULTADO TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PELEA")
        onCreate(db)
    }

    fun insertarPelea(uid:String, nomMonstruo:String, res:String):Long  {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_UID_PERSONAJE, uid)
            put(COLUMN_NOM_MONSTRUO,nomMonstruo)
            put(COLUMN_RESULTADO, res)
        }
        val id= db.insert(TABLA_PELEA, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun mostrarPeleas(uid:String){
        val selectQuery = "SELECT * FROM $TABLA_PELEA WHERE $COLUMN_UID_PERSONAJE=$uid"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val nom_mons = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_MONSTRUO))
                val resul = cursor.getString(cursor.getColumnIndex(COLUMN_RESULTADO))
                val tm=TiposMonstruos()
                println("Has luchado contra el monstruo ${tm.infoMonstruo(nom_mons).getNombre()} nivel ${tm.infoMonstruo(nom_mons).getNivel()} y has $resul.")
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
    }

}