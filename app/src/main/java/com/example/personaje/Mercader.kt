package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
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
        val foto: ImageView = findViewById(R.id.imageView)
        val dbHelper2 = DatabaseHelper2(this)
        val listArtC: List<Articulo> = dbHelper2.getArticulo()
        val arrayArC = listArtC.joinToString(separator = "\n") { it.toString() }
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")

        continuar.setOnClickListener {
            var intent = Intent(this@Mercader, Aventura::class.java)
            startActivity(intent)
        }
        comerciar.setOnClickListener {
            comerciar.visibility = View.GONE
            continuar.visibility = View.GONE
            compra.visibility = View.VISIBLE
            venta.visibility = View.VISIBLE
            cancela.visibility = View.VISIBLE
            cancela.setOnClickListener {
                foto.setImageResource(R.drawable.mercader)
                comerciar.visibility = View.VISIBLE
                continuar.visibility = View.VISIBLE
                foto.visibility = View.VISIBLE
                arti.visibility = View.GONE
                compra.visibility = View.GONE
                venta.visibility = View.GONE
                cancela.visibility = View.GONE
            }
            compra.setOnClickListener {
                cancela.setOnClickListener {
                    foto.setImageResource(R.drawable.mercader)
                    comerciar.visibility = View.VISIBLE
                    continuar.visibility = View.VISIBLE
                    foto.visibility = View.VISIBLE
                    arti.visibility = View.GONE
                    compra.visibility = View.GONE
                    venta.visibility = View.GONE
                    cancela.visibility = View.GONE
                }
                arti.text = "LISTA DE ARTICULOS\n" + arrayArC
                arti.visibility = View.VISIBLE
                foto.visibility = View.GONE
            }
            venta.setOnClickListener {
                cancela.setOnClickListener {
                    foto.setImageResource(R.drawable.mercader)
                    comerciar.visibility = View.VISIBLE
                    continuar.visibility = View.VISIBLE
                    arti.visibility = View.GONE
                    compra.visibility = View.GONE
                    venta.visibility = View.GONE
                    cancela.visibility = View.GONE
                }
                arti.visibility = View.VISIBLE
                foto.setImageResource(R.drawable.mochila)
                foto.visibility = View.VISIBLE
                arti.text= moch.toString()
            }
        }
    }
}