package com.example.personaje

class NombreArticulo {
    fun nArti(
        nom: String
    ): Articulo.Nombre {
        when(nom){
            "BASTON" ->{
                return Articulo.Nombre.BASTON
            }
            "ESPADA" ->{
                return Articulo.Nombre.ESPADA
            }
            "DAGA" ->{
                return Articulo.Nombre.DAGA
            }
            "MARTILLO" ->{
                return Articulo.Nombre.MARTILLO
            }
            "GARRAS" ->{
                return Articulo.Nombre.GARRAS
            }
            "ESCUDO" ->{
                return Articulo.Nombre.ESCUDO
            }
            "ARMADURA" ->{
                return Articulo.Nombre.ARMADURA
            }
            "HACHA" ->{
                return Articulo.Nombre.HACHA
            }else -> return  Articulo.Nombre.DAGA
        }
    }
}