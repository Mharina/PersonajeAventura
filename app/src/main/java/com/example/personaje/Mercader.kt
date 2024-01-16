package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Mercader : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mercader)
        val comerciar: Button = findViewById(R.id.button)
        val continuar: Button = findViewById(R.id.button2)
        continuar.setOnClickListener{
            var intent = Intent(this@Mercader, Aventura::class.java)
            startActivity(intent)
        }
        comerciar.setOnClickListener{
            var intent = Intent(this@Mercader, Proximamente::class.java)
            startActivity(intent)
        }
    }
}