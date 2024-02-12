package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class Objeto : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_objeto)

        val nombre = findViewById<TextView>(R.id.objMostrado)
        val peso = findViewById<TextView>(R.id.pesoLibre)
        val foto = findViewById<ImageView>(R.id.imageView)
        val recoger: Button = findViewById(R.id.button)
        val continuar: Button = findViewById(R.id.button2)
        val dbHelper = DatabaseHelper(this)
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val artic = dbHelper.getArticulo()
        val ale = Random()
        val num = ale.nextInt(11)
        val imagen = artic[num].getImg()
        val ruta = resources.getIdentifier(imagen,"drawable",packageName)
        foto.setImageResource(ruta)
        nombre.text = artic[num].toString()
        val moch: Mochila? = intent.getParcelableExtra<Mochila>("mochila")
        val contenidoMoch = intent.getParcelableArrayListExtra<Articulo>("contenido")
        peso.text = "Tienes un peso de ${moch!!.getPesoMochila().toString()} libre"

        if (contenidoMoch != null) {
            contenidoMoch.forEach {
                moch.actualizarMochila(it)
            }
        }

        continuar.setOnClickListener{
            val intent = Intent(this@Objeto, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            intent.putParcelableArrayListExtra("contenido", moch.getContenido())
            startActivity(intent)
        }

        recoger.setOnClickListener{

//            var pos = moch?.findObjeto(artic[num].getNombre())
//            if (pos!=-1){
//                artic[num].setUnidades(artic[num].getUnidades()+1)
//                moch!!.addArticulo(artic[num])
//            } else {
//                moch!!.addArticulo(artic[num])
//            }
            if (artic[num].getPeso()<= moch.getPesoMochila()){
                moch.addArticulo(artic[num], 1)
                val intent = Intent(this@Objeto, Aventura::class.java)
                intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                intent.putExtra("personaje", pj)
                intent.putExtra("mochila", moch)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Peso excede del peso TOTAL\tTienes ${moch.getPesoMochila()} libre.", Toast.LENGTH_LONG).show()
            }


        }
    }
}