package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class Libro : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro)

        val atras: Button = findViewById(R.id.button6)
        val handler = Handler()
        val fondo: LinearLayout = findViewById(R.id.hoja)
        val texto: TextView = findViewById(R.id.textView12)
        val dbPelea = DatabasePelea (this)


        texto.visibility = View.INVISIBLE

        fondo.background = resources.getDrawable(R.drawable.hoja_blanca)
            //poner uid del personaje
            texto.text=dbPelea.mostrarPeleas("")
            texto.visibility = View.VISIBLE


    }
}