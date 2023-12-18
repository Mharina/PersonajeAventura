package com.example.personaje

import android.widget.ImageView

class Imagen {
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