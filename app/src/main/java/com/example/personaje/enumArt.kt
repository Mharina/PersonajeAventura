package com.example.personaje

import android.widget.ImageView

class enumArt {
    fun enuT(
        tipo: String
    ): Articulo.TipoArticulo {
        when(tipo){
            "ARMA" ->{
                return Articulo.TipoArticulo.ARMA
            }
            "OBJETO" ->{
                return Articulo.TipoArticulo.OBJETO
            }
            "PROTECCION" ->{
                return Articulo.TipoArticulo.PROTECCION
            }else -> return Articulo.TipoArticulo.OBJETO
        }
    }
    fun enuN(
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
            "POCION" ->{
                return Articulo.Nombre.POCION
            }
            "IRA" ->{
                return Articulo.Nombre.IRA
            }
            "ESCUDO" ->{
                return Articulo.Nombre.ESCUDO
            }
            "ARMADURA" ->{
                return Articulo.Nombre.ARMADURA
            }else -> return Articulo.Nombre.BASTON
        }
    }
}