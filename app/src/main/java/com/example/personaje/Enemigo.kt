package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class Enemigo : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enemigo)
        val huir: Button = findViewById(R.id.button2)
        val luchar: Button = findViewById(R.id.button)
        var vidapj: TextView = findViewById(R.id.textView12)
        var vidam: TextView = findViewById(R.id.textView13)
        val habilidad: Button = findViewById(R.id.button5)
        val atacar: Button = findViewById(R.id.button3)
        val inventario: Button = findViewById(R.id.button4)
        var texto1: TextView = findViewById(R.id.textView14)
        var texto2: TextView = findViewById(R.id.textView15)
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")
        val dbHelper3 = DatabaseEnemigo(this)
        val arrayMonstruo = dbHelper3.getMonstruo()
        var num = 0
        if ((pj!!.getNivel() + 2) <= 10) {
            num = (1..(pj!!.getNivel() + 2)).random()
        } else {
            num = (1..10).random()
        }
        val monstruo = arrayMonstruo[num]
        var vidaPersonaje = pj!!.getSalud()
        var vidaMonstruo = monstruo.getSalud()
        vidapj.text = "Vida: ${vidaPersonaje} Nivel: ${pj?.getNivel()}"
        vidam.text = "Vida: ${vidaMonstruo} Nivel: ${monstruo.getNivel()}"
        huir.setOnClickListener {
            var intent = Intent(this@Enemigo, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            startActivity(intent)
        }
        luchar.setOnClickListener {
            luchar.visibility = View.GONE
            huir.visibility = View.GONE
            habilidad.visibility = View.VISIBLE
            atacar.visibility = View.VISIBLE
            inventario.visibility = View.VISIBLE
            var expGanada = monstruo.getSalud()
            var salir = false
            var hab = false
            var evasion: Double
            evasion = (1..(10)).random()+(pj.getNivel()*0.1)
            while (vidaMonstruo > 0 && vidaPersonaje > 0) {
                habilidad.setOnClickListener {
                    if (!hab) {
                        when (pj.getClase()) {
                            "Mago" -> {
                                pj.calcularSalud()
                                vidaPersonaje = pj.getSalud()
                                texto1.text =
                                    "${pj.getNombre()} utiliza su habilidad de Mago para restaurar su salud."
                                hab = true
                            }

                            "Brujo" -> {
                                pj.setAtaque(pj.getAtaque() * 2)
                                texto1.text =
                                    "${pj.getNombre()} utiliza su habilidad de Brujo para duplicar su ataque."
                                hab = true
                            }

                            "Guerrero" -> {
                                pj.setDefensa(pj.getDefensa() * 2)
                                texto1.text =
                                    "${pj.getNombre()} utiliza su habilidad de Guerrero para duplicar su suerte."
                                hab = true
                            }
                        }
                    } else {
                        Toast.makeText(
                            this,
                            "Ya has activado la habilidad, no puedes volver ha hacerlo este turno",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                val ataqueMonstruo = if (evasion >= 5) 0 else monstruo.getAtaque()

                // Aplicar la defensa del personaje
                val defensaPersonaje = pj.getDefensa()
                val danoMonstruo = if (evasion >= 5) 0 else ataqueMonstruo - defensaPersonaje

                if (evasion<5) {
                    vidaPersonaje -= danoMonstruo
                }
                texto1.visibility = View.VISIBLE
                texto1.visibility = View.VISIBLE
                texto1.text = "${pj.getNombre()} tiene una suerte de ${evasion} y una defensa de ${defensaPersonaje}."
                texto2.text = "${pj.getNombre()} ha recibido ${danoMonstruo} de daño. Salud de ${pj.getNombre()}: ${vidaPersonaje}"

                if (vidaMonstruo > 0) {
                    atacar.setOnClickListener {
                        vidaMonstruo -= pj.getAtaque()
                        vidam.text = "Vida: ${vidaMonstruo} Nivel: ${monstruo.getNivel()}"
                        texto1.text =
                            "${pj.getNombre()} ataca al ${monstruo.getNombre()}. Salud del ${monstruo.getNombre()}: ${vidaMonstruo}"
                        if (vidaMonstruo <= 0) {
                            pj.setExperiencia(expGanada)
                            texto1.text =
                                "${pj.getNombre()} ha derrotado al ${monstruo.getNombre()} y gana ${expGanada} de experiencia."
                            salir = true
                            var intent = Intent(this@Enemigo, Proximamente::class.java)
                            intent.putExtra("personaje", pj)
                            intent.putExtra("mochila", moch)
                            startActivity(intent)
                        }
                        vidaPersonaje -= ataqueMonstruo
                        vidapj.text = "Vida: ${vidaPersonaje} Nivel: ${pj?.getNivel()}"
                        if (vidaPersonaje <= 0) {
                            pj.setExperiencia(expGanada)
                            salir = true
                            texto1.text = "${pj.getNombre()} has muerto."
                            var intent = Intent(this@Enemigo, MainActivity::class.java)
                            startActivity(intent)
                        }
                        texto2.text =
                            "${monstruo.getNombre()} ataca a ${pj.getNombre()}. Salud de ${pj.getNombre()}: ${vidaPersonaje}"
                        pj.calcularAtaque()
                        pj.calcularDefensa()
                    }
                    inventario.setOnClickListener {

                    }
                }
            }
        }
    }
}
