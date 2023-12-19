package com.example.personaje

import android.content.Intent
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
        val nombre: EditText  = findViewById(R.id.editTextText)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, PersonajeMostrar::class.java)
            val personaje = Personaje(nombre.text.toString(), 100, spinnerEstadoVital.selectedItem as String, spinnerRaza.selectedItem as String, spinnerClase.selectedItem as String)
            intent.putExtra("personaje", personaje)
            intent.putExtra("imagen_id", foto.drawable.toString())
            startActivity(intent)
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