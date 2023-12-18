package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.personaje.Imagen

class PersonajeMostrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje_mostrar)

        val pj = intent.getParcelableExtra<Personaje>("personaje")
        //val img = intent.getStringExtra("imagen_id")

        //var imagen : Imagen = findViewById<ImageView>(R.id.imageView2)



        val nombre = findViewById<TextView>(R.id.textView5)
        val raza = findViewById<TextView>(R.id.textView7)
        val clase = findViewById<TextView>(R.id.textView9)
        val estadoVital = findViewById<TextView>(R.id.textView11)
        //var foto: ImageView = findViewById(R.id.imageView2)

        //foto.setImageResource(img.drawa)
        nombre.text = "${pj?.getNombre()}"
        raza.text = "${pj?.getRaza()}"
        clase.text = "${pj?.getClase()}"
        estadoVital.text = "${pj?.getEstadoVital()}"
        var btnVolver: Button = findViewById(R.id.Volver1)
        var btnComenzar: Button = findViewById(R.id.empezar)

        btnVolver.setOnClickListener {
            val intent = Intent(this@PersonajeMostrar, MainActivity::class.java)
            startActivity(intent)
        }
        btnComenzar.setOnClickListener {
            val intent = Intent(this@PersonajeMostrar, Aventura::class.java)
            startActivity(intent)
        }
    }
}