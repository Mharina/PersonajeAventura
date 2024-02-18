package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date

class DatabasePelea (context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION){

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "REGISTRO_PELEA.db"
        private const val TABLA_PELEA = "peleas"
        private const val KEY_ID = "_id"
        private const val COLUMN_UID_PERSONAJE = "uid"
        private const val COLUMN_NOM_MONSTRUO = "id_mon"
        private const val COLUMN_RESULTADO = "nombre"
        private const val COLUMN_FECHA = "fecha"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_PELEA ($KEY_ID INTEGER PRIMARY KEY, $COLUMN_UID_PERSONAJE TEXT, $COLUMN_NOM_MONSTRUO TEXT, $COLUMN_RESULTADO TEXT, $COLUMN_FECHA DATE)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PELEA")
        onCreate(db)
    }

    fun insertarPelea(uid:String, nomMonstruo:String, res:String, fecha:Date):Long  {
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_UID_PERSONAJE, uid)
            put(COLUMN_NOM_MONSTRUO,nomMonstruo)
            put(COLUMN_RESULTADO, res)
            put(COLUMN_FECHA, formato.format(fecha))
        }
        val id= db.insert(TABLA_PELEA, null, values)
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun mostrarPeleas(uid:String):String{
        val selectQuery = "SELECT * FROM $TABLA_PELEA WHERE $COLUMN_UID_PERSONAJE=$uid"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val nom_mons = cursor.getString(cursor.getColumnIndex(COLUMN_NOM_MONSTRUO))
                val resul = cursor.getString(cursor.getColumnIndex(COLUMN_RESULTADO))
                val fecha = cursor.getString(cursor.getColumnIndex(COLUMN_FECHA))
                val tm=TiposMonstruos()
                return "El día $fecha has luchado contra el monstruo ${tm.infoMonstruo(nom_mons).getNombre()} nivel ${tm.infoMonstruo(nom_mons).getNivel()} y has $resul."
            }while (cursor.moveToNext())
        }else {
            return ""
        }
        cursor.close()
        db.close()

    }
}