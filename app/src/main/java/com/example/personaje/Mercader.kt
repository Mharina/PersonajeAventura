package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class Mercader : AppCompatActivity() {
    var unidades = 1
    val unidadMinima = 1
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
        val buy: Button = findViewById(R.id.buy)
        val dbHelperMercader = DatabaseHelperMercader(this)
        val arrayArticulos = dbHelperMercader.getArticulo()
        rest = findViewById(R.id.buttonRestar)
        var uds: TextView = findViewById(R.id.udsText)
        sum = findViewById(R.id.buttonSumar)
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
            val intent = Intent(this@Mercader, Aventura::class.java)
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
                buy.visibility = View.INVISIBLE
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
                    buy.visibility = View.INVISIBLE
                }

                var ale = Random()
                var num = ale.nextInt(10)
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
}