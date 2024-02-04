package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper2(context: Context) : SQLiteOpenHelper(context, DATABASE, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE = "OBJETOS_MERCADER.db"
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

        val arrayArticulosC = ArrayList<Articulo>()
        arrayArticulosC.add(Articulo(1,Articulo.TipoArticulo.ARMA,Articulo.Nombre.DAGA,3,"daga",1,4))
        arrayArticulosC.add(Articulo(2,Articulo.TipoArticulo.ARMA,Articulo.Nombre.BASTON,4,"baston",1,3))
        arrayArticulosC.add(Articulo(3,Articulo.TipoArticulo.ARMA,Articulo.Nombre.ESPADA,3,"espada",2,4))
        arrayArticulosC.add(Articulo(10,Articulo.TipoArticulo.ARMA,Articulo.Nombre.HACHA,3,"hacha",3,4))
        arrayArticulosC.add(Articulo(4,Articulo.TipoArticulo.ARMA,Articulo.Nombre.GARRAS,3,"garras",1,4))
        arrayArticulosC.add(Articulo(5,Articulo.TipoArticulo.ARMA,Articulo.Nombre.MARTILLO,3,"martillo",5,4))
        arrayArticulosC.add(Articulo(6,Articulo.TipoArticulo.PROTECCION,Articulo.Nombre.ESCUDO,3,"escudo",2,4))
        arrayArticulosC.add(Articulo(7,Articulo.TipoArticulo.PROTECCION,Articulo.Nombre.ARMADURA,3,"armadura",7,4))
        arrayArticulosC.add(Articulo(8,Articulo.TipoArticulo.OBJETO,Articulo.Nombre.IRA,3,"ira",1,4))
        arrayArticulosC.add(Articulo(9,Articulo.TipoArticulo.OBJETO,Articulo.Nombre.POCION,3,"pocion",8,4))
        for (i in 0..9){
            insertarArticulo(arrayArticulosC[i])
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_OBJETOS")
        onCreate(db)
    }
    fun insertarArticulo(articulo: Articulo):Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, articulo.getNombre().toString())
            put(COLUMN_PESO, articulo.getPeso())
            put(COLUMN_TIPO, articulo.getTipoArticulo().toString())
            put(COLUMN_IMG, articulo.getImg())
            put(COLUMN_UNIDADES, articulo.getUnidades())
            put(COLUMN_VALOR, articulo.getValor())
        }
        val id= db.insert(TABLA_OBJETOS, null, values)
        db.close()
        return id
    }
    @SuppressLint("Range")
    fun retirarArticulo(articulo: Articulo, unidad: Int){
        val idD= articulo.getId()
        val selectQuery = "SELECT * FROM $TABLA_OBJETOS WHERE $KEY_ID=$idD"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        val unidades = cursor.getInt(cursor.getColumnIndex(COLUMN_UNIDADES))
        if(unidades==unidad){
            db.execSQL("DELETE FROM $TABLA_OBJETOS WHERE $KEY_ID=$idD");
        }else{
            val cont=articulo.getUnidades()-unidad
            db.execSQL("UPDATE $TABLA_OBJETOS SET $COLUMN_UNIDADES=$cont WHERE $KEY_ID=$idD")
        }
        db.close()
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
                articulos.add(Articulo(id,enum.enuT(tipo), enum.enuN(nombre), peso, img, unidades, valor))} while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return articulos
    }
}