package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Ciudad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciudad)
        val entrar: Button = findViewById(R.id.button)
        val continuar: Button = findViewById(R.id.button2)
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")
        continuar.setOnClickListener{
            var intent = Intent(this@Ciudad, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            startActivity(intent)
        }
        entrar.setOnClickListener{
            var intent = Intent(this@Ciudad, Proximamente::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            startActivity(intent)
        }
    }
}