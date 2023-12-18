package com.example.personaje

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.personaje.obtImg

class PersonajeMostrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personaje_mostrar)

        val pj = intent.getParcelableExtra<Personaje>("personaje")
        //val img = intent.getStringExtra("imagen_id")

        val nombre = findViewById<TextView>(R.id.textView5)
        val raza = findViewById<TextView>(R.id.textView7)
        val clase = findViewById<TextView>(R.id.textView9)
        val estadoVital = findViewById<TextView>(R.id.textView11)
        var foto: ImageView = findViewById(R.id.imageView2)

        //foto.setImageResource(img.drawa)
        nombre.text = "${pj?.getNombre()}"
        raza.text = "${pj?.getRaza()}"
        clase.text = "${pj?.getClase()}"
        estadoVital.text = "${pj?.getEstadoVital()}"
        var btnVolver: Button = findViewById(R.id.Volver1)
        var btnComenzar: Button = findViewById(R.id.empezar)
//        val recursoImg = "${pj?.getRaza()?.toLowerCase()}"+
//                "${pj?.getClase()?.toLowerCase()}"+
//                "${pj?.getRaza()?.toLowerCase()}"+".jpg"
//        val idRecurso = resources.getIdentifier(recursoImg, "drawable", packageName)
//        if (idRecurso!=0)
//            foto.setImageResource(idRecurso)
//        else
//            foto.setImageResource(R.drawable.comodin)
        obtenerImagen3(foto,pj?.getRaza().toString(),pj?.getClase().toString(),pj?.getEstadoVital().toString())


        btnVolver.setOnClickListener {
            val intent = Intent(this@PersonajeMostrar, MainActivity::class.java)
            startActivity(intent)
        }
        btnComenzar.setOnClickListener {
            val intent = Intent(this@PersonajeMostrar, Aventura::class.java)
            startActivity(intent)
        }
    }
    fun obtenerImagen3(
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