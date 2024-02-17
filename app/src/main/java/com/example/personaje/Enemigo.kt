package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class Enemigo : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enemigo)
        val huir: Button = findViewById(R.id.button2)
        val luchar: Button = findViewById(R.id.button)
        val vidam: TextView = findViewById(R.id.textView17)
        val vidapj: TextView = findViewById(R.id.textView13)
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
        val contenidoMoch = intent.getParcelableArrayListExtra<Articulo>("contenido")
        val dbHelper3 = DatabaseEnemigo(this)
//        val calendario = Calendario(this)
//        val inicio = System.currentTimeMillis() * 60 * 60 * 1000
//        val fin = inicio + 1 * 60 * 60 * 1000
        val lvl = pj!!.getNivel()
        val dbPelea = DatabasePelea (this)
        val arrayMonstruo = dbHelper3.getMonstruo()
        var num = 0
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.skyrim_dovahkiin)
        var pos=0
        val cImg = obtImg()
        val imgP: ImageView = findViewById(R.id.imageView3)
        cImg.obtenerImagen3(imgP,pj!!.getRaza(),pj!!.getClase(),pj!!.getEstadoVital())

        if (contenidoMoch != null) {
            contenidoMoch.forEach {
                if (moch != null) {
                    moch.actualizarMochila(it)
                }
            }
        }

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
        if ((pj!!.getNivel()) <= 10) {
            num = (pj.getNivel()..(pj!!.getNivel() + 1)).random()
        } else {
            num = (pj.getNivel()-2..10).random()
        }
        val monstruo = arrayMonstruo[num-1]
        var vidaPersonaje = pj!!.getSalud()
        var vidaMonstruo = monstruo.getSalud()
        val progressBarLifeM = findViewById<ProgressBar>(R.id.progressBarLifeM)
        fun actualizarBarraDeVidaM(vidaActual: Int, vidaTotal: Int) {
            progressBarLifeM.max = vidaTotal
            progressBarLifeM.progress = vidaActual
            vidam.text= "$vidaActual/$vidaTotal lvl:${monstruo.getNivel()}"
        }
        val progressBarLifeP = findViewById<ProgressBar>(R.id.progressBarLife)
        fun actualizarBarraDeVidaP(vidaActual: Int, vidaTotal: Int) {
            progressBarLifeP.max = vidaTotal
            progressBarLifeP.progress = vidaActual
            vidapj.text= "$vidaActual/$vidaTotal lvl:${pj.getNivel()}"
        }
        actualizarBarraDeVidaM(vidaMonstruo,monstruo.getSalud())
        actualizarBarraDeVidaP(vidaPersonaje,pj.getSalud())
        huir.setOnClickListener {
            val intent = Intent(this@Enemigo, Aventura::class.java)
            intent.putExtra("personaje", pj)
            intent.putExtra("mochila", moch)
            if(mp.isPlaying){
                mp.stop()
            }
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
            val ataqueMonstruo = if (evasion >= 8) 0 else monstruo.getAtaque()

            // Aplicar la defensa del personaje
            val defensaPersonaje = pj.getDefensa()
            val danoMonstruo = if (evasion >= 8) 0 else ataqueMonstruo - defensaPersonaje

            if (evasion<8) {

                vidaPersonaje -= danoMonstruo
            }
            texto1.visibility = View.VISIBLE
            texto2.visibility = View.VISIBLE
            texto1.text = "${pj.getNombre()} tiene una suerte de $evasion y una defensa de ${defensaPersonaje}."
            texto2.text = "${pj.getNombre()} ha recibido $danoMonstruo de daño. Salud de ${pj.getNombre()}: $vidaPersonaje"

            atacar.setOnClickListener {
                pocion.visibility = View.GONE
                textPocion.visibility = View.GONE
                ira.visibility = View.GONE
                textIra.visibility = View.GONE
                vidaMonstruo -= pj.getAtaque()
                Thread.sleep(1000)
                texto1.text = "${pj.getNombre()} ataca al ${monstruo.getNombre()}. Salud del ${monstruo.getNombre()}: $vidaMonstruo"
                if (vidaMonstruo <= 0) {
                    actualizarBarraDeVidaM(0,monstruo.getSalud())
                    pj.setExperiencia(expGanada)
                    dbPelea.insertarPelea("",monstruo.getNombre(),"ganado")
//                    calendario.crearEvento("Pelea","Has ganado contra ${monstruo.getNombre()}",inicio, fin)
                    if(lvl!=pj.getNivel()){
//                        calendario.crearEvento("Nivel","Has subido de nivel, lvl:${pj.getNivel()}",inicio, fin)
                    }
                    texto1.text = "${pj.getNombre()} has derrotado a ${monstruo.getNombre()}."
                    if(mp.isPlaying){
                        mp.stop()
                    }
                    val intent = Intent(this@Enemigo, Aventura::class.java)
                    startActivity(intent)
                    mp.stop()
                }
                actualizarBarraDeVidaP(vidaPersonaje,pj.getSalud())
                actualizarBarraDeVidaM(vidaMonstruo,monstruo.getSalud())
                vidaPersonaje -= ataqueMonstruo
                Thread.sleep(1000)
                if (vidaPersonaje <= 0) {
                    actualizarBarraDeVidaP(0,pj.getSalud())
                    dbPelea.insertarPelea("",monstruo.getNombre(),"perdido")
//                    calendario.crearEvento("Pelea","Has perdido contra ${monstruo.getNombre()}",inicio, fin)
                    texto1.text = "${pj.getNombre()} has muerto."
                    if(mp.isPlaying){
                        mp.stop()
                    }
                    val intent = Intent(this@Enemigo, MainActivity::class.java)
                    startActivity(intent)
                    mp.stop()
                }
                actualizarBarraDeVidaP(vidaPersonaje,pj.getSalud())
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
                        actualizarBarraDeVidaP(0,pj.getSalud())
                        dbPelea.insertarPelea("",monstruo.getNombre(),"perdido")
//                        calendario.crearEvento("Pelea","Has perdido contra ${monstruo.getNombre()}",inicio, fin)
                        texto1.text = "${pj.getNombre()} has muerto."
                        if(mp.isPlaying){
                            mp.stop()
                        }
                        val intent = Intent(this@Enemigo, MainActivity::class.java)
                        startActivity(intent)
                        mp.stop()
                    }
                    actualizarBarraDeVidaP(vidaPersonaje,pj.getSalud())
                }
                ira.setOnClickListener{
                    if (textIra.text.equals("0")){
                        Toast.makeText(this, "No tienes pociones", Toast.LENGTH_LONG).show()
                    }else{
                        pj.setAtaque(pj.getAtaque() + 80)
                    }
                    vidaPersonaje -= ataqueMonstruo
                    if (vidaPersonaje <= 0) {
                        actualizarBarraDeVidaP(0,pj.getSalud())
                        dbPelea.insertarPelea("",monstruo.getNombre(),"perdido")
//                        calendario.crearEvento("Pelea","Has perdido contra ${monstruo.getNombre()}",inicio, fin)
                        texto1.text = "${pj.getNombre()} has muerto."
                        if(mp.isPlaying){
                            mp.stop()
                        }
                        val intent = Intent(this@Enemigo, MainActivity::class.java)
                        intent.putExtra("personaje", pj)
                        intent.putExtra("mochila", moch)
                        if (moch != null) {
                            intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                        }
                        startActivity(intent)
                        mp.stop()
                    }
                    actualizarBarraDeVidaP(vidaPersonaje,pj.getSalud())
                }
            }
        }
    }
}