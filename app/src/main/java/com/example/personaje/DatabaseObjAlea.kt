package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE = "OBJETOS_ALEATORIOS.db"
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
    fun insertarArticulo(articulo: Articulo):Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, articulo.getNombre().name)
            put(COLUMN_PESO, articulo.getPeso())
            put(COLUMN_TIPO, articulo.getTipoArticulo().name)
            put(COLUMN_IMG, articulo.getImg())
            put(COLUMN_UNIDADES, articulo.getUnidades())
            put(COLUMN_VALOR, articulo.getValor())
        }
        val id= db.insert(TABLA_OBJETOS, null, values)
        db.close()
        return id
    }
    @SuppressLint("Range")
    fun getArticulo(): ArrayList<Articulo> {
        val articulos = ArrayList<Articulo>()
        val selectQuery = "SELECT * FROM $TABLA_OBJETOS"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                val nombre = cursor.getString(cursor.getColumnIndex(COLUMN_NOMBRE))
                val peso = cursor.getInt(cursor.getColumnIndex(COLUMN_PESO))
                val tipo = cursor.getString(cursor.getColumnIndex(COLUMN_TIPO))
                val img = cursor.getString(cursor.getColumnIndex(COLUMN_IMG))
                val unidades = cursor.getInt(cursor.getColumnIndex(COLUMN_UNIDADES))
                val valor = cursor.getInt(cursor.getColumnIndex(COLUMN_VALOR))
                val enum = enumArt()
                articulos.add(Articulo(id,enum.enuT(tipo), enum.enuN(nombre), peso, img, unidades, valor))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return articulos
    }
    fun recreaTabla(){
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseHelper.TABLA_OBJETOS}")
        onCreate(db)

        val arrayArticulos = ArrayList<Articulo>()

        arrayArticulos.add(Articulo(1,Articulo.TipoArticulo.ARMA,Articulo.Nombre.DAGA,3,"daga",1,15))
        arrayArticulos.add(Articulo(2,Articulo.TipoArticulo.ARMA,Articulo.Nombre.BASTON,8,"baston",1,15))
        arrayArticulos.add(Articulo(3,Articulo.TipoArticulo.ARMA,Articulo.Nombre.ESPADA,5,"espada",1,15))
        arrayArticulos.add(Articulo(10,Articulo.TipoArticulo.ARMA,Articulo.Nombre.HACHA,6,"hacha",1,15))
        arrayArticulos.add(Articulo(4,Articulo.TipoArticulo.ARMA,Articulo.Nombre.GARRAS,4,"garras",1,15))
        arrayArticulos.add(Articulo(5,Articulo.TipoArticulo.ARMA,Articulo.Nombre.MARTILLO,7,"martillo",1,15))
        arrayArticulos.add(Articulo(6,Articulo.TipoArticulo.PROTECCION,Articulo.Nombre.ESCUDO,5,"escudo",1,15))
        arrayArticulos.add(Articulo(7,Articulo.TipoArticulo.PROTECCION,Articulo.Nombre.ARMADURA,9,"armadura",1,15))
        arrayArticulos.add(Articulo(8,Articulo.TipoArticulo.OBJETO,Articulo.Nombre.IRA,1,"ira",1,30))
        arrayArticulos.add(Articulo(9,Articulo.TipoArticulo.OBJETO,Articulo.Nombre.POCION,1,"pocion",1,15))
        arrayArticulos.add(Articulo(11,Articulo.TipoArticulo.ORO,Articulo.Nombre.MONEDA,0,"moneda",1,15))
        for (i in 0..10){
            insertarArticulo(arrayArticulos[i])
        }
    }
}