package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Objeto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objeto)
        val recojer: Button = findViewById(R.id.button)
        val continuar: Button = findViewById(R.id.button2)
        continuar.setOnClickListener{
            var intent = Intent(this@Objeto, Aventura::class.java)
            startActivity(intent)
        }
        recojer.setOnClickListener{
            var intent = Intent(this@Objeto, Aventura::class.java)
            startActivity(intent)
        }
    }
}