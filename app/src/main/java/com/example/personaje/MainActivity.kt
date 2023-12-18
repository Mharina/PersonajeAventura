package com.example.personaje

import android.content.Intent
import android.graphics.drawable.Drawable
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
import androidx.core.graphics.createBitmap
//import com.example.personaje.Personaje

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var spinnerRaza: Spinner = findViewById(R.id.spinnerRaza)
        var spinnerClase: Spinner = findViewById(R.id.spinnerClase)
        var spinnerEstadoVital: Spinner = findViewById(R.id.spinnerEstadoVital)
        var foto: ImageView = findViewById(R.id.imageView)

        var opcionesRaza: Array<String> = resources.getStringArray(R.array.raza)
        var opcionesClase: Array<String> = resources.getStringArray(R.array.clase)
        var opcionesEstadoVital: Array<String> = resources.getStringArray(R.array.estadoVital)

        var adapterRaza: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.color_spinner_fuera, opcionesRaza)
        spinnerRaza.adapter = adapterRaza
        var adapterClase: ArrayAdapter<String> =
            ArrayAdapter(this, R.layout.color_spinner_fuera, opcionesClase)
        spinnerClase.adapter = adapterClase
        var adapterEstadoVital: ArrayAdapter<String> =
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
                var seleccionRaza: String = parent?.getItemAtPosition(position) as String
                var seleccionClase: String = spinnerClase.selectedItem as String
                var seleccionEstadoVital: String = spinnerEstadoVital.selectedItem as String
                obtenerImagen3(foto, seleccionRaza, seleccionClase, seleccionEstadoVital)
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
                var seleccionClase: String = parent?.getItemAtPosition(position) as String
                var seleccionRaza: String = spinnerRaza.selectedItem as String
                var seleccionEstadoVital: String = spinnerEstadoVital.selectedItem as String
                obtenerImagen3(foto, seleccionRaza, seleccionClase, seleccionEstadoVital)
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
                var seleccionEstadoVital: String = parent?.getItemAtPosition(position) as String
                var seleccionRaza: String = spinnerRaza.selectedItem as String
                var seleccionClase: String = spinnerClase.selectedItem as String
                obtenerImagen3(foto, seleccionRaza, seleccionClase, seleccionEstadoVital)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        var button: Button = findViewById<Button>(R.id.button)
        var nombre: EditText  = findViewById<EditText>(R.id.editTextText)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, PersonajeMostrar::class.java)
            val personaje = Personaje(nombre.text.toString(), 100, spinnerEstadoVital.selectedItem as String, spinnerRaza.selectedItem as String, spinnerClase.selectedItem as String)
            intent.putExtra("personaje", personaje)
            intent.putExtra("imagen_id", foto.drawable.toString())
            startActivity(intent)
        }
    }
    private fun obtenerImagen3(
        foto: ImageView,
        seleccionRaza: String,
        seleccionClase: String,
        seleccionEstadoVital: String
    ) {

        when (seleccionRaza) {
            "Humano" -> {
                when (seleccionClase) {
                    "Mago" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.humanomagoadolescente)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.humanomagoadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.humanomagoanciano)
                            }
                        }
                    }

                    "Brujo" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.humanobrujoadolescente)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.humanobrujoadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.humanobrujoanciano)
                            }
                        }
                    }

                    "Guerrero" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.humanoguerreroadolescente)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.humanoguerreroadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.humanoguerreroanciano)
                            }
                        }
                    }
                }
            }

            "Elfo" -> {
                when (seleccionClase) {
                    "Mago" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.elfoadolescentemago)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.elfomagoadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.elfomagoanciano)
                            }
                        }
                    }

                    "Brujo" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.elfobrujoadolescente2)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.elfobrujoadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.elfobrujoanciano)
                            }
                        }
                    }

                    "Guerrero" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.elfoguerreroadolescente)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.elfoguerreroadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.elfoguerreroanciano)
                            }
                        }
                    }
                }
            }

            "Enano" -> {
                when (seleccionClase) {
                    "Mago" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.enanomagoadolescente2)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.enanomagoadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.enanomagoanciano)
                            }
                        }
                    }

                    "Brujo" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.enanobrujoadolescente)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.enanobrujoadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.enanobrujoanciano)
                            }
                        }
                    }

                    "Guerrero" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.enanoguerreroadolescente)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.enanoguerreroadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.enanoguerreroanciano)
                            }
                        }
                    }
                }
            }

            "Maldito" -> {
                when (seleccionClase) {
                    "Mago" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.malditomagoadolescente)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.malditomagoadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.malditomagoanciano)
                            }
                        }
                    }

                    "Brujo" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.malditobrujoadolescente)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.malditobrujoadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.malditobrujoanciano)
                            }
                        }
                    }

                    "Guerrero" -> {
                        when (seleccionEstadoVital) {
                            "Adolescente" -> {
                                foto.setImageResource(R.drawable.malditoguerreroadolescente)
                            }

                            "Adulto" -> {
                                foto.setImageResource(R.drawable.malditoguerreroadulto)
                            }

                            "Anciano" -> {
                                foto.setImageResource(R.drawable.malditoguerreroanciano)
                            }
                        }
                    }
                }
            }

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