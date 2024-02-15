package com.example.personaje

class TiposMonstruos {
    fun infoMonstruo(
        nom:String
    ):Monstruo{
        when(nom){
            "Estrige"->{
                return Monstruo("Estrige",1)
            }
            "Lamia"->{
                return Monstruo("Lamia",2)
            }
            "Kikimora"->{
                return Monstruo("Kikimora",3)
            }
            "Sirena"->{
                return Monstruo("Sirena",4)
            }
            "Ghuls"->{
                return Monstruo("Ghuls",5)
            }
            "Grifo"->{
                return Monstruo("Grifo",6)
            }
            "Djin"->{
                return Monstruo("Djin",7)
            }
            "Basilisco"->{
                return Monstruo("Basilisco",8)
            }
            "Miriápodo"->{
                return Monstruo("Miriápodo",9)
            }
            "Dragón"->{
                return Monstruo("Dragón",10)
            }else -> return Monstruo("Estrige",1)
        }
    }
}