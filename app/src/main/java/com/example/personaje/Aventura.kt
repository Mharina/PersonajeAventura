package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import java.util.Random

class Aventura : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aventura)
        val dado: ImageButton = findViewById(R.id.imageButton)
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")

        dado.setOnClickListener{
            var intent = Intent(this@Aventura, Mercader::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            startActivity(intent)
            /*var ale = Random()
            var num = ale.nextInt(4)
            when(num){
                0 -> {
                    var intent = Intent(this@Aventura, Ciudad::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    startActivity(intent)
                }
                1 -> {
                    var intent = Intent(this@Aventura, Enemigo::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    startActivity(intent)
                }
                2 -> {
                    var intent = Intent(this@Aventura, Mercader::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    startActivity(intent)
                }
                3 -> {
                    var intent = Intent(this@Aventura, Objeto::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    startActivity(intent)
                }
            }*/
        }
    }
}