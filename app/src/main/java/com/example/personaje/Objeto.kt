package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Objeto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objeto)
        val nombre = findViewById<TextView>(R.id.texto1)
        val recojer: Button = findViewById(R.id.button)
        val continuar: Button = findViewById(R.id.button2)
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")

        continuar.setOnClickListener{
            var intent = Intent(this@Objeto, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            startActivity(intent)
        }
        recojer.setOnClickListener{
            var intent = Intent(this@Objeto, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            startActivity(intent)
        }
    }
}