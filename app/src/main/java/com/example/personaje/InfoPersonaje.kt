package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class InfoPersonaje : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_personaje)

        val nombre = findViewById<TextView>(R.id.textView19)
        val raza = findViewById<TextView>(R.id.textView21)
        val clase = findViewById<TextView>(R.id.textView23)
        val estadoVital = findViewById<TextView>(R.id.textView25)
        val nivel = findViewById<TextView>(R.id.textView27)
        val salud = findViewById<TextView>(R.id.textView29)
        val ataque = findViewById<TextView>(R.id.textView31)
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")
        val foto: ImageView = findViewById(R.id.fotoP)
        val toolbar: Toolbar = findViewById(R.id.toolbarEjemplo)

        setSupportActionBar(toolbar)
        supportActionBar?.title="Ficha Personaje"

        nombre.text = "${pj?.getNombre()}"
        raza.text = "${pj?.getRaza()}"
        clase.text = "${pj?.getClase()}"
        estadoVital.text = "${pj?.getEstadoVital()}"
        nivel.text = "${pj?.getNivel()}"
        salud.text = "${pj?.getSalud()}"
        ataque.text = "${pj?.getAtaque()}"

        val imagen = obtImg()
        imagen.obtenerImagen3(foto,pj?.getRaza().toString(),pj?.getClase().toString(),pj?.getEstadoVital().toString())
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.personaje->{
                val intent = Intent(this, InfoPersonaje::class.java)
                // Recuperar objetos y arrays? a traves de las tablas
//                intent.putExtra("personaje", pj)
//                intent.putExtra("mochila", moch)
//                intent.putExtra("uid", usuarioID)
//                if (moch != null) {
//                    intent.putParcelableArrayListExtra("contenido", moch.getContenido())
//                }
                startActivity(intent)
                Toast.makeText(this,"personaje", Toast.LENGTH_LONG).show()
            }
            R.id.mochila->{
                val intent = Intent(this, PersonajeMostrar::class.java)
//                intent.putExtra("personaje", pj)
//                intent.putExtra("mochila", moch)
//                intent.putExtra("uid", usuarioID)
//                if (moch != null) {
//                    intent.putParcelableArrayListExtra("contenido", moch.getContenido())
//                }
                startActivity(intent)
                Toast.makeText(this,"mochila", Toast.LENGTH_LONG).show()
            }
            R.id.libro->{
                val intent = Intent(this, Libro::class.java)
//                intent.putExtra("personaje", pj)
//                intent.putExtra("mochila", moch)
//                intent.putExtra("uid", usuarioID)
//                if (moch != null) {
//                    intent.putParcelableArrayListExtra("contenido", moch.getContenido())
//                }
                startActivity(intent)
                Toast.makeText(this,"libro", Toast.LENGTH_LONG).show()
            }
            R.id.guardar->{

                Toast.makeText(this,"guardar", Toast.LENGTH_LONG).show()
            }
            R.id.guardar_salir->{
                val intent = Intent(this, Login::class.java)
                // guardar mochila, personaje etc
                startActivity(intent)
                Toast.makeText(this,"guardar y salir", Toast.LENGTH_LONG).show()
            }
            R.id.salir->{
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                Toast.makeText(this,"salir", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}