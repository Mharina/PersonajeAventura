package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.util.Random

class Objeto : AppCompatActivity() {
    private lateinit var pj: Personaje
    private lateinit var moch: Mochila
    private lateinit var usuarioID: String
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
        val artic = dbHelper.getArticulo()
        val ale = Random()
        val num = ale.nextInt(11)
        val imagen = artic[num].getImg()
        val ruta = resources.getIdentifier(imagen, "drawable", packageName)
        val toolbar: Toolbar = findViewById(R.id.toolbarEjemplo)
        foto.setImageResource(ruta)
        nombre.text = artic[num].toString()
        pj = intent.getParcelableExtra<Personaje>("personaje")!!
        moch = intent.getParcelableExtra<Mochila>("mochila")!!
        usuarioID = intent.getStringExtra("uid").toString()
        val contenidoMoch = intent.getParcelableArrayListExtra<Articulo>("contenido")
        peso.text = "Tienes un peso de ${moch!!.getPesoMochila().toString()} libre"

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Aventura"

        if (contenidoMoch != null) {
            contenidoMoch.forEach {
                moch.actualizarMochila(it)
            }
        }

        continuar.setOnClickListener {
            val intent = Intent(this@Objeto, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            intent.putParcelableArrayListExtra("contenido", moch.getContenido())
            startActivity(intent)
        }

        recoger.setOnClickListener {

            if (artic[num].getPeso() <= moch.getPesoMochila()) {
                moch.addArticulo(artic[num], 1)
                val intent = Intent(this@Objeto, Aventura::class.java)
                intent.putExtra("personaje", pj)
                intent.putExtra("mochila", moch)
                intent.putExtra("uid",usuarioID)
                if (moch != null) {
                    intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                }
                startActivity(intent)
            } else {
                Toast.makeText(
                    this,
                    "Peso excede del peso TOTAL\tTienes ${moch.getPesoMochila()} libre.",
                    Toast.LENGTH_LONG
                ).show()
            }


        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.personaje -> {
                val intent = Intent(this, InfoPersonaje::class.java)
                intent.putExtra("personaje", pj)
                intent.putExtra("mochila", moch)
                intent.putExtra("uid", usuarioID)
                if (moch != null) {
                    intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                }
                startActivity(intent)
                Toast.makeText(this, "personaje", Toast.LENGTH_LONG).show()
            }

            R.id.mochila -> {
                val intent = Intent(this, InfoMochila::class.java)
                intent.putExtra("personaje", pj)
                intent.putExtra("mochila", moch)
                intent.putExtra("uid", usuarioID)
                if (moch != null) {
                    intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                }
                startActivity(intent)
                Toast.makeText(this, "mochila", Toast.LENGTH_LONG).show()
            }

            R.id.libro -> {
                val intent = Intent(this, Libro::class.java)
                intent.putExtra("personaje", pj)
                intent.putExtra("mochila", moch)
                intent.putExtra("uid", usuarioID)
                if (moch != null) {
                    intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                }
                startActivity(intent)
                Toast.makeText(this, "libro", Toast.LENGTH_LONG).show()
            }

            R.id.guardar -> {

                Toast.makeText(this, "guardar", Toast.LENGTH_LONG).show()
            }

            R.id.guardar_salir -> {
                val intent = Intent(this, Login::class.java)
                // guardar mochila, personaje etc
                startActivity(intent)
                Toast.makeText(this, "guardar y salir", Toast.LENGTH_LONG).show()
            }

            R.id.salir -> {
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                Toast.makeText(this, "salir", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}