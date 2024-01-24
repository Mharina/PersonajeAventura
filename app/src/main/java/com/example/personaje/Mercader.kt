package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isInvisible

class Mercader : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mercader)
        val comerciar: Button = findViewById(R.id.button)
        val continuar: Button = findViewById(R.id.button2)
        val arti: TextView = findViewById(R.id.art)
        val compra: Button = findViewById(R.id.compra)
        val venta: Button = findViewById(R.id.venta)
        val cancela: Button = findViewById(R.id.cancelar)
        val dbHelper = DatabaseHelper2(this)
        val listArt : List<Articulo> = dbHelper.getArticulo()
        val arrayArt = listArt.joinToString(separator = "\n") { it.toString() }

        arti.text = "LISTA DE ARTICULOS\n"+arrayArt

        continuar.setOnClickListener{
            var intent = Intent(this@Mercader, Aventura::class.java)
            startActivity(intent)
        }
        comerciar.setOnClickListener{
            comerciar.visibility = View.GONE
            continuar.visibility = View.GONE
            arti.visibility = View.VISIBLE
            compra.visibility = View.VISIBLE
            venta.visibility = View.VISIBLE
            cancela.visibility = View.VISIBLE
        }
    }
}