package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.util.Locale
import java.util.Random

class Mercader : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech
    var unidades = 1
    var unidadMinima = 1
    private lateinit var rest: ImageButton
    private lateinit var sum: ImageButton
    private lateinit var pj: Personaje
    private lateinit var moch: Mochila
    private lateinit var usuarioID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mercader)
        val comerciar: Button = findViewById(R.id.button)
        val continuar: Button = findViewById(R.id.button2)
        val arti: TextView = findViewById(R.id.art)
        val compra: Button = findViewById(R.id.compra)
        val venta: Button = findViewById(R.id.venta)
        val cancela: Button = findViewById(R.id.cancelar)
        val foto: ImageView = findViewById(R.id.imageView)
        val buy: Button = findViewById(R.id.buy)
        val dbHelperMercader = DatabaseHelperMercader(this)
        val arrayArticulos = dbHelperMercader.getArticulo()
        sum = findViewById(R.id.buttonSumar)
        rest = findViewById(R.id.buttonRestar)
        val uds: TextView = findViewById(R.id.udsText)
        val toolbar: Toolbar = findViewById(R.id.toolbarEjemplo)
        pj = intent.getParcelableExtra<Personaje>("personaje")!!
        moch = intent.getParcelableExtra<Mochila>("mochila")!!
        usuarioID = intent.getStringExtra("uid").toString()
        val contenidoMoch = intent.getParcelableArrayListExtra<Articulo>("contenido")

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Mercader"

        if (contenidoMoch != null) {
            contenidoMoch.forEach {
                if (moch != null) {
                    moch.actualizarMochila(it)
                }
            }
        }

        textToSpeech = TextToSpeech(this){status->
            if (status ==TextToSpeech.SUCCESS){
                val result = textToSpeech.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA||result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(this,"lenguaje no soportado", Toast.LENGTH_LONG).show()
                }
            }
        }

        continuar.setOnClickListener {
            textToSpeech.speak(
                continuar.text.toString().trim(),
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
            val intent = Intent(this@Mercader, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            intent.putExtra("uid",usuarioID)
            if (moch != null) {
                intent.putParcelableArrayListExtra("contenido", moch.getContenido())
            }
            startActivity(intent)
        }

        comerciar.setOnClickListener {
            textToSpeech.speak(
                comerciar.text.toString().trim(),
                TextToSpeech.QUEUE_FLUSH,
                null,
                null
            )
            comerciar.visibility = View.GONE
            continuar.visibility = View.GONE
            compra.visibility = View.VISIBLE
            venta.visibility = View.VISIBLE
            cancela.visibility = View.VISIBLE
            cancela.setOnClickListener {
                foto.setImageResource(R.drawable.mercader)
                comerciar.visibility = View.VISIBLE
                continuar.visibility = View.VISIBLE
                foto.visibility = View.VISIBLE
                arti.visibility = View.GONE
                compra.visibility = View.GONE
                venta.visibility = View.GONE
                cancela.visibility = View.GONE
                rest.visibility = View.INVISIBLE
                sum.visibility = View.INVISIBLE
                uds.visibility = View.INVISIBLE
                buy.visibility = View.INVISIBLE
            }

            compra.setOnClickListener {
                textToSpeech.speak(
                    compra.text.toString().trim(),
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                )
                cancela.setOnClickListener {
                    textToSpeech.speak(
                        cancela.text.toString().trim(),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                    uds.text = "1"
                    foto.setImageResource(R.drawable.mercader)
                    comerciar.visibility = View.VISIBLE
                    continuar.visibility = View.VISIBLE
                    foto.visibility = View.VISIBLE
                    arti.visibility = View.GONE
                    compra.visibility = View.GONE
                    venta.visibility = View.GONE
                    cancela.visibility = View.GONE
                    rest.visibility = View.INVISIBLE
                    sum.visibility = View.INVISIBLE
                    uds.visibility = View.INVISIBLE
                    buy.visibility = View.INVISIBLE
                }

                var num = (0..arrayArticulos.size).random()
                var imagen = arrayArticulos[num].getImg()
                var ruta = resources.getIdentifier(imagen, "drawable", packageName)
                actualizarBotones(dbHelperMercader.obtUdsArt(num + 1))

                foto.setImageResource(ruta)
                arti.text = arrayArticulos[num].toString()
                arti.visibility = View.VISIBLE
                foto.visibility = View.VISIBLE
                rest.visibility = View.VISIBLE
                sum.visibility = View.VISIBLE
                uds.visibility = View.VISIBLE
                buy.visibility = View.VISIBLE
                sum.setOnClickListener {
                    if (unidades < dbHelperMercader.obtUdsArt(num + 1)) {
                        unidades++
                        uds.text = unidades.toString()
                        actualizarBotones(dbHelperMercader.obtUdsArt(num + 1))
                    }
                }
                rest.setOnClickListener {
                    if (unidades > unidadMinima) {
                        unidades--
                        uds.text = unidades.toString()
                        actualizarBotones(dbHelperMercader.obtUdsArt(num + 1))
                    }
                }
                buy.setOnClickListener {
                    if ((arrayArticulos[num].getPeso()) * unidades <= moch!!.getPesoMochila()!!) {
                        moch!!.addArticulo(arrayArticulos[num], unidades)
                    } else {
                        Toast.makeText(
                            this,
                            "Peso excede del peso TOTAL.\tTienes ${moch.getPesoMochila()} libre.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    dbHelperMercader.retirarArticulo(arrayArticulos[num], unidades)
                    Toast.makeText(
                        this,
                        "Comprado ${unidades} ${arrayArticulos[num].getNombre()}.\tTienes ${moch.getPesoMochila()} libre.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            venta.setOnClickListener {
                textToSpeech.speak(
                    venta.text.toString().trim(),
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                )
                cancela.setOnClickListener {
                    textToSpeech.speak(
                        cancela.text.toString().trim(),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        null
                    )
                    foto.setImageResource(R.drawable.mercader)
                    comerciar.visibility = View.VISIBLE
                    continuar.visibility = View.VISIBLE
                    arti.visibility = View.GONE
                    compra.visibility = View.GONE
                    venta.visibility = View.GONE
                    cancela.visibility = View.GONE
                    rest.visibility = View.INVISIBLE
                    sum.visibility = View.INVISIBLE
                    uds.visibility = View.INVISIBLE
                }
                actualizarBotones(5)
                arti.visibility = View.VISIBLE
                foto.setImageResource(R.drawable.mochila)
                foto.visibility = View.VISIBLE
                rest.visibility = View.VISIBLE
                sum.visibility = View.VISIBLE
                uds.visibility = View.VISIBLE
                arti.text = moch.toString()
                sum.setOnClickListener {
                    if (unidades < 5) {
                        unidades++
                        uds.text = unidades.toString()
                        actualizarBotones(5)
                    }
                }
                rest.setOnClickListener {
                    if (unidades > unidadMinima) {
                        unidades--
                        uds.text = unidades.toString()
                        actualizarBotones(5)
                    }
                }
            }

        }
    }

    private fun actualizarBotones(unidadMax: Int) {
        sum.visibility = if (unidades >= unidadMax) View.INVISIBLE else View.VISIBLE
        rest.visibility = if (unidades <= unidadMinima) View.INVISIBLE else View.VISIBLE
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.personaje->{
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