package com.example.personaje

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.util.Random

class Enemigo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enemigo)
        val huir: Button = findViewById(R.id.button2)
        val luchar: Button = findViewById(R.id.button)
        val vidapj: TextView = findViewById(R.id.textView12)
        val vidam: TextView = findViewById(R.id.textView13)
        val habilidad: Button = findViewById(R.id.button5)
        val atacar: Button = findViewById(R.id.button3)
        val inventario: Button = findViewById(R.id.button4)
        val texto1: TextView = findViewById(R.id.textView14)
        val texto2: TextView = findViewById(R.id.textView15)
        val musica: ImageButton = findViewById(R.id.imageButton2)
        val pocion: ImageButton = findViewById(R.id.imageButton3)
        val ira: ImageButton = findViewById(R.id.imageButton4)
        val textPocion: TextView = findViewById(R.id.textView)
        val textIra: TextView = findViewById(R.id.textView16)
        val pj = intent.getParcelableExtra<Personaje>("personaje")
        val moch = intent.getParcelableExtra<Mochila>("mochila")
        val dbHelper3 = DatabaseEnemigo(this)
        val arrayMonstruo = dbHelper3.getMonstruo()
        var num = 0
        var mp: MediaPlayer = MediaPlayer.create(this, R.raw.skyrim_dovahkiin)
        mp.start()
        musica.setOnClickListener {
            if(mp.isPlaying){
                mp.stop()
                musica.setImageResource(R.drawable.sin_sonido)
            }else{
                musica.setImageResource(R.drawable.herramienta_de_audio_con_altavoz)
                mp= MediaPlayer.create(this, R.raw.skyrim_dovahkiin)
                mp.start()
            }
        }
        if ((pj!!.getNivel() + 2) <= 10) {
            num = (1..(pj!!.getNivel() + 2)).random()
        } else {
            num = (1..10).random()
        }
        val progressBarLife = findViewById<ProgressBar>(R.id.progressBarLife)
        fun actualizarBarraDeVida(vidaActual: Int, vidaTotal: Int) {
            progressBarLife.max = vidaTotal
            progressBarLife.progress = vidaActual
        }
        val monstruo = arrayMonstruo[num]
        var vidaPersonaje = pj!!.getSalud()
        var vidaMonstruo = monstruo.getSalud()
        //vidapj.text = "Vida: ${vidaPersonaje} Nivel: ${pj?.getNivel()}"
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
            pocion.visibility = View.GONE
            textPocion.visibility = View.GONE
            ira.visibility = View.GONE
            textIra.visibility = View.GONE
            habilidad.visibility = View.VISIBLE
            atacar.visibility = View.VISIBLE
            inventario.visibility = View.VISIBLE
            var expGanada = monstruo.getSalud()
            var hab = false
            var evasion: Double = (1..(10)).random()+(pj.getNivel()*0.1)

            habilidad.setOnClickListener {
                pocion.visibility = View.GONE
                textPocion.visibility = View.GONE
                ira.visibility = View.GONE
                textIra.visibility = View.GONE
                if (!hab) {
                    when (pj.getClase()) {
                        "Mago" -> {
                            pj.calcularSalud()
                            vidaPersonaje = pj.getSalud()
                            texto1.text = "${pj.getNombre()} utiliza su habilidad de Mago para restaurar su salud."
                            hab = true
                        }

                        "Brujo" -> {
                            pj.setAtaque(pj.getAtaque() * 2)
                            texto1.text = "${pj.getNombre()} utiliza su habilidad de Brujo para duplicar su ataque."
                            hab = true
                        }

                        "Guerrero" -> {
                            pj.setDefensa(pj.getDefensa() * 2)
                            texto1.text = "${pj.getNombre()} utiliza su habilidad de Guerrero para duplicar su suerte."
                            hab = true
                        }
                    }
                } else {
                    Toast.makeText(this, "Ya has activado la habilidad, no puedes volver ha hacerlo este turno", Toast.LENGTH_LONG).show()
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
            texto1.text = "${pj.getNombre()} tiene una suerte de $evasion y una defensa de ${defensaPersonaje}."
            texto2.text = "${pj.getNombre()} ha recibido $danoMonstruo de daño. Salud de ${pj.getNombre()}: $vidaPersonaje"

            atacar.setOnClickListener {
                pocion.visibility = View.GONE
                textPocion.visibility = View.GONE
                ira.visibility = View.GONE
                textIra.visibility = View.GONE
                vidaMonstruo -= pj.getAtaque()
                texto1.text = "${pj.getNombre()} ataca al ${monstruo.getNombre()}. Salud del ${monstruo.getNombre()}: $vidaMonstruo"
                if (vidaMonstruo <= 0) {
                    pj.setExperiencia(expGanada)
                    texto1.text = "${pj.getNombre()} has derrotado a ${monstruo.getNombre()}."
                    val intent = Intent(this@Enemigo, MainActivity::class.java)
                    startActivity(intent)
                    mp.stop()
                }
                actualizarBarraDeVida(vidaPersonaje,pj.getSalud())
                vidam.text = "Vida: $vidaMonstruo Nivel: ${monstruo.getNivel()}"
                vidaPersonaje -= ataqueMonstruo
                if (vidaPersonaje <= 0) {
                    //vidapj.text = "Vida: 0 Nivel: ${pj?.getNivel()}"
                    actualizarBarraDeVida(0,pj.getSalud())
                    texto1.text = "${pj.getNombre()} has muerto."
                    val intent = Intent(this@Enemigo, MainActivity::class.java)
                    startActivity(intent)
                    mp.stop()
                }
                //vidapj.text = "Vida: $vidaPersonaje Nivel: ${pj?.getNivel()}"
                actualizarBarraDeVida(vidaPersonaje,pj.getSalud())
                texto2.text = "${monstruo.getNombre()} ataca a ${pj.getNombre()}. Salud de ${pj.getNombre()}: $vidaPersonaje"
                pj.calcularAtaque()
                pj.calcularDefensa()
            }
            inventario.setOnClickListener {
                if (moch!!.findObjeto(Articulo.Nombre.POCION)!=-1){
                    textPocion.text = "${moch.getContenido()[moch!!.findObjeto(Articulo.Nombre.POCION)].getUnidades()}"
                }else{
                    textPocion.text = "0"
                }
                if (moch!!.findObjeto(Articulo.Nombre.IRA)!=-1){
                    textIra.text = "${moch.getContenido()[moch!!.findObjeto(Articulo.Nombre.IRA)].getUnidades()}"
                }else{
                    textIra.text = "0"
                }
                pocion.visibility = View.VISIBLE
                textPocion.visibility = View.VISIBLE
                ira.visibility = View.VISIBLE
                textIra.visibility = View.VISIBLE
                pocion.setOnClickListener{
                    if (textPocion.text.equals("0")){
                        Toast.makeText(this, "No tienes pociones", Toast.LENGTH_LONG).show()
                    }else{
                        vidaPersonaje+=(pj.getSalud()*0.5).toInt()
                    }
                    vidaPersonaje -= ataqueMonstruo
                    if (vidaPersonaje <= 0) {
                        //vidapj.text = "Vida: 0 Nivel: ${pj?.getNivel()}"
                        actualizarBarraDeVida(0,pj.getSalud())
                        texto1.text = "${pj.getNombre()} has muerto."
                        val intent = Intent(this@Enemigo, MainActivity::class.java)
                        startActivity(intent)
                        mp.stop()
                    }
                    actualizarBarraDeVida(vidaPersonaje,pj.getSalud())
                    //vidapj.text = "Vida: $vidaPersonaje Nivel: ${pj?.getNivel()}"
                }
                ira.setOnClickListener{
                    if (textIra.text.equals("0")){
                        Toast.makeText(this, "No tienes pociones", Toast.LENGTH_LONG).show()
                    }else{
                        pj.setAtaque(pj.getAtaque() + 80)
                    }
                    vidaPersonaje -= ataqueMonstruo
                    if (vidaPersonaje <= 0) {
                        actualizarBarraDeVida(0,pj.getSalud())
                        //vidapj.text = "Vida: 0 Nivel: ${pj?.getNivel()}"
                        texto1.text = "${pj.getNombre()} has muerto."
                        val intent = Intent(this@Enemigo, MainActivity::class.java)
                        startActivity(intent)
                        mp.stop()
                    }
                    actualizarBarraDeVida(vidaPersonaje,pj.getSalud())
                    //vidapj.text = "Vida: $vidaPersonaje Nivel: ${pj?.getNivel()}"
                }
            }
        }
    }
}
