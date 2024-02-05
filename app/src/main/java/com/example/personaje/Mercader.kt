package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import java.util.Random

class Mercader : AppCompatActivity() {
    var unidades = 1
    val unidadMaxima = 10
    val unidadMinima = 0
    private lateinit var rest: ImageButton
    private lateinit var sum: ImageButton
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
        val dbHelper2 = DatabaseHelper2(this)
        val arrayArticulos = dbHelper2.getArticulo()
        rest = findViewById(R.id.buttonRestar)
        var uds: TextView = findViewById(R.id.udsText)
        sum = findViewById(R.id.buttonRestar)
        val arrayArc = arrayArticulos.joinToString("\n")
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

        continuar.setOnClickListener {
            var intent = Intent(this@Mercader, Aventura::class.java)
            intent.putParcelableArrayListExtra("contenido", moch?.getContenido())
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            startActivity(intent)
        }

        comerciar.setOnClickListener {
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
            }

            compra.setOnClickListener {
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
                }
                var ale = Random()
                var num = ale.nextInt(10)
                var imagen = arrayArticulos[num].getImg()
                var ruta = resources.getIdentifier(imagen, "drawable", packageName)
                foto.setImageResource(ruta)
                arti.text = arrayArticulos[num].toString()
                arti.visibility = View.VISIBLE
                foto.visibility = View.VISIBLE
                rest.visibility = View.VISIBLE
                sum.visibility = View.VISIBLE
                uds.visibility = View.VISIBLE
                sum.setOnClickListener {
                    if (unidades < unidadMaxima) {
                        unidades++
                        uds.text = unidades.toString()
                        actualizarBotones()
                    }
                }
                rest.setOnClickListener {
                    if (unidades > unidadMinima) {
                        unidades--
                        uds.text = unidades.toString()
                        actualizarBotones()
                    }
                }
            }

            venta.setOnClickListener {
                cancela.setOnClickListener {
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
                arti.visibility = View.VISIBLE
                foto.setImageResource(R.drawable.mochila)
                foto.visibility = View.VISIBLE
                rest.visibility = View.VISIBLE
                sum.visibility = View.VISIBLE
                uds.visibility = View.VISIBLE
                arti.text = moch.toString()
                sum.setOnClickListener {
                    if (unidades < unidadMaxima) {
                        unidades++
                        uds.text = unidades.toString()
                        actualizarBotones()
                    }
                }
                rest.setOnClickListener {
                    if (unidades > unidadMinima) {
                    unidades--
                    uds.text = unidades.toString()
                    actualizarBotones()
                }
                }
            }

        }
    }

    private fun actualizarBotones() {
        sum.visibility = if (unidades >= unidadMaxima) View.INVISIBLE else View.VISIBLE
        rest.visibility = if (unidades <= unidadMinima) View.INVISIBLE else View.VISIBLE
    }
}