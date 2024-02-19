package com.example.personaje

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.util.Date

class Enemigo : AppCompatActivity() {
    private lateinit var pj: Personaje
    private lateinit var moch: Mochila
    private lateinit var usuarioID: String
    private lateinit var mp: MediaPlayer
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enemigo)

        mp = MediaPlayer.create(this, R.raw.skyrim_dovahkiin)
        pj = intent.getParcelableExtra<Personaje>("personaje")!!
        moch = intent.getParcelableExtra<Mochila>("mochila")!!
        usuarioID = intent.getStringExtra("uid").toString()
        val huir: Button = findViewById(R.id.button2)
        val luchar: Button = findViewById(R.id.button)
        val vidam: TextView = findViewById(R.id.textView17)
        val vidapj: TextView = findViewById(R.id.textView13)
        val habilidad: Button = findViewById(R.id.button5)
        val atacar: Button = findViewById(R.id.button3)
        val equip: Button = findViewById(R.id.button16)
        val inventario: Button = findViewById(R.id.button4)
        val texto1: TextView = findViewById(R.id.textView14)
        val texto2: TextView = findViewById(R.id.textView15)
        val musica: ImageButton = findViewById(R.id.musica)
        val pocion: ImageButton = findViewById(R.id.imageButton3)
        val ira: ImageButton = findViewById(R.id.imageButton4)
        val textPocion: TextView = findViewById(R.id.textView)
        val textIra: TextView = findViewById(R.id.textView16)
        val seleccionar = findViewById<Button>(R.id.button9)
        val insertarArma = findViewById<Button>(R.id.button13)
        val insertarProtec = findViewById<Button>(R.id.button14)
        val atras =  findViewById<Button>(R.id.button10)
        val insertar = findViewById<EditText>(R.id.editTextText2)
        val n_espada = findViewById<TextView>(R.id.textView33)
        val n_baston = findViewById<TextView>(R.id.textView34)
        val n_daga = findViewById<TextView>(R.id.textView35)
        val n_martillo = findViewById<TextView>(R.id.textView36)
        val n_garras = findViewById<TextView>(R.id.textView37)
        val n_hacha = findViewById<TextView>(R.id.textView38)
        val n_escudo = findViewById<TextView>(R.id.textView39)
        val n_armadura = findViewById<TextView>(R.id.textView40)
        val mochilaCon = findViewById<TextView>(R.id.textView43)
        val textI = findViewById<TextView>(R.id.textView41)
        val contenidoMoch = intent.getParcelableArrayListExtra<Articulo>("contenido")
        val dbHelper3 = DatabaseEnemigo(this)
//        val calendario = Calendario(this)
        val fecha = Date()
        val lvl = pj!!.getNivel()
        val dbPelea = DatabasePelea (this)
        val arrayMonstruo = dbHelper3.getMonstruo()
        var num = 0
        var pos=0
        val cImg = obtImg()
        val imgP: ImageView = findViewById(R.id.imageView3)
        val toolbar: Toolbar = findViewById(R.id.toolbarEjemplo)
        cImg.obtenerImagen3(imgP,pj!!.getRaza(),pj!!.getClase(),pj!!.getEstadoVital())

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Enemigo"

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
            intent.putExtra("uid", usuarioID)
            if (moch != null) {
                intent.putParcelableArrayListExtra("contenido", moch.getContenido())
            }
            if(mp.isPlaying){
                mp.stop()
            }
            startActivity(intent)
        }
        var cont=0
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
            equip.visibility = View.VISIBLE
            var expGanada = monstruo.getSalud()
            var hab = false
            var evasion: Double = (1..(10)).random()+(pj.getNivel()*0.1)
            var ataquePj=pj.getAtaque()
            var defensaPj=pj.getDefensa()

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
                            ataquePj=pj.getAtaque() * 2
                            texto1.text = "${pj.getNombre()} utiliza su habilidad de Brujo para duplicar su ataque."
                            hab = true
                        }

                        "Guerrero" -> {
                            defensaPj=pj.getDefensa() * 2
                            texto1.text = "${pj.getNombre()} utiliza su habilidad de Guerrero para duplicar su defensa."
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
                Thread.sleep(333)
                texto1.text = "${pj.getNombre()} ataca al ${monstruo.getNombre()}. Salud del ${monstruo.getNombre()}: $vidaMonstruo"
                if (vidaMonstruo <= 0) {
                    actualizarBarraDeVidaM(0,monstruo.getSalud())
                    pj.setExperiencia(expGanada)
                    dbPelea.insertarPelea("",monstruo.getNombre(),"ganado",fecha)
//                    calendario.crearEvento("Pelea","Has ganado contra ${monstruo.getNombre()}")
                    if(lvl!=pj.getNivel()){
//                        calendario.crearEvento("Nivel","Has subido de nivel, lvl:${pj.getNivel()}")
                    }
                    texto1.text = "${pj.getNombre()} has derrotado a ${monstruo.getNombre()}."
                    pj.setSalud(vidaPersonaje)
                    if(mp.isPlaying){
                        mp.stop()
                    }
                    val intent = Intent(this@Enemigo, Aventura::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    intent.putExtra("uid", usuarioID)
                    if (moch != null) {
                        intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                    }
                    startActivity(intent)
                    mp.stop()
                }
                actualizarBarraDeVidaP(vidaPersonaje,pj.getSalud())
                Thread.sleep(333)
                actualizarBarraDeVidaM(vidaMonstruo,monstruo.getSalud())
                vidaPersonaje -= ataqueMonstruo
                Thread.sleep(333)
                if (vidaPersonaje <= 0) {
                    actualizarBarraDeVidaP(0,pj.getSalud())
                    dbPelea.insertarPelea("",monstruo.getNombre(),"perdido",fecha)
//                    calendario.crearEvento("Pelea","Has perdido contra ${monstruo.getNombre()}")
                    texto1.text = "${pj.getNombre()} has muerto."
                    if(mp.isPlaying){
                        mp.stop()
                    }
                    val intent = Intent(this@Enemigo, Aventura::class.java)
                    intent.putExtra("personaje", pj)
                    intent.putExtra("mochila", moch)
                    intent.putExtra("uid", usuarioID)
                    if (moch != null) {
                        intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                    }
                    startActivity(intent)
                    mp.stop()
                }
                actualizarBarraDeVidaP(vidaPersonaje,pj.getSalud())
                texto2.text = "${monstruo.getNombre()} ataca a ${pj.getNombre()}. Salud de ${pj.getNombre()}: $vidaPersonaje"

                if(hab==true&&cont==0){
                    ataquePj=pj.getAtaque()
                    defensaPj=pj.getDefensa()
                    cont++
                }
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
                        moch.quitarArticulo(moch.getContenido()[moch!!.findObjeto(Articulo.Nombre.POCION)],1)
                    }
                    vidaPersonaje -= ataqueMonstruo
                    if (vidaPersonaje <= 0) {
                        actualizarBarraDeVidaP(0,pj.getSalud())
                        dbPelea.insertarPelea("",monstruo.getNombre(),"perdido",fecha)
//                        calendario.crearEvento("Pelea","Has perdido contra ${monstruo.getNombre()}")
                        texto1.text = "${pj.getNombre()} has muerto."
                        if(mp.isPlaying){
                            mp.stop()
                        }
                        val intent = Intent(this@Enemigo, Aventura::class.java)
                        intent.putExtra("personaje", pj)
                        intent.putExtra("mochila", moch)
                        intent.putExtra("uid", usuarioID)
                        if (moch != null) {
                            intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                        }
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
                        moch.quitarArticulo(moch.getContenido()[moch!!.findObjeto(Articulo.Nombre.IRA)],1)
                    }
                    vidaPersonaje -= ataqueMonstruo
                    if (vidaPersonaje <= 0) {
                        actualizarBarraDeVidaP(0,pj.getSalud())
                        dbPelea.insertarPelea("",monstruo.getNombre(),"perdido",fecha)
//                        calendario.crearEvento("Pelea","Has perdido contra ${monstruo.getNombre()}")
                        texto1.text = "${pj.getNombre()} has muerto."
                        if(mp.isPlaying){
                            mp.stop()
                        }
                        val intent = Intent(this@Enemigo, Aventura::class.java)
                        intent.putExtra("personaje", pj)
                        intent.putExtra("mochila", moch)
                        intent.putExtra("uid", usuarioID)
                        if (moch != null) {
                            intent.putParcelableArrayListExtra("contenido", moch.getContenido())
                        }
                        startActivity(intent)
                        mp.stop()
                    }
                    actualizarBarraDeVidaP(vidaPersonaje,pj.getSalud())
                }
            }
            equip.setOnClickListener {
                mochilaCon.visibility=View.VISIBLE
                mochilaCon.text = moch.toString()
                insertarArma.visibility=View.VISIBLE
                insertarProtec.visibility=View.VISIBLE
                insertarArma.setOnClickListener {
                    mochilaCon.visibility=View.GONE
                    insertarArma.visibility= View.GONE
                    insertarProtec.visibility= View.GONE
                    atras.visibility=View.VISIBLE
                    insertar.visibility=View.VISIBLE
                    textI.visibility=View.VISIBLE
                    seleccionar.visibility=View.VISIBLE
                    seleccionar.setOnClickListener {
                        val art=NombreArticulo()
                        val e=equipar()
                        if(moch.findObjeto(art.nArti(insertar.text.toString()))!=-1){
                            val arA=e.fequi(art.nArti(insertar.text.toString()),pj)
                            ataquePj+=arA
                        }else{
                            Toast.makeText(this, "No tienes ese articulo", Toast.LENGTH_LONG).show()
                        }
                    }
                    atras.setOnClickListener {
                        atras.visibility=View.GONE
                        seleccionar.visibility=View.GONE
                        n_espada.visibility=View.GONE
                        n_baston.visibility=View.GONE
                        n_daga.visibility=View.GONE
                        n_martillo.visibility=View.GONE
                        n_garras.visibility=View.GONE
                        n_hacha.visibility=View.GONE
                        n_escudo.visibility= View.GONE
                        n_armadura.visibility= View.GONE
                        insertarArma.visibility= View.VISIBLE
                        insertarProtec.visibility= View.VISIBLE
                        insertar.visibility= View.GONE
                        textI.visibility=View.GONE
                        mochilaCon.visibility=View.VISIBLE
                    }
                    n_espada.visibility=View.VISIBLE
                    n_baston.visibility=View.VISIBLE
                    n_daga.visibility=View.VISIBLE
                    n_martillo.visibility=View.VISIBLE
                    n_garras.visibility=View.VISIBLE
                    n_hacha.visibility=View.VISIBLE
                    n_escudo.visibility= View.GONE
                    n_armadura.visibility= View.GONE
                    if(moch.findObjeto(Articulo.Nombre.BASTON)!=-1){
                        val baston=moch.getContenido()[moch.findObjeto(Articulo.Nombre.BASTON)]
                        n_baston.text =baston.toString()
                    }else{
                        val baston="No tienes baston"
                        n_baston.text = baston
                    }
                    if(moch.findObjeto(Articulo.Nombre.ESPADA)!=-1){
                        val espada=moch.getContenido()[moch.findObjeto(Articulo.Nombre.ESPADA)]
                        n_espada.text =espada.toString()
                    }else{
                        val espada="No tienes espada"
                        n_espada.text = espada
                    }
                    if(moch.findObjeto(Articulo.Nombre.DAGA)!=-1){
                        val daga=moch.getContenido()[moch.findObjeto(Articulo.Nombre.DAGA)]
                        n_daga.text =daga.toString()
                    }else{
                        val daga="No tienes daga"
                        n_daga.text = daga
                    }
                    if(moch.findObjeto(Articulo.Nombre.MARTILLO)!=-1){
                        val martillo=moch.getContenido()[moch.findObjeto(Articulo.Nombre.MARTILLO)]
                        n_martillo.text =martillo.toString()
                    }else{
                        val martillo="No tienes martillo"
                        n_martillo.text =martillo
                    }
                    if(moch.findObjeto(Articulo.Nombre.GARRAS)!=-1){
                        val garras=moch.getContenido()[moch.findObjeto(Articulo.Nombre.GARRAS)]
                        n_garras.text=garras.toString()
                    }else{
                        val garras="No tienes garras"
                        n_garras.text=garras
                    }
                    if(moch.findObjeto(Articulo.Nombre.HACHA)!=-1){
                        val hacha=moch.getContenido()[moch.findObjeto(Articulo.Nombre.HACHA)]
                        n_hacha.text=hacha.toString()
                    }else{
                        val hacha="No tienes hacha"
                        n_hacha.text=hacha
                    }
                }
                insertarProtec.setOnClickListener {
                    mochilaCon.visibility=View.GONE
                    insertarArma.visibility= View.GONE
                    insertarProtec.visibility= View.GONE
                    atras.visibility=View.VISIBLE
                    insertar.visibility=View.VISIBLE
                    textI.visibility=View.VISIBLE
                    seleccionar.visibility=View.VISIBLE
                    seleccionar.setOnClickListener {
                        val art=NombreArticulo()
                        val e=equipar()
                        if(moch.findObjeto(art.nArti(insertar.text.toString()))!=-1){
                            val arP=e.fequi(art.nArti(insertar.text.toString()),pj)
                            defensaPj+=arP
                        }else{
                            Toast.makeText(this, "No tienes ese articulo", Toast.LENGTH_LONG).show()
                        }
                    }
                    atras.setOnClickListener {
                        atras.visibility=View.GONE
                        seleccionar.visibility=View.GONE
                        n_espada.visibility=View.GONE
                        n_baston.visibility=View.GONE
                        n_daga.visibility=View.GONE
                        n_martillo.visibility=View.GONE
                        n_garras.visibility=View.GONE
                        n_hacha.visibility=View.GONE
                        n_escudo.visibility= View.GONE
                        n_armadura.visibility= View.GONE
                        insertarArma.visibility= View.VISIBLE
                        insertarProtec.visibility= View.VISIBLE
                        insertar.visibility= View.GONE
                        textI.visibility=View.GONE
                        mochilaCon.visibility=View.VISIBLE
                    }
                    n_espada.visibility=View.GONE
                    n_baston.visibility=View.GONE
                    n_daga.visibility=View.GONE
                    n_martillo.visibility=View.GONE
                    n_garras.visibility=View.GONE
                    n_hacha.visibility=View.GONE
                    n_escudo.visibility= View.VISIBLE
                    n_armadura.visibility= View.VISIBLE
                    if(moch.findObjeto(Articulo.Nombre.ESCUDO)!=-1){
                        val escudo=moch.getContenido()[moch.findObjeto(Articulo.Nombre.ESCUDO)]
                        n_escudo.text=escudo.toString()
                    }else{
                        val escudo="No tienes escudo"
                        n_escudo.text=escudo
                    }
                    if(moch.findObjeto(Articulo.Nombre.ARMADURA)!=-1){
                        val armadura=moch.getContenido()[moch.findObjeto(Articulo.Nombre.ARMADURA)]
                        n_armadura.text=armadura.toString()
                    }else{
                        val armadura="No tienes armadura"
                        n_armadura.text=armadura
                    }
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