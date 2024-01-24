package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Enemigo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enemigo)
        val huir: Button = findViewById(R.id.button2)
        val luchar: Button = findViewById(R.id.button)
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")
        huir.setOnClickListener{
            var intent = Intent(this@Enemigo, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            startActivity(intent)
        }
        luchar.setOnClickListener{
            var intent = Intent(this@Enemigo, Proximamente::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            startActivity(intent)
        }
    }
}