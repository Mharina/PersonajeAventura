package com.example.personaje

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinnerRaza: Spinner = findViewById(R.id.spinnerRaza)
        val spinnerClase: Spinner = findViewById(R.id.spinnerClase)
        val spinnerEstadoVital: Spinner = findViewById(R.id.spinnerEstadoVital)
        val foto: ImageView = findViewById(R.id.imageView)
        val dbHelperA = DatabaseHelper (this)
        val dbHelperC = DatabaseHelper2 (this)
        val arrayArticulos = ArrayList<Articulo>()
        val arrayArticulosC = ArrayList<Articulo>()
        arrayArticulos.add(Articulo(1,Articulo.TipoArticulo.ARMA,Articulo.Nombre.DAGA,3,"daga",1,9))
        arrayArticulos.add(Articulo(2,Articulo.TipoArticulo.ARMA,Articulo.Nombre.BASTON,4,"baston",1,22))
        arrayArticulos.add(Articulo(3,Articulo.TipoArticulo.ARMA,Articulo.Nombre.ESPADA,3,"espada",1,8))
        arrayArticulos.add(Articulo(10,Articulo.TipoArticulo.ARMA,Articulo.Nombre.HACHA,3,"hacha",1,4))
        arrayArticulos.add(Articulo(4,Articulo.TipoArticulo.ARMA,Articulo.Nombre.GARRAS,3,"garras",1,6))
        arrayArticulos.add(Articulo(5,Articulo.TipoArticulo.ARMA,Articulo.Nombre.MARTILLO,3,"martillo",1,13))
        arrayArticulos.add(Articulo(6,Articulo.TipoArticulo.PROTECCION,Articulo.Nombre.ESCUDO,3,"escudo",1,11))
        arrayArticulos.add(Articulo(7,Articulo.TipoArticulo.PROTECCION,Articulo.Nombre.ARMADURA,3,"armadura",1,5))
        arrayArticulos.add(Articulo(8,Articulo.TipoArticulo.OBJETO,Articulo.Nombre.IRA,3,"ira",1,40))
        arrayArticulos.add(Articulo(9,Articulo.TipoArticulo.OBJETO,Articulo.Nombre.POCION,3,"pocion",1,5))
        for (i in 0..9){
            dbHelperA.insertarArticulo(arrayArticulos[i])
        }

        arrayArticulosC.add(Articulo(1,Articulo.TipoArticulo.ARMA,Articulo.Nombre.DAGA,3,"daga",1,4))
        arrayArticulosC.add(Articulo(2,Articulo.TipoArticulo.ARMA,Articulo.Nombre.BASTON,4,"baston",1,3))
        arrayArticulosC.add(Articulo(3,Articulo.TipoArticulo.ARMA,Articulo.Nombre.ESPADA,3,"espada",2,4))
        arrayArticulosC.add(Articulo(10,Articulo.TipoArticulo.ARMA,Articulo.Nombre.HACHA,3,"hacha",3,4))
        arrayArticulosC.add(Articulo(4,Articulo.TipoArticulo.ARMA,Articulo.Nombre.GARRAS,3,"garras",1,4))
        arrayArticulosC.add(Articulo(5,Articulo.TipoArticulo.ARMA,Articulo.Nombre.MARTILLO,3,"martillo",5,4))
        arrayArticulosC.add(Articulo(6,Articulo.TipoArticulo.PROTECCION,Articulo.Nombre.ESCUDO,3,"escudo",2,4))
        arrayArticulosC.add(Articulo(7,Articulo.TipoArticulo.PROTECCION,Articulo.Nombre.ARMADURA,3,"armadura",7,4))
        arrayArticulosC.add(Articulo(8,Articulo.TipoArticulo.OBJETO,Articulo.Nombre.IRA,3,"ira",1,4))
        arrayArticulosC.add(Articulo(9,Articulo.TipoArticulo.OBJETO,Articulo.Nombre.POCION,3,"pocion",8,4))
        for (i in 0..9){
            dbHelperC.insertarArticulo(arrayArticulosC[i])
        }



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
        val button: Button = findViewById(R.id.button)

        button.setOnClickListener {
            val nombre: EditText = findViewById(R.id.editTextText)
            if (nombre.text.isBlank()) {
                nombre.setError("El campo nombre es necesario")
            } else {
                val intent = Intent(this@MainActivity, PersonajeMostrar::class.java)
                val personaje = Personaje(
                    nombre.text.toString(),
                    100,
                    spinnerEstadoVital.selectedItem as String,
                    spinnerRaza.selectedItem as String,
                    spinnerClase.selectedItem as String
                )
                val mochila = Mochila(
                    personaje.getPesoMochila()

                )
                intent.putExtra("mochila", mochila)
                intent.putExtra("personaje", personaje)
                intent.putExtra("imagen_id", foto.drawable.toString())
                startActivity(intent)
            }
        }
        val OBJETOS_ALEATORIOS = DatabaseHelper(this)
    }
}
class Mochila(private var pesoMochila: Int)
    :Parcelable{
    private var contenido=ArrayList<Articulo>()

    constructor(parcel: Parcel) : this(parcel.readInt()) {

    }

//    fun getPesoMochila():Int{
//        return pesoMochila
//    }
    fun addArticulo(articulo: Articulo) {
        if (articulo.getPeso() <= pesoMochila) {
            when (articulo.getTipoArticulo()) {
                Articulo.TipoArticulo.ARMA -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.BASTON, Articulo.Nombre.ESPADA, Articulo.Nombre.DAGA,
                        Articulo.Nombre.MARTILLO, Articulo.Nombre.GARRAS, Articulo.Nombre.HACHA -> {
                            contenido.add(articulo)
                            this.pesoMochila -= articulo.getPeso()
                            //println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo ARMA.")
                    }
                }
                Articulo.TipoArticulo.OBJETO -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.POCION, Articulo.Nombre.IRA -> {
                            contenido.add(articulo)
                            this.pesoMochila -= articulo.getPeso()
                            //println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo OBJETO.")
                    }
                }
                Articulo.TipoArticulo.PROTECCION -> {
                    when (articulo.getNombre()) {
                        Articulo.Nombre.ESCUDO, Articulo.Nombre.ARMADURA -> {
                            contenido.add(articulo)
                            this.pesoMochila -= articulo.getPeso()
                            //println("${articulo.getNombre()} ha sido añadido a la mochila.")
                        }
                        else -> println("Nombre del artículo no válido para el tipo PROTECCION.")
                    }
                }
            }
        } else {
            println("El peso del artículo excede el límite de la mochila.")
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
    enum class TipoArticulo { ARMA, OBJETO, PROTECCION }
    enum class Nombre { BASTON, ESPADA, DAGA, HACHA, MARTILLO, GARRAS, POCION, IRA, ESCUDO, ARMADURA }
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
    private var pesoMochila: Int,
    private var estadoVital: String,
    private var raza: String,
    private var clase: String,

    ): Parcelable {
    var monedero = HashMap<Int, Int>()

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {

    }

    init {
        monedero.put(1, 0)
        monedero.put(5, 0)
        monedero.put(10, 0)
        monedero.put(25, 0)
        monedero.put(100, 0)
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

    fun getPesoMochila(): Int {
        return pesoMochila
    }

    fun setPesoMochila(pesoMochila: Int) {
        this.pesoMochila = pesoMochila
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeInt(pesoMochila)
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