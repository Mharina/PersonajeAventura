package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

class Libro : AppCompatActivity() {
    val mp: MediaPlayer = MediaPlayer.create(this, R.raw.skyrim_tundra)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_libro)

        val atras: Button = findViewById(R.id.button6)
        val handler = Handler()
        val fondo: LinearLayout = findViewById(R.id.hoja)
        val texto: TextView = findViewById(R.id.textView12)
        val dbPelea = DatabasePelea (this)

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

        texto.visibility = View.INVISIBLE

        fondo.background = resources.getDrawable(R.drawable.hoja_blanca)
            //poner uid del personaje
            texto.text=dbPelea.mostrarPeleas("")
            texto.visibility = View.VISIBLE


    }
}