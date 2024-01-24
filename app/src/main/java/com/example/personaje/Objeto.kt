package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Random

class Objeto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objeto)
        val url = findViewById<TextView>(R.id.texto1)
        val nombre = findViewById<TextView>(R.id.texto1)
        val foto = findViewById<ImageView>(R.id.imageView)
        val recoger: Button = findViewById(R.id.button)
        val continuar: Button = findViewById(R.id.button2)
        val dbHelper = DatabaseHelper(this)
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")
        val artic = dbHelper.getArticulo()
        var ale = Random()
        var num = ale.nextInt(10)
        var imagen = artic[num].getImg()
        var ruta = resources.getIdentifier(imagen,"drawable",packageName)
        url.text = imagen
        foto.setImageResource(ruta)
        nombre.text = artic[num].toString()
        val contenidoMoch = intent.getParcelableArrayListExtra<Articulo>("contenido")

        contenidoMoch?.forEach {
            if (moch != null) {
                moch.addArticulo(it)
            }
        }

        continuar.setOnClickListener{
            var intent = Intent(this@Objeto, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            if (moch != null) {
                intent.putParcelableArrayListExtra("contenido", moch.getContenido())
            }
            startActivity(intent)
        }

        recoger.setOnClickListener{
            moch!!.addArticulo(artic[num])
            var intent = Intent(this@Objeto, Aventura::class.java)
            intent.putParcelableArrayListExtra("contenido", moch.getContenido())
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            startActivity(intent)
        }
    }
}