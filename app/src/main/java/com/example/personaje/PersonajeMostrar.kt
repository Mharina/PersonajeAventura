package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class PersonajeMostrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje_mostrar)

        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")

        val nombre = findViewById<TextView>(R.id.textView5)
        val raza = findViewById<TextView>(R.id.textView7)
        val clase = findViewById<TextView>(R.id.textView9)
        val estadoVital = findViewById<TextView>(R.id.textView11)
        val foto: ImageView = findViewById(R.id.imageView2)
        val toolbar: Toolbar = findViewById(R.id.toolbarEjemplo)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Información del Personaje"

        nombre.text = "${pj?.getNombre()}"
        raza.text = "${pj?.getRaza()}"
        clase.text = "${pj?.getClase()}"
        estadoVital.text = "${pj?.getEstadoVital()}"
        val btnVolver: Button = findViewById(R.id.Volver1)
        val btnComenzar: Button = findViewById(R.id.empezar)

        val imagen = obtImg()
        imagen.obtenerImagen3(foto,pj?.getRaza().toString(),pj?.getClase().toString(),pj?.getEstadoVital().toString())

        btnVolver.setOnClickListener {
            val intent = Intent(this@PersonajeMostrar, MainActivity::class.java)
            startActivity(intent)
        }
        btnComenzar.setOnClickListener {
            val intent = Intent(this@PersonajeMostrar, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            startActivity(intent)
        }
    }
}