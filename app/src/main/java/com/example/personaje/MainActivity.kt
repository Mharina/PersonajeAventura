package com.example.personaje

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.speech.tts.TextToSpeech
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var textToSpeech:TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerRaza: Spinner = findViewById(R.id.spinnerRaza)
        val spinnerClase: Spinner = findViewById(R.id.spinnerClase)
        val spinnerEstadoVital: Spinner = findViewById(R.id.spinnerEstadoVital)
        val foto: ImageView = findViewById(R.id.imageView)
        val musica: ImageButton = findViewById(R.id.imageButton2)
        val dbHelperA = DatabaseHelper (this)
        val dbHelperMercader = DatabaseHelperMercader (this)
        val dbHelperM = DatabaseEnemigo (this)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val mp: MediaPlayer = MediaPlayer.create(this, R.raw.skyrim_before_the_storm)
        val nombre: EditText = findViewById(R.id.editTextText)

        dbHelperMercader.recreaTabla()
        dbHelperA.recreaTabla()
        dbHelperM.recreaTabla()
        setSupportActionBar(toolbar)

        val opcionesRaza: Array<String> = resources.getStringArray(R.array.raza)
        val opcionesClase: Array<String> = resources.getStringArray(R.array.clase)
        val opcionesEstadoVital: Array<String> = resources.getStringArray(R.array.estadoVital)

        val adapterRaza: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.color_spinner_fuera, opcionesRaza)
        spinnerRaza.adapter = adapterRaza
        val adapterClase: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.color_spinner_fuera, opcionesClase)
        spinnerClase.adapter = adapterClase
        val adapterEstadoVital: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.color_spinner_fuera, opcionesEstadoVital)
        spinnerEstadoVital.adapter = adapterEstadoVital

        adapterRaza.setDropDownViewResource(R.layout.spinner_dropdown_item)
        adapterClase.setDropDownViewResource(R.layout.spinner_dropdown_item)
        adapterEstadoVital.setDropDownViewResource(R.layout.spinner_dropdown_item)

        spinnerRaza.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val seleccionRaza: String = parent?.getItemAtPosition(position) as String
                val seleccionClase: String = spinnerClase.selectedItem as String
                val seleccionEstadoVital: String = spinnerEstadoVital.selectedItem as String
                val imagen = obtImg()
                imagen.obtenerImagen3(foto, seleccionRaza, seleccionClase, seleccionEstadoVital)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spinnerClase.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val seleccionClase: String = parent?.getItemAtPosition(position) as String
                val seleccionRaza: String = spinnerRaza.selectedItem as String
                val seleccionEstadoVital: String = spinnerEstadoVital.selectedItem as String
                val imagen = obtImg()
                imagen.obtenerImagen3(foto, seleccionRaza, seleccionClase, seleccionEstadoVital)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spinnerEstadoVital.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val seleccionEstadoVital: String = parent?.getItemAtPosition(position) as String
                val seleccionRaza: String = spinnerRaza.selectedItem as String
                val seleccionClase: String = spinnerClase.selectedItem as String
                val imagen = obtImg()
                imagen.obtenerImagen3(foto, seleccionRaza, seleccionClase, seleccionEstadoVital)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
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

        textToSpeech = TextToSpeech(this){status->
            if (status ==TextToSpeech.SUCCESS){
                val result = textToSpeech.setLanguage(Locale.getDefault())
                if (result == TextToSpeech.LANG_MISSING_DATA||result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(this,"lenguaje no soportado",Toast.LENGTH_LONG).show()
                }
            }
        }

        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            if (nombre.text.isBlank()) {
                nombre.setError("El campo nombre es necesario")
            } else {
                textToSpeech.speak(
                    nombre.text.toString().trim(),
                    TextToSpeech.QUEUE_FLUSH,
                    null,
                    null
                )
                val intent = Intent(this@MainActivity, PersonajeMostrar::class.java)
                val personaje = Personaje(
                    nombre.text.toString(),
                    spinnerEstadoVital.selectedItem as String,
                    spinnerRaza.selectedItem as String,
                    spinnerClase.selectedItem as String
                )
                intent.putExtra("mochila", personaje.getMochila())
                intent.putExtra("personaje", personaje)
                intent.putExtra("imagen_id", foto.drawable.toString())
                if(mp.isPlaying){
                    mp.stop()
                }
                startActivity(intent)
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
                val intent = Intent(this@MainActivity, PersonajeMostrar::class.java)
                startActivity(intent)
                Toast.makeText(this,"personaje", Toast.LENGTH_LONG).show()
            }
            R.id.mochila->{
                val intent = Intent(this@MainActivity, PersonajeMostrar::class.java)
                startActivity(intent)
                Toast.makeText(this,"mochila", Toast.LENGTH_LONG).show()
            }
            R.id.libro->{
                val intent = Intent(this@MainActivity, PersonajeMostrar::class.java)
                startActivity(intent)
                Toast.makeText(this,"libro", Toast.LENGTH_LONG).show()
            }
            R.id.guardar->{

                Toast.makeText(this,"guardar", Toast.LENGTH_LONG).show()
            }
            R.id.guardar_salir->{
                val intent = Intent(this@MainActivity, PersonajeMostrar::class.java)
                startActivity(intent)
                Toast.makeText(this,"guardar y salir", Toast.LENGTH_LONG).show()
            }
            R.id.salir->{
                val intent = Intent(this@MainActivity, PersonajeMostrar::class.java)
                startActivity(intent)
                Toast.makeText(this,"salir", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
class Mochila(private var pesoMochila: Int)
    :Parcelable{
    private var contenido=ArrayList<Articulo>()

    constructor(parcel: Parcel) : this(parcel.readInt()) {

    }
    fun getPesoMochila():Int{
        return pesoMochila
    }

    fun actualizarMochila(articulo:Articulo){
        contenido.add(articulo)
    }
    fun addArticulo(articulo: Articulo, unidad: Int) {
        if(this.findObjeto(articulo.getNombre())==-1){
            if (articulo.getPeso()*unidad <= pesoMochila) {
                when (articulo.getTipoArticulo()) {
                    Articulo.TipoArticulo.ARMA -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.BASTON, Articulo.Nombre.ESPADA, Articulo.Nombre.DAGA,
                            Articulo.Nombre.MARTILLO, Articulo.Nombre.GARRAS, Articulo.Nombre.HACHA -> {
                                articulo.setUnidades(unidad)
                                contenido.add(articulo)
                                this.pesoMochila -= articulo.getPeso()*unidad
                            }
                            else -> println("Nombre del artículo no válido para el tipo ARMA.")
                        }
                    }
                    Articulo.TipoArticulo.OBJETO -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.POCION, Articulo.Nombre.IRA -> {
                                articulo.setUnidades(unidad)
                                contenido.add(articulo)
                                this.pesoMochila -= articulo.getPeso()*unidad
                            }
                            else -> println("Nombre del artículo no válido para el tipo OBJETO.")
                        }
                    }
                    Articulo.TipoArticulo.PROTECCION -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
                                articulo.setUnidades(unidad)
                                contenido.add(articulo)
                                this.pesoMochila -= articulo.getPeso()*unidad
                            }
                            else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                        }
                    }
                    Articulo.TipoArticulo.ORO->{
                        when (articulo.getNombre()){
                            Articulo.Nombre.MONEDA->{
                                articulo.setUnidades(unidad)
                                contenido.add(articulo)
                                this.pesoMochila -= articulo.getPeso()*unidad
                            }
                            else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                        }
                    }
                }
            } else {
                println("El peso del artículo excede el límite de la mochila.")
            }
        }else{
            if (articulo.getPeso()*unidad <= pesoMochila) {
                when (articulo.getTipoArticulo()) {
                    Articulo.TipoArticulo.ARMA -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.BASTON, Articulo.Nombre.ESPADA, Articulo.Nombre.DAGA,
                            Articulo.Nombre.MARTILLO, Articulo.Nombre.GARRAS, Articulo.Nombre.HACHA -> {
                                var cont = contenido[this.findObjeto(articulo.getNombre())].getUnidades()
                                contenido[this.findObjeto(articulo.getNombre())].setUnidades(cont+unidad)
                                this.pesoMochila -= articulo.getPeso()*unidad
                            }
                            else -> println("Nombre del artículo no válido para el tipo ARMA.")
                        }
                    }
                    Articulo.TipoArticulo.OBJETO -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.POCION, Articulo.Nombre.IRA -> {
                                var cont = contenido[this.findObjeto(articulo.getNombre())].getUnidades()
                                contenido[this.findObjeto(articulo.getNombre())].setUnidades(cont+unidad)
                                this.pesoMochila -= articulo.getPeso()*unidad
                            }
                            else -> println("Nombre del artículo no válido para el tipo OBJETO.")
                        }
                    }
                    Articulo.TipoArticulo.PROTECCION -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
                                var cont = contenido[this.findObjeto(articulo.getNombre())].getUnidades()
                                contenido[this.findObjeto(articulo.getNombre())].setUnidades(cont+unidad)
                                this.pesoMochila -= articulo.getPeso()*unidad
                            }
                            else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                        }
                    }
                    Articulo.TipoArticulo.ORO->{
                        when (articulo.getNombre()){
                            Articulo.Nombre.MONEDA->{
                                var cont = contenido[this.findObjeto(articulo.getNombre())].getUnidades()
                                contenido[this.findObjeto(articulo.getNombre())].setUnidades(cont+unidad)
                                this.pesoMochila -= articulo.getPeso()*unidad
                            }
                            else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                        }
                    }
                }
            } else {
                println("El peso del artículo excede el límite de la mochila.")
            }
        }
    }
    fun quitarArticulo(articulo: Articulo, unidad: Int){
        if(this.findObjeto(articulo.getNombre())!=-1){
            if(articulo.getUnidades()-unidad<=0) {
                when (articulo.getTipoArticulo()) {
                    Articulo.TipoArticulo.ARMA -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.BASTON, Articulo.Nombre.ESPADA, Articulo.Nombre.DAGA,
                            Articulo.Nombre.MARTILLO, Articulo.Nombre.GARRAS, Articulo.Nombre.HACHA -> {
                                articulo.setUnidades(unidad)
                                contenido.add(articulo)
                                this.pesoMochila += articulo.getPeso() * unidad
                                var cont = contenido[this.findObjeto(Articulo.Nombre.MONEDA)].getUnidades()
                                contenido[this.findObjeto(Articulo.Nombre.MONEDA)].setUnidades(cont+((articulo.getValor()/15)*articulo.getUnidades()))
                            }

                            else -> println("Nombre del artículo no válido para el tipo ARMA.")
                        }
                    }

                    Articulo.TipoArticulo.OBJETO -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.POCION, Articulo.Nombre.IRA -> {
                                articulo.setUnidades(unidad)
                                contenido.add(articulo)
                                this.pesoMochila += articulo.getPeso() * unidad
                                var cont = contenido[this.findObjeto(Articulo.Nombre.MONEDA)].getUnidades()
                                contenido[this.findObjeto(Articulo.Nombre.MONEDA)].setUnidades(cont+((articulo.getValor()/15)*articulo.getUnidades()))
                            }

                            else -> println("Nombre del artículo no válido para el tipo OBJETO.")
                        }
                    }

                    Articulo.TipoArticulo.PROTECCION -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
                                articulo.setUnidades(unidad)
                                contenido.add(articulo)
                                this.pesoMochila += articulo.getPeso() * unidad
                                var cont = contenido[this.findObjeto(Articulo.Nombre.MONEDA)].getUnidades()
                                contenido[this.findObjeto(Articulo.Nombre.MONEDA)].setUnidades(cont+((articulo.getValor()/15)*articulo.getUnidades()))
                            }

                            else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                        }
                    }

                    Articulo.TipoArticulo.ORO -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.MONEDA -> {
                                articulo.setUnidades(unidad)
                                this.pesoMochila += articulo.getPeso() * unidad
                                var cont = contenido[this.findObjeto(Articulo.Nombre.MONEDA)].getUnidades()
                                contenido[this.findObjeto(Articulo.Nombre.MONEDA)].setUnidades(cont+((articulo.getValor()/15)*articulo.getUnidades()))
                            }

                            else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                        }
                    }
                }
            }else {
                when (articulo.getTipoArticulo()) {
                    Articulo.TipoArticulo.ARMA -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.BASTON, Articulo.Nombre.ESPADA, Articulo.Nombre.DAGA,
                            Articulo.Nombre.MARTILLO, Articulo.Nombre.GARRAS, Articulo.Nombre.HACHA -> {
                                this.pesoMochila += articulo.getPeso() * unidad
                                var cont = contenido[this.findObjeto(Articulo.Nombre.MONEDA)].getUnidades()
                                contenido[this.findObjeto(Articulo.Nombre.MONEDA)].setUnidades(cont+((articulo.getValor()/15)*articulo.getUnidades()))
                                contenido.remove(articulo)
                            }

                            else -> println("Nombre del artículo no válido para el tipo ARMA.")
                        }
                    }

                    Articulo.TipoArticulo.OBJETO -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.POCION, Articulo.Nombre.IRA -> {
                                this.pesoMochila += articulo.getPeso() * unidad
                                var cont = contenido[this.findObjeto(Articulo.Nombre.MONEDA)].getUnidades()
                                contenido[this.findObjeto(Articulo.Nombre.MONEDA)].setUnidades(cont+((articulo.getValor()/15)*articulo.getUnidades()))
                                contenido.remove(articulo)
                            }

                            else -> println("Nombre del artículo no válido para el tipo OBJETO.")
                        }
                    }

                    Articulo.TipoArticulo.PROTECCION -> {
                        when (articulo.getNombre()) {
                            Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
                                this.pesoMochila += articulo.getPeso() * unidad
                                var cont = contenido[this.findObjeto(Articulo.Nombre.MONEDA)].getUnidades()
                                contenido[this.findObjeto(Articulo.Nombre.MONEDA)].setUnidades(cont+((articulo.getValor()/15)*articulo.getUnidades()))
                                contenido.remove(articulo)
                            }

                            else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                        }
                    }
                    Articulo.TipoArticulo.ORO->{
                        when (articulo.getNombre()){
                            Articulo.Nombre.MONEDA->{
                                println("No se pueden vender las monedas")
                            }
                            else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                        }
                    }
                }
            }
        }else {
            println("No se encuentra el articulo en la mochila")
        }
    }
    fun getContenido(): ArrayList<Articulo> {
        return contenido
    }
    fun findObjeto(nombre: Articulo.Nombre): Int {
        return contenido.indexOfFirst { it.getNombre() == nombre }
    }
    override fun toString(): String {
        return if (contenido.isEmpty()) {
            "Mochila vacía"
        } else {
            "Artículos en la mochila:\n ${contenido.joinToString("\n")}"
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(pesoMochila)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Mochila> {
        override fun createFromParcel(parcel: Parcel): Mochila {
            return Mochila(parcel)
        }

        override fun newArray(size: Int): Array<Mochila?> {
            return arrayOfNulls(size)
        }
    }
}

data class Articulo(
    private var id: Int,
    private var tipoArticulo: TipoArticulo,
    private var nombre: Nombre,
    private var peso: Int,
    private var img: String,
    private var unidades: Int,
    private var valor: Int
): Parcelable {
    enum class TipoArticulo { ARMA, OBJETO, PROTECCION, ORO }
    enum class Nombre { BASTON, ESPADA, DAGA, HACHA, MARTILLO, GARRAS, POCION, IRA, ESCUDO, ARMADURA, MONEDA }
    override fun describeContents(): Int {
        return 0
    }
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        TipoArticulo.valueOf(parcel.readString()!!),
        Nombre.valueOf(parcel.readString()!!),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt()
    ) {

    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(tipoArticulo.toString())
        parcel.writeString(nombre.toString())
        parcel.writeInt(peso)
        parcel.writeString(img)
        parcel.writeInt(unidades)
        parcel.writeInt(valor)
    }
    override fun toString(): String {
        //return "[ID: $id, Tipo:$tipoArticulo, Nombre:$nombre, Peso:$peso, Unidades:$unidades, Valor:$valor]"
        return "[ID: $id, Nombre: $nombre, Peso: $peso, Uds: $unidades, Valor: $valor]"
    }

    fun getPeso(): Int {
        return peso
    }
    fun getNombre(): Nombre {
        return nombre
    }
    fun getTipoArticulo(): TipoArticulo {
        return tipoArticulo
    }
    fun getAumentoAtaque(): Int {
        return when (nombre) {
            Nombre.BASTON -> 10
            Nombre.ESPADA -> 20
            Nombre.DAGA -> 15
            Nombre.HACHA -> 18
            Nombre.MARTILLO -> 25
            Nombre.GARRAS -> 30
            Nombre.IRA -> 80
            else -> 0 // Para otros tipos de armas no especificados
        }
    }
    fun getAumentoDefensa(): Int {
        return when (nombre) {
            Nombre.ESCUDO -> 10
            Nombre.ARMADURA -> 20
            else -> 0 // Para otros tipos de protecciones no especificados
        }
    }
    fun getAumentoVida(): Int {
        return when (nombre) {
            Nombre.POCION -> 100
            else -> 0 // Para otros tipos de objetos no especificados
        }
    }

    fun getImg(): String {
        return img
    }

    fun setImg(img: String) {
        this.img = img
    }
    fun getUnidades(): Int {
        return unidades
    }

    fun setUnidades(unidades: Int) {
        this.unidades = unidades
    }
    fun getValor(): Int {
        return valor
    }

    fun setValor(valor: Int) {
        this.valor = valor
    }

    fun getId(): Int{
        return id;
    }

    companion object CREATOR : Parcelable.Creator<Articulo> {
        override fun createFromParcel(parcel: Parcel): Articulo {
            return Articulo(parcel)
        }

        override fun newArray(size: Int): Array<Articulo?> {
            return arrayOfNulls(size)
        }
    }

}
data class Personaje(
    private var nombre: String,
    private var estadoVital: String,
    private var raza: String,
    private var clase: String,

    ): Parcelable {
    private val mochila = Mochila(44)
    private var salud: Int = 0
    private var ataque: Int = 0
    private var experiencia: Int
    private var nivel: Int
    private var defensa: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {

    }

    init {
        calcularSalud()
        calcularAtaque()
        calcularDefensa()
        experiencia = 0
        nivel = 1
    }

    fun getNombre(): String {
        return nombre
    }

    fun setNombre(nombre: String) {
        this.nombre = nombre
    }

    fun getEstadoVital(): String {
        return estadoVital
    }

    fun setEstadoVital(estadoVital: String) {
        this.estadoVital = estadoVital
    }
    fun setAtaque(ataque: Int){
        this.ataque=ataque
    }
    fun getRaza(): String {
        return raza
    }

    fun setRaza(raza: String) {
        this.raza = raza
    }

    fun getClase(): String {
        return clase
    }

    fun setClase(clase: String) {
        this.clase = clase
    }

//    fun getPesoMochila(): Int {
//        return pesoMochila
//    }

    fun getMochila() : Mochila {
        return this.mochila
    }
    fun getExperiencia(): Int {
        return experiencia
    }
    fun setExperiencia(experienciaGanada: Int) {
        experiencia += experienciaGanada
        while (experiencia >= 1000) {
            subirNivel()
            experiencia -= 1000 // Reducir la experiencia en 1000 al subir de nivel
        }
    }
    fun getNivel(): Int {
        return nivel
    }
    fun getSalud(): Int{
        return salud
    }
    fun getAtaque(): Int{
        return ataque
    }
    fun getDefensa(): Int{
        return defensa
    }
    fun setDefensa(defensa:Int){
        this.defensa=defensa
    }
    fun setSalud(salud: Int){
        this.salud=salud
    }
    fun setNivel(nivel: Int){
        this.nivel=nivel
    }
    fun subirNivel() {
        if (nivel < 10) { // Limitar el nivel a 10
            nivel++
            calcularSalud() // Calcular el nuevo valor de salud al subir de nivel
            calcularAtaque() // Calcular el nuevo valor de ataque al subir de nivel
            calcularDefensa()
        }
    }
    fun calcularSalud() {
        salud = when (nivel) {
            1 -> 100
            2 -> 200
            3 -> 300
            4 -> 450
            5 -> 600
            6 -> 800
            7 -> 1000
            8 -> 1250
            9 -> 1500
            10 -> 2000
            else -> 100 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }

    fun calcularAtaque() {
        ataque = when (nivel) {
            1 -> 10
            2 -> 20
            3 -> 25
            4 -> 30
            5 -> 40
            6 -> 100
            7 -> 200
            8 -> 350
            9 -> 400
            10 -> 450
            else -> 10 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }
    fun calcularDefensa() {
        defensa = when (nivel) {
            1 -> 4
            2 -> 9
            3 -> 14
            4 -> 19
            5 -> 49
            6 -> 59
            7 -> 119
            8 -> 199
            9 -> 349
            10 -> 399
            else -> 4 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(estadoVital)
        parcel.writeString(raza)
        parcel.writeString(clase)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Personaje> {
        override fun createFromParcel(parcel: Parcel): Personaje {
            return Personaje(parcel)
        }

        override fun newArray(size: Int): Array<Personaje?> {
            return arrayOfNulls(size)
        }
    }
}
class Monstruo(
    private var nombre: String,
    private var nivel: Int
) {
    private var salud: Int = 0
    private var ataque: Int = 0

    init {
        calcularSalud() // Inicializar la salud según el nivel al nivel 1
        calcularAtaque() // Inicializar el ataque según el nivel al nivel 1
    }

    fun getNombre(): String {
        return nombre
    }
    fun setNombre(nuevoNombre:String) {
        nombre = nuevoNombre
    }
    fun getNivel(): Int {
        return nivel
    }
    fun setNivel(nuevoNivel:Int) {
        nivel = nuevoNivel
    }
    fun getSalud(): Int {
        return salud
    }
    fun setSalud(nuevaSalud: Int) {
        salud = nuevaSalud
    }
    fun getAtaque(): Int {
        return ataque
    }
    fun setAtaque(nuevoAtaque: Int) {
        ataque = nuevoAtaque
    }
    private fun calcularSalud() {
        salud = when (nivel) {
            1 -> 100
            2 -> 125
            3 -> 150
            4 -> 200
            5 -> 250
            6 -> 350
            7 -> 400
            8 -> 600
            9 -> 800
            10 -> 1000
            else -> 100 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }
    private fun calcularAtaque() {
        ataque = when (nivel) {
            1 -> 5
            2 -> 10
            3 -> 15
            4 -> 20
            5 -> 50
            6 -> 60
            7 -> 120
            8 -> 200
            9 -> 350
            10 -> 400
            else -> 5 // Valor por defecto si el nivel está fuera del rango especificado
        }
    }
    override fun toString(): String {
        return "Monstruo: Nombre: $nombre, Nivel: $nivel, Salud: $salud, Ataque: $ataque"
    }

}