package com.example.personaje

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.util.Random

class Aventura : AppCompatActivity() {
    private lateinit var pj: Personaje
    private lateinit var moch: Mochila
    private lateinit var usuarioID: String
    private lateinit var mp: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aventura)
        val dado: ImageButton = findViewById(R.id.imageButton)
        val toolbar: Toolbar = findViewById(R.id.toolbarEjemplo)
        mp = MediaPlayer.create(this, R.raw.skyrim_from_past_to_present)
        pj = intent.getParcelableExtra<Personaje>("personaje")!!
        moch = intent.getParcelableExtra<Mochila>("mochila")!!
        usuarioID = intent.getStringExtra("uid").toString()

        val musica: ImageButton = findViewById(R.id.musica)
        val contenidoMoch = intent.getParcelableArrayListExtra<Articulo>("contenido")

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Aventura"

        if (contenidoMoch != null) {
            contenidoMoch.forEach {
                if (moch != null) {
                    moch.actualizarMochila(it)
                }
            }
        }

        var pos=0
        mp.isLooping = true
        mp.start()
        musica.setOnClickListener {
            if(mp.isPlaying){
                pos = mp.currentPosition
                mp.pause()
                mp.isLooping = false
                musica.setImageResource(R.drawable.sin_sonido)
            }else{
                musica.setImageResource(R.drawable.herramienta_de_audio_con_altavoz)
                mp.seekTo(pos)
                mp.start()
                mp.isLooping = true
            }
        }

        dado.setOnClickListener{
            if(mp.isPlaying){
                mp.stop()
            }
            var ale = Random()
            var num = ale.nextInt(3)
            when(num){
                2 -> {
                    var intent = Intent(this@Aventura, Enemigo::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    intent.putExtra("uid", usuarioID)
                    if (moch != null) {
                        intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                    }
                    startActivity(intent)
                }
                1 -> {
                    var intent = Intent(this@Aventura, Mercader::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    intent.putExtra("uid", usuarioID)
                    if (moch != null) {
                        intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                    }
                    startActivity(intent)
                }
                0 -> {
                    var intent = Intent(this@Aventura, Objeto::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    intent.putExtra("uid", usuarioID)
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
                if(mp.isPlaying){
                    mp.stop()
                }
                val intent = Intent(this, InfoPersonaje::class.java)
                intent.putExtra("personaje", pj)
                intent.putExtra("mochila", moch)
                intent.putExtra("uid", usuarioID)
                if (moch != null) {
                    intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                }
                startActivity(intent)
                Toast.makeText(this,"personaje", Toast.LENGTH_LONG).show()
            }
            R.id.mochila->{
                if(mp.isPlaying){
                    mp.stop()
                }
                val intent = Intent(this, InfoMochila::class.java)
                intent.putExtra("personaje", pj)
                intent.putExtra("mochila", moch)
                intent.putExtra("uid", usuarioID)
                if (moch != null) {
                    intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                }
                startActivity(intent)
                Toast.makeText(this,"mochila", Toast.LENGTH_LONG).show()
            }
            R.id.libro->{
                if(mp.isPlaying){
                    mp.stop()
                }
                val intent = Intent(this, Libro::class.java)
                intent.putExtra("personaje", pj)
                intent.putExtra("mochila", moch)
                intent.putExtra("uid", usuarioID)
                if (moch != null) {
                    intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                }
                startActivity(intent)
                Toast.makeText(this,"libro", Toast.LENGTH_LONG).show()
            }
            R.id.guardar->{
                val dbPJ = DatabasePersonaje(this)
                dbPJ.insertarPersonaje(pj,usuarioID)
                Toast.makeText(this,"guardar", Toast.LENGTH_LONG).show()
            }
            R.id.guardar_salir->{
                if(mp.isPlaying){
                    mp.stop()
                }
                val intent = Intent(this, Login::class.java)
                val dbPJ = DatabasePersonaje(this)
                dbPJ.insertarPersonaje(pj,usuarioID)
                startActivity(intent)
                Toast.makeText(this,"guardar y salir", Toast.LENGTH_LONG).show()
            }
            R.id.salir->{
                if(mp.isPlaying){
                    mp.stop()
                }
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                Toast.makeText(this,"salir", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}