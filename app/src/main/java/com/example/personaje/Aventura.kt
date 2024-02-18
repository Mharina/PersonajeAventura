package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import java.util.Random

class Aventura : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aventura)
        val dado: ImageButton = findViewById(R.id.imageButton)
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch: Mochila? = intent.getParcelableExtra<Mochila>("mochila")
        val contenidoMoch = intent.getParcelableArrayListExtra<Articulo>("contenido")

        if (contenidoMoch != null) {
            contenidoMoch.forEach {
                if (moch != null) {
                    moch.actualizarMochila(it)
                }
            }
        }

        dado.setOnClickListener{
            var ale = Random()
            var num = ale.nextInt(2)
            when(num){
                2 -> {
                    var intent = Intent(this@Aventura, Enemigo::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    if (moch != null) {
                        intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                    }
                    startActivity(intent)
                }
                1 -> {
                    var intent = Intent(this@Aventura, Mercader::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    if (moch != null) {
                        intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                    }
                    startActivity(intent)
                }
                0 -> {
                    var intent = Intent(this@Aventura, Objeto::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    if (moch != null) {
                        intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                    }
                    startActivity(intent)
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.personaje->{
                val intent = Intent(this@Aventura, InfoPersonaje::class.java)
                startActivity(intent)
                Toast.makeText(this,"personaje", Toast.LENGTH_LONG).show()
            }
            R.id.mochila->{
                val intent = Intent(this@Aventura, InfoMochila::class.java)
                startActivity(intent)
                Toast.makeText(this,"mochila", Toast.LENGTH_LONG).show()
            }
            R.id.libro->{
                val intent = Intent(this@Aventura, Libro::class.java)
                startActivity(intent)
                Toast.makeText(this,"libro", Toast.LENGTH_LONG).show()
            }
            R.id.guardar->{
                Toast.makeText(this,"guardar", Toast.LENGTH_LONG).show()
            }
            R.id.guardar_salir->{
                val intent = Intent(this@Aventura, Login::class.java)
                startActivity(intent)
                Toast.makeText(this,"guardar y salir", Toast.LENGTH_LONG).show()
            }
            R.id.salir->{
                val intent = Intent(this@Aventura, Login::class.java)
                startActivity(intent)
                Toast.makeText(this,"salir", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}