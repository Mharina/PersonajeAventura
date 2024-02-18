package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.personaje.databinding.ActivityPartidaBinding

class Partida : AppCompatActivity() {
    private lateinit var binding : ActivityPartidaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartidaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val usuarioID = intent.getStringExtra("uid").toString()

        binding.Empezar.setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra("uid",usuarioID)
            startActivity(intent)
        }

        binding.Continuar.setOnClickListener {
            val dbPj = DatabasePersonaje(this)
            val pj=dbPj.getPersonaje(usuarioID)
            var intent = Intent(this, Aventura::class.java)
            intent.putExtra("uid",usuarioID)
            intent.putExtra("personaje", pj)
            startActivity(intent)
            Toast.makeText(this, "Debería buscar en la DB por el pj y pasarlo a la activity Aventura", Toast.LENGTH_LONG).show()
        }
    }
}