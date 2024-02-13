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

        binding.Empezar.setOnClickListener{
            startActivity(Intent (this, MainActivity::class.java))
        }

        binding.Continuar.setOnClickListener {
            Toast.makeText(this, "Deberíua buscar en la DB por el pj y pasarlo a la activity Aventura", Toast.LENGTH_LONG).show()
        }
    }
}