package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

class InfoPersonaje : AppCompatActivity() {
    private lateinit var pj: Personaje
    private lateinit var moch: Mochila
    private lateinit var usuarioID: String
    val mp: MediaPlayer = MediaPlayer.create(this, R.raw.skyrim_tundra)

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
        val foto: ImageView = findViewById(R.id.fotoP)
        val toolbar: Toolbar = findViewById(R.id.toolbarEjemplo)
        pj = intent.getParcelableExtra<Personaje>("personaje")!!
        moch = intent.getParcelableExtra<Mochila>("mochila")!!
        usuarioID = intent.getStringExtra("uid").toString()
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.skyrim_tundra)
        val musica: ImageButton = findViewById(R.id.musica)

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

                Toast.makeText(this,"guardar", Toast.LENGTH_LONG).show()
            }
            R.id.guardar_salir->{
                if(mp.isPlaying){
                    mp.stop()
                }
                val intent = Intent(this, Login::class.java)
                // guardar mochila, personaje etc
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