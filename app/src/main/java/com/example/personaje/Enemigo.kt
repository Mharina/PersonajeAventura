package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Random

class Enemigo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enemigo)
        val huir: Button = findViewById(R.id.button2)
        val luchar: Button = findViewById(R.id.button)
        var vidapj: TextView = findViewById(R.id.textView12)
        var vidam: TextView = findViewById(R.id.textView13)
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")
        val dbHelper3 = DatabaseEnemigo(this)
        val arrayMonstruo = dbHelper3.getMonstruo()
        var ale = Random()
        var num=0
        if((pj!!.getNivel()+2)<=10){
            num = (1..(pj!!.getNivel()+2)).random()
        }else{
            num = (1..10).random()
        }
        val monstruo=arrayMonstruo[num]
        var vidaPersonaje = pj!!.getSalud()
        var vidaMonstruo = monstruo.getSalud()
        vidapj.text = "Vida: ${vidaPersonaje} Nivel: ${pj?.getNivel()}"
        vidam.text = "Vida: ${vidaMonstruo} Nivel: ${monstruo.getNivel()}"
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