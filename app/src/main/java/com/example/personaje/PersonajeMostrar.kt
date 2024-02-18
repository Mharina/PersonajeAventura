package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.Locale

class PersonajeMostrar : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje_mostrar)

        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")
        val toolbar: Toolbar = findViewById(R.id.toolbarEjemplo)
        val usuarioID = intent.getStringExtra("uid").toString()
        val nombre = findViewById<TextView>(R.id.textView5)
        val raza = findViewById<TextView>(R.id.textView7)
        val clase = findViewById<TextView>(R.id.textView9)
        val estadoVital = findViewById<TextView>(R.id.textView11)
        val foto: ImageView = findViewById(R.id.imageView2)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Persionaje Creado"
        nombre.text = "${pj?.getNombre()}"
        raza.text = "${pj?.getRaza()}"
        clase.text = "${pj?.getClase()}"
        estadoVital.text = "${pj?.getEstadoVital()}"
        val btnVolver: Button = findViewById(R.id.Volver1)
        val btnComenzar: Button = findViewById(R.id.empezar)

        val imagen = obtImg()
        imagen.obtenerImagen3(foto,pj?.getRaza().toString(),pj?.getClase().toString(),pj?.getEstadoVital().toString())

        textToSpeech = TextToSpeech(this){status->
            if (status ==TextToSpeech.SUCCESS){
                val result = textToSpeech.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA||result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(this,"lenguaje no soportado", Toast.LENGTH_LONG).show()
                }
            }
        }

        btnVolver.setOnClickListener {
            textToSpeech.speak(
                btnVolver.text.toString().trim(),
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
            val intent = Intent(this@PersonajeMostrar, MainActivity::class.java)
            startActivity(intent)
        }
        btnComenzar.setOnClickListener {
            textToSpeech.speak(
                btnComenzar.text.toString().trim(),
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
            val intent = Intent(this@PersonajeMostrar, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            intent.putExtra("uid",usuarioID)
            if (moch != null) {
                intent.putParcelableArrayListExtra("contenido", moch.getContenido())
            }
            startActivity(intent)
        }
    }
}