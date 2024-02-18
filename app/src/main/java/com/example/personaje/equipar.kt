package com.example.personaje

class equipar {
    fun fequi(
        nom: Articulo.Nombre,
        pj:Personaje
    ):Int{
        when(nom){
            Articulo.Nombre.BASTON ->{
                return 10
            }
            Articulo.Nombre.ESPADA ->{
                return 15
            }
            Articulo.Nombre.DAGA ->{
                return 5
            }
            Articulo.Nombre.MARTILLO ->{
                return 20
            }
            Articulo.Nombre.GARRAS ->{
                return 15
            }
            Articulo.Nombre.ESCUDO ->{
                return 10
            }
            Articulo.Nombre.ARMADURA ->{
                return 30
            }
            Articulo.Nombre.HACHA ->{
                return 10
            }else -> {
                pj.setAtaque(pj.getAtaque())
                pj.setDefensa(pj.getDefensa())
                return  0
            }
        }
    }
}