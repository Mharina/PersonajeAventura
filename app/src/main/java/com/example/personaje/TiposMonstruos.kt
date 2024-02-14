package com.example.personaje

class TiposMonstruos {
    fun infoMonstruo(
        id:Int
    ):Monstruo{
        when(id){
            1->{
                return Monstruo("Estrige",1)
            }
            2->{
                return Monstruo("Lamia",2)
            }
            3->{
                return Monstruo("Kikimora",3)
            }
            4->{
                return Monstruo("Sirena",4)
            }
            5->{
                return Monstruo("Ghuls",5)
            }
            6->{
                return Monstruo("Grifo",6)
            }
            7->{
                return Monstruo("Djin",7)
            }
            8->{
                return Monstruo("Basilisco",8)
            }
            9->{
                return Monstruo("Miriápodo",9)
            }
            10->{
                return Monstruo("Dragón",10)
            }else -> return Monstruo("Estrige",1)
        }
    }
}