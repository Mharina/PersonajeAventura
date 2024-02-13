package com.example.personaje

class FormarArticulo {
    fun fArti(
        nom: String,
        uni: Int
    ): Articulo {
        when(nom){
            "BASTON" ->{
                return Articulo(2,Articulo.TipoArticulo.ARMA,Articulo.Nombre.BASTON,8,"baston",uni,3)
            }
            "ESPADA" ->{
                return Articulo(3,Articulo.TipoArticulo.ARMA,Articulo.Nombre.ESPADA,5,"espada",uni,4)
            }
            "DAGA" ->{
                return Articulo(1, Articulo.TipoArticulo.ARMA,Articulo.Nombre.DAGA,3,"daga",uni,4)
            }
            "MARTILLO" ->{
                return Articulo(5,Articulo.TipoArticulo.ARMA,Articulo.Nombre.MARTILLO,7,"martillo",uni,4)
            }
            "GARRAS" ->{
                return Articulo(4,Articulo.TipoArticulo.ARMA,Articulo.Nombre.GARRAS,4,"garras",uni,4)
            }
            "POCION" ->{
                return Articulo(9,Articulo.TipoArticulo.OBJETO,Articulo.Nombre.POCION,1,"pocion",uni,4)
            }
            "IRA" ->{
                return Articulo(8,Articulo.TipoArticulo.OBJETO,Articulo.Nombre.IRA,1,"ira",uni,30)
            }
            "ESCUDO" ->{
                return Articulo(6,Articulo.TipoArticulo.PROTECCION,Articulo.Nombre.ESCUDO,5,"escudo",uni,4)
            }
            "ARMADURA" ->{
                return Articulo(7,Articulo.TipoArticulo.PROTECCION,Articulo.Nombre.ARMADURA,9,"armadura",uni,4)
            }
            "HACHA" ->{
                return Articulo(10,Articulo.TipoArticulo.ARMA,Articulo.Nombre.HACHA,6,"hacha",uni,4)
            }
            "MONEDA"->{
                return Articulo(11,Articulo.TipoArticulo.ORO,Articulo.Nombre.MONEDA,0,"moneda",uni,15)
            } else -> return  Articulo(1, Articulo.TipoArticulo.ARMA,Articulo.Nombre.DAGA,3,"daga",uni,4)
        }
    }
}